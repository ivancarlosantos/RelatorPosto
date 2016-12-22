/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorpostoeaton.business.datamodels;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import relatorpostoeaton.entities.Colaborador;
import relatorpostoeaton.entities.Frentista;

/**
 *
 * @author icarlos
 */
public class ColaboradorDataModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private Object[] data[];
    private String[] columnNames = {"Nome", "Apelido", "NumeroCadastro", "DataNasc", "TipoColaborador", "Sexo", "RegLocal", "Função","NumTag"};

    public ColaboradorDataModel(List<Colaborador> mytabledata) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        data = new Object[mytabledata.size()][columnNames.length];
        int i = 0;
        for (Colaborador colab : mytabledata) {
            data[i][0] = colab.getNomFun();
            data[i][1] = colab.getApeFun();
            data[i][2] = colab.getColaboradorPK().getNumCad();
            data[i][3] = sdf.format(colab.getDatNas());
            data[i][4] = colab.getColaboradorPK().getTipCol();
            data[i][5] = colab.getTipSex();
            data[i][6] = colab.getColaboradorPK().getRegLocal();
            if (colab instanceof Frentista) {
                data[i][7] = "Frentista";
            } else {
                data[i][7] = "Colaborador";
            }
            data[i][8] = colab.getTag();
            i++;
        }
    }

    public int getRowCount() {
        if (data != null) {
            return data.length;
        }
        return 0;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * @return the data
     */
    public Object[][] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object[][] data) {
        this.data = data;
    }

    /**
     * @return the columnNames
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * @param columnNames the columnNames to set
     */
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
}

