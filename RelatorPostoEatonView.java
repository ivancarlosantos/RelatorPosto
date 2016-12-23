/*
 * RelatorPostoEatonView.java
 */
package relatorpostoeaton;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.table.AbstractTableModel;
import relatorpostoeaton.cellrenderers.CentroDeCustoCellRenderer;
import relatorpostoeaton.cellrenderers.FrentistaCellRenderer;
import relatorpostoeaton.cellrenderers.VeiculoCellRenderer;
import relatorpostoeaton.entities.Frentista;
import relatorpostoeaton.entities.Registros;
import relatorpostoeaton.entities.Veiculo;
import relatorpostoeaton.exporters.PDFExporter;
/**
 * The application's main frame.
 */
public class RelatorPostoEatonView extends FrameView {

    SimpleDateFormat gsdf = new SimpleDateFormat("dd/MM/yyyy");
    Properties prop = new Properties();

    public RelatorPostoEatonView(SingleFrameApplication app) {
        super(app);

        initComponents();
        try {
            prop.load(new FileInputStream("rpev.properties"));
            this.jTextFieldRepExport.setText(prop.getProperty("repexport"));
            this.jTextFieldPDFReader.setText(prop.getProperty("pdfreader"));
        } catch (IOException ex) {
            Logger.getLogger(RelatorPostoEatonView.class.getName()).log(Level.SEVERE, null, ex);
        }
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = RelatorPostoEatonApp.getApplication().getMainFrame();
            aboutBox = new RelatorPostoEatonAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        RelatorPostoEatonApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
                         
    RelatorioTableModel rtm;

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {                                               
   
    }                                              

    private void jButtonPDFExportActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        PDFExporter pdfe = new PDFExporter();
        HashMap thm = null;
        String titulo = "Relatório Geral";
        if (jRBGetSelection(buttonGroupAgrupamento).equals(jRadioButtonFrentista)) {
            thm = hmcFrentistaLitros;
            titulo = "Relatório por Frentista";
        }

        if (jRBGetSelection(buttonGroupAgrupamento).equals(jRadioButtonVeiculo)) {
            thm = hmcchc;
            titulo = "Relatório por Veículo";
        }

        if (jRBGetSelection(buttonGroupAgrupamento).equals(jRadioButtonCentroCusto)) {
            thm = hmcCentroCustoLitros;
            titulo = "Relatório por Centros de Custo";
        }

        if (jRBGetSelection(buttonGroupAgrupamento).equals(jRadioButtonData)) {
            titulo = "Relatório por Data";
            thm = hmcDataLitros;
        }

        if (jRBGetSelection(buttonGroupAgrupamento).equals(jRadioButtonSAgrupar)) {
            titulo = "Relatório Geral";
            thm = hmcGeral;
        }

        Date ini = null;
        Date fim = null;
        if ((jFormattedTextFieldDataIni.getText() != null) && (jFormattedTextFieldDataIni.getText().compareTo("") != 0)) {
            try {
                ini = df2.parse(jFormattedTextFieldDataIni.getText());
            } catch (ParseException ex) {
                Logger.getLogger(RelatorPostoEatonView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((jFormattedTextFieldDataFim.getText() != null) && (jFormattedTextFieldDataFim.getText().compareTo("") != 0)) {
            try {
                fim = df2.parse(jFormattedTextFieldDataFim.getText());
            } catch (ParseException ex) {
                Logger.getLogger(RelatorPostoEatonView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String filepath = jTextFieldRepExport.getText() + jTextFieldNomeSaida.getText();
        pdfe.setFile(filepath);
        pdfe.export(ini, fim, titulo, thm, rtm);
        if(jCheckBoxVisualizar.isSelected()) {
            try {
                String cmd[] = {jTextFieldPDFReader.getText(), filepath};
                Runtime.getRuntime().exec(cmd);
            } catch (IOException ex) {
                Logger.getLogger(RelatorPostoEatonView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }                                                

    private void jButtonRepexportActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(this.jPanel3);
        jTextFieldRepExport.setText(fileChooser.getSelectedFile().getPath()+"/");
    }                                                

    private void jButtonPDFReaderActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this.jPanel3);
        jTextFieldPDFReader.setText(fileChooser.getSelectedFile().getPath());
    }                                                

    private void jButtonSalvarConfigActionPerformed(java.awt.event.ActionEvent evt) {                                                    
         try {
            prop.setProperty("repexport", this.jTextFieldRepExport.getText());
            prop.setProperty("pdfreader", this.jTextFieldPDFReader.getText());
            prop.store(new FileOutputStream("rpev.properties"), "Files path properties.");
        } catch (IOException ex) {
            Logger.getLogger(RelatorPostoEatonView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                                   

    private void jButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {                                              
        jListCentrosDeCusto.clearSelection();
        jListFrentista.clearSelection();
        jListTipoVeiculos.clearSelection();
        jListVeiculos.clearSelection();
        jTextFieldHodometroFim.setText("");
        jTextFieldHodometroIni.setText("");
        jFormattedTextFieldDataFim.setText("");
        jFormattedTextFieldDataIni.setText("");
        buttonGroupAgrupamento.setSelected(jRadioButtonSAgrupar.getModel(), true);
    }                                             

    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroupAgrupamento;
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonLimpar;
    private javax.swing.JButton jButtonPDFExport;
    private javax.swing.JButton jButtonPDFReader;
    private javax.swing.JButton jButtonRepexport;
    private javax.swing.JButton jButtonSalvarConfig;
    private javax.swing.JCheckBox jCheckBoxVisualizar;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataFim;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListCentrosDeCusto;
    private javax.swing.JList jListFrentista;
    private javax.swing.JList jListTipoVeiculos;
    private javax.swing.JList jListVeiculos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButtonCentroCusto;
    private javax.swing.JRadioButton jRadioButtonData;
    private javax.swing.JRadioButton jRadioButtonFrentista;
    private javax.swing.JRadioButton jRadioButtonSAgrupar;
    private javax.swing.JRadioButton jRadioButtonVeiculo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableResultados;
    private javax.swing.JTextField jTextFieldHodometroFim;
    private javax.swing.JTextField jTextFieldHodometroIni;
    private javax.swing.JTextField jTextFieldNomeSaida;
    private javax.swing.JTextField jTextFieldPDFReader;
    private javax.swing.JTextField jTextFieldRepExport;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration                   
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

  

    private HashMap<String, CalculatedByCarHashCell> hmcchc = new HashMap<String, CalculatedByCarHashCell>();
    private HashMap<String, Double> hmcFrentistaLitros = new HashMap<String, Double>();
    private HashMap<String, Double> hmcCentroCustoLitros = new HashMap<String, Double>();
    private HashMap<String, Double> hmcDataLitros = new HashMap<String, Double>();
    private HashMap<String, Double> hmcGeral = new HashMap<String, Double>();

    public class CalculatedByCarHashCell {

        private Double litros;
        private Double minHodom;
        private Double maxHodom;

        /**
         * @return the litros
         */
        public Double getLitros() {
            return litros;
        }

        /**
         * @param litros the litros to set
         */
        public void setLitros(Double litros) {
            this.litros = litros;
        }

        /**
         * @return the minHodom
         */
        public Double getMinHodom() {
            return minHodom;
        }

        /**
         * @param minHodom the minHodom to set
         */
        public void setMinHodom(Double minHodom) {
            this.minHodom = minHodom;
        }

        /**
         * @return the maxHodom
         */
        public Double getMaxHodom() {
            return maxHodom;
        }

        /**
         * @param maxHodom the maxHodom to set
         */
        public void setMaxHodom(Double maxHodom) {
            this.maxHodom = maxHodom;
        }
    }

    public void calculateHmcchc(List<Object[]> mlr) {
        hmcchc.clear();
        hmcCentroCustoLitros.clear();
        hmcDataLitros.clear();
        hmcFrentistaLitros.clear();
        hmcGeral.clear();
        Double totalLitros = 0d;
        for (Object[] o : mlr) {
            Registros r = (Registros) o[0];
            Veiculo v = (Veiculo) o[1];
            Frentista f = (Frentista) o[2];
            if (!hmcchc.containsKey(r.getPlaca() + " - " + v.getModelo())) {
                CalculatedByCarHashCell cchc = new CalculatedByCarHashCell();
                cchc.setLitros(r.getLitros());
                cchc.setMaxHodom(r.getKm());
                cchc.setMinHodom(r.getKm());
                hmcchc.put(r.getPlaca() + " - " + v.getModelo(), cchc);
            } else {
                CalculatedByCarHashCell cchc = hmcchc.get(r.getPlaca() + " - " + v.getModelo());
                cchc.setLitros(cchc.getLitros() + r.getLitros());
                if (r.getKm()>cchc.getMaxHodom().doubleValue()) {
                    cchc.setMaxHodom(r.getKm());
                }
                if (r.getKm() < cchc.getMinHodom().doubleValue()) {
                    cchc.setMinHodom(r.getKm());
                }
            }
            if (!hmcCentroCustoLitros.containsKey(v.getCentroCusto().toString())) {
                hmcCentroCustoLitros.put(v.getCentroCusto().toString(), r.getLitros());
            } else {
                Double ccd = hmcCentroCustoLitros.get(v.getCentroCusto().toString());
                ccd += r.getLitros();
                hmcCentroCustoLitros.put(v.getCentroCusto().toString(), ccd);
            }
            if (!hmcFrentistaLitros.containsKey(r.getMatricula() + " - " + f.getNomFun())) {
                hmcFrentistaLitros.put(r.getMatricula() + " - " + f.getNomFun(), r.getLitros());
            } else {
                Double d = hmcFrentistaLitros.get(r.getMatricula() + " - " + f.getNomFun());
                d += r.getLitros();
                hmcFrentistaLitros.put(r.getMatricula() + " - " + f.getNomFun(),d);
            }
            if (!hmcDataLitros.containsKey(gsdf.format(r.getData()))) {
                hmcDataLitros.put(gsdf.format(r.getData()), r.getLitros());
            } else {
                Double dl = hmcDataLitros.get(gsdf.format(r.getData()));
                dl += r.getLitros();
                hmcDataLitros.put(gsdf.format(r.getData()), dl);
            }
            totalLitros += r.getLitros();
        }
        hmcGeral.put("GERAL", totalLitros);
    }

    public class RelatorioTableModel extends AbstractTableModel {

        private String[] columnNames = {"Índice", "Veículo", "Centro de Custo", "Frentista", "Hodômetro", "Litros", "Data"};
        Object[][] data;

        public String[] getColumnNames() {
            return columnNames;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public RelatorioTableModel(List<Object[]> ldata) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DecimalFormat df = new DecimalFormat("#0.00");
            data = new Object[ldata.size()][columnNames.length];
            int i = 0;
            for (Object[] ob : ldata) {
                Registros reg = ((Registros) ob[0]);
                Veiculo veic = ((Veiculo) ob[1]);
                Frentista frent = ((Frentista) ob[2]);
                data[i][0] = reg.getIndice();
                data[i][1] = veic.getPlaca() + " - " + veic.getModelo();
                data[i][2] = veic.getCentroCusto().toString();
                data[i][3] = frent.getColaboradorPK() + " - " + frent.getNomFun();
                data[i][4] = df.format(reg.getKm()).replace('.', ',');
                data[i][5] = df.format((reg.getLitros())).replace('.', ',');
                data[i][6] = sdf.format(reg.getData());
                i++;
            }
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }

    public static JRadioButton jRBGetSelection(ButtonGroup group) {
        for (Enumeration e = group.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel() == group.getSelection()) {
                return b;
            }
        }
        return null;
    }
}

