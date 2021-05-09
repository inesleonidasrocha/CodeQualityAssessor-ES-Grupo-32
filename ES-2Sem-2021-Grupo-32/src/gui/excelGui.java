package gui;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
/**
 * @author Inês Rocha
 */
public class excelGui implements Serializable {
	private static JTable tabelaExcel;
	private static JScrollPane scrollPane;

	public JTable getTabelaExcel() {
		return tabelaExcel;
	}

	public void setTabelaExcel(JTable tabelaExcel) {
		this.tabelaExcel = tabelaExcel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public static void dataToExcel(HashMap<Integer, String[]> methodID) {
		String[][] rowData = new String[methodID.size()][11];
		for (int i = 0; i < methodID.size(); i++) {
			String[] methodData = methodID.get(i + 1);
			rowData[i][0] = String.valueOf(i + 1);
			rowData[i][1] = methodData[2];
			rowData[i][2] = methodData[0];
			rowData[i][3] = methodData[1];
			rowData[i][4] = methodData[3];
			rowData[i][5] = methodData[4];
			rowData[i][6] = methodData[5];
			rowData[i][7] = methodData[9]; // is_God_Class
			rowData[i][8] = methodData[6];
			rowData[i][9] = methodData[7];
			rowData[i][10] = methodData[8];// is_long_Method
		}
		tabelaExcel.setModel(
				new DefaultTableModel(rowData, new String[] { "MethodID", "package", "class", "method", "NOM_class",
						"LOC_class", "WMC_class", "is_God_class", "LOC_method", "CYCLO_method", "is_Long_Method" }));
		tabelaExcel.getColumnModel().getColumn(0).setMinWidth(70);
		tabelaExcel.getColumnModel().getColumn(1).setMinWidth(70);
		tabelaExcel.getColumnModel().getColumn(2).setMinWidth(20);
		tabelaExcel.getColumnModel().getColumn(3).setMinWidth(70);
		tabelaExcel.getColumnModel().getColumn(4).setMinWidth(70);
		tabelaExcel.getColumnModel().getColumn(5).setMinWidth(100);
		tabelaExcel.getColumnModel().getColumn(6).setMinWidth(100);
		tabelaExcel.getColumnModel().getColumn(7).setMinWidth(100);
		tabelaExcel.getColumnModel().getColumn(8).setMinWidth(100);
		tabelaExcel.getColumnModel().getColumn(9).setMinWidth(100);
		tabelaExcel.getColumnModel().getColumn(10).setMinWidth(100);
		tabelaExcel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabelaExcel.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tabelaExcel);
	}
}