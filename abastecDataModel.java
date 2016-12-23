/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package relatorpostoeaton.business.datamodels;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import relatorpostoeaton.entities.Abastecimento;
/**
 *
 * @author icarlos
 */
public class AbastecimentoDataModel extends AbstractTableModel{

    private static final long serialVersionUID = 1L;
    private Object[] data[];
    private String[] columnNames = {"ID", "Motorista", "Carro", "DataAbastecimento", "Frentista","Combustivel","KM","Litros"};

    public AbastecimentoDataModel(List<Abastecimento> mytabledata) {
        data = new Object[mytabledata.size()][columnNames.length];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int i = 0;
        for (Abastecimento abt : mytabledata) {
            data[i][0] = abt.getId();
            data[i][1] = abt.getMotorista();
            data[i][2] = abt.getCarro();
            data[i][3] = sdf.format(abt.getDataAbastecimento());
            data[i][4] = abt.getFrentista();
            data[i][5] = abt.getCombustivel();
            data[i][6] = abt.getKm();
            data[i][7] = abt.getLitros();
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

