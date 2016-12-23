/*
 * WindowFindTagCards.java
 *
 * Created on Feb 21, 2011, 10:35:45 AM
 */
package relatorpostoeaton.screens;

import java.util.List;
import javax.swing.JFrame;
import relatorpostoeaton.entities.Carro;
import relatorpostoeaton.entities.Colaborador;

/**
 *
 * @author icarlos
 */
public class WindowFindTagCards extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Creates new form WindowFindTagCards */
    public WindowFindTagCards() {
        initComponents();
    }


    private void jBtnFecharActionPerformed(java.awt.event.ActionEvent evt) {                                           
        dispose();
    }                                          

    private void jBtnOKActionPerformed(java.awt.event.ActionEvent evt) {                                       

        try {
            List<Carro> carList = AbastecimentoSC.getInstanceAbastecimentoSC().getCarroList();
            List<Colaborador> colabList = AbastecimentoSC.getInstanceAbastecimentoSC().getColabList();
            AbastecimentoSC abastecimentoSC = AbastecimentoSC.getInstanceAbastecimentoSC();
            Carro carro = carList.get(jTableCarros.getSelectedRow());
            Colaborador col = colabList.get(jTableFrentistas.getSelectedRow());
            System.out.println("====car===" + carro.getPlaca() + "==" + carro.getNome());
            System.out.println("====colab===" + col.getNomFun() + "==" + col.getTag());
            if (colabList.size() > 0) {
                abastecimentoSC.getjCmbBxFrentista().setSelectedItem(col);
                System.out.println("===passou aqui1===" + col);            }
            if (carList.size() > 0) {
                abastecimentoSC.getjCmbBxAbastecCarro().setSelectedItem(carro);
                System.out.println("===passou aqui2===" + carro);
            }
            abastecimentoSC.repaint();
        } catch (IndexOutOfBoundsException index) {
        }
    }                                      

    private void jTableCarrosMouseClicked(java.awt.event.MouseEvent evt) {                                          
    }                                         

    private void jTableFrentistasMouseClicked(java.awt.event.MouseEvent evt) {                                              
    }                                             

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new WindowFindTagCards().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JButton jBtnFechar;
    private javax.swing.JButton jBtnOK;
    private javax.swing.JLabel jLabelCarros;
    private javax.swing.JLabel jLabelFrentistas;
    private javax.swing.JLabel jLabelTittle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableCarros;
    private javax.swing.JTable jTableFrentistas;
    // End of variables declaration

    /**
     * @return the jTableCarros
     */
    public javax.swing.JTable getjTableCarros() {
        return jTableCarros;
    }

    /**
     * @param jTableCarros the jTableCarros to set
     */
    public void setjTableCarros(javax.swing.JTable jTableCarros) {
        this.jTableCarros = jTableCarros;
    }

    /**
     * @return the jTableFrentistas
     */
    public javax.swing.JTable getjTableFrentistas() {
        return jTableFrentistas;
    }

    /**
     * @param jTableFrentistas the jTableFrentistas to set
     */
    public void setjTableFrentistas(javax.swing.JTable jTableFrentistas) {
        this.jTableFrentistas = jTableFrentistas;
    }
}

