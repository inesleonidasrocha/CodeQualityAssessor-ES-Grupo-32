package gui;

import java.io.File;
//reading value of a particular cell  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import metrics.GetMetrics;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import codeSmells.GetCodeSmellsExcel;
/**
 * @author Inês Rocha
 * @author Tiago Bombas
 */
public class ExcelReader {

	private GetCodeSmellsExcel getCodeSmellsExcel = new GetCodeSmellsExcel();
	public final static int CLASSROWNUMBER = 2;
	public final static int METHODROWNUMBER = 3;
	public final static int GODROWNUMBER = 7;
	public final static int LONGROWNUMBER = 10;
	public final static int FILELENGHT = 248;
	public static HashMap<Integer, String[]> codeSmellsProf;
	private static HashMap<Integer, String[]> ourCodeSmells;
	private static HashMap<Integer, String[]> codeSmellsMerged;
	public static String[] colunas;
	private static int VPGod = 0;
	private static int VNGod = 0;
	private static int FPGod = 0;
	private static int FNGod = 0;
	private static int VPLong = 0;
	private static int VNLong = 0;
	private static int FPLong = 0;
	private static int FNLong = 0;

	public ExcelReader(HashMap<Integer, String[]> ourCodeSmells) {
		this.ourCodeSmells = ourCodeSmells;
		this.codeSmellsProf = getCodeSmellsExcel.getProfCodeSmells();
	}

	public void getCodeSmell() throws IOException {
		codeSmellsMerged = new HashMap<Integer, String[]>();
		this.mergeHash();
		this.getCodeQuality(codeSmellsMerged);
	}

	public void mergeHash() {
		for (int i = 1; i <= ourCodeSmells.size(); i++) {
			String[] metricsData = new String[8];
			metricsData = ourCodeSmells.get(i);

			for (int j = 1; j < FILELENGHT; j++) {
				String[] codeProf = new String[4];
				codeProf = codeSmellsProf.get(j);

				String[] getCodeData = new String[4];

				if (metricsData[0].equals(codeProf[2]) && metricsData[1].equals(codeProf[3])) {

					getCodeData[0] = codeProf[0];// GodRow given
					getCodeData[1] = codeProf[1];// LongRow given
					getCodeData[2] = metricsData[9];// GodRow ours
					getCodeData[3] = metricsData[8];// longRow ours

					codeSmellsMerged.put(codeSmellsMerged.size() + 1, getCodeData);
				}

			}
		}
	}

	public int indicatorToGui(String value) {
		int count = 0;
		if (value.equals("VPGod")) {
			count = VPGod;
			VPGod = 0;
		}
		if (value.equals("VNGod")) {
			count = VNGod;
			VNGod = 0;
		}
		if (value.equals("FPGod")) {
			count = FPGod;
			FPGod = 0;
		}
		if (value.equals("FNGod")) {
			count = FNGod;
			FNGod = 0;
		}
		if (value.equals("VPLong")) {
			count = VPLong;
			VPLong = 0;
		}
		if (value.equals("VNLong")) {
			count = VNLong;
			VNLong = 0;
		}
		if (value.equals("FPLong")) {
			count = FPLong;
			FPLong = 0;
		}
		if (value.equals("FNLong")) {
			count = FNLong;
			FNLong = 0;
		}

		return count;
	}

	public void getCodeQuality(HashMap<Integer, String[]> methodId) {

		for (int i = 1; i < methodId.size(); i++) {
			String[] codeMerged = new String[8];
			codeMerged = codeSmellsMerged.get(i);

			// buscar indicadores para o is_Long_method
			getIndicadoresLong(codeMerged[1], codeMerged[3]);

			// buscar indicadores para o is_god_class
			getIndicadoresGod(codeMerged[0], codeMerged[2]);

		}

	}

	private void getIndicadoresLong(String givenLongRow, String ourLongRow) {
		if (givenLongRow.equalsIgnoreCase("TRUE") && ourLongRow.equalsIgnoreCase("TRUE"))
			VPLong++;

		if (givenLongRow.equalsIgnoreCase("TRUE") && ourLongRow.equalsIgnoreCase("FALSE"))
			FPLong++;

		if (givenLongRow.equalsIgnoreCase("FALSE") && ourLongRow.equalsIgnoreCase("TRUE"))
			FNLong++;

		if (givenLongRow.equalsIgnoreCase("FALSE") && ourLongRow.equalsIgnoreCase("FALSE"))
			VNLong++;

	}

	public void getIndicadoresGod(String givenGodRow, String ourGodRow) {
		if (givenGodRow.equalsIgnoreCase("TRUE") && ourGodRow.equalsIgnoreCase("TRUE"))
			VPGod++;

		if (givenGodRow.equalsIgnoreCase("TRUE") && ourGodRow.equalsIgnoreCase("FALSE"))
			FPGod++;

		if (givenGodRow.equalsIgnoreCase("FALSE") && ourGodRow.equalsIgnoreCase("TRUE"))
			FNGod++;

		if (givenGodRow.equalsIgnoreCase("FALSE") && ourGodRow.equalsIgnoreCase("FALSE"))
			VNGod++;

	}

	// method defined for reading a cell
	public String ReadCellData(int vRow, int vColumn) {

		return getCodeSmellsExcel.ReadCellData(vRow, vColumn);
	}
}