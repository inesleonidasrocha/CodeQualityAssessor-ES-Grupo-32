package metrics;

import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * @author Beatriz Lima
 * @author Maria Inês Monteiro
 * @author Tiago Bombas
 */

public class JavaToExcel extends Thread {
	private static GetMetrics getMetrics;
	private static String folderName;
	private static HSSFWorkbook workbook;

	public JavaToExcel(String folderName) {
		this.folderName = folderName;

	}

	public static void writeToExcel() {
		try {

			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);

			rowhead.createCell(0).setCellValue("MethodID");
			rowhead.createCell(1).setCellValue("package");
			rowhead.createCell(2).setCellValue("class");
			rowhead.createCell(3).setCellValue("method");
			rowhead.createCell(4).setCellValue("NOM_class");
			rowhead.createCell(5).setCellValue("LOC_class");
			rowhead.createCell(6).setCellValue("WMC_class");
			rowhead.createCell(7).setCellValue("LOC_method");
			rowhead.createCell(8).setCellValue("CYCLO_method");

			File projectDir = new File(folderName);

			String filename = projectDir.getName() + "_metrics.xls";

			HashMap<Integer, String[]> methodID = getMetrics.extractMetrics(projectDir, 50, 10, 50, 10, "AND");

			for (int i = 0; i < methodID.size(); i++) {
				HSSFRow row = rowContents(sheet, methodID, i);

			}
			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Your excel file has been generated!");
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	public HSSFWorkbook getWorkBook() {
		return workbook;
	}

	private static HSSFRow rowContents(HSSFSheet sheet, HashMap<Integer, String[]> methodID, int numberOfLine) {
		String[] a = methodID.get(numberOfLine + 1);
		HSSFRow row = sheet.createRow((short) numberOfLine + 1);
		row.createCell(0).setCellValue(String.valueOf(numberOfLine + 1));
		row.createCell(1).setCellValue(a[2]);
		row.createCell(2).setCellValue(a[0]);
		row.createCell(3).setCellValue(a[1]);
		row.createCell(4).setCellValue(a[3]);
		row.createCell(5).setCellValue(a[4]);
		row.createCell(6).setCellValue(a[5]);
		row.createCell(7).setCellValue(a[6]);
		row.createCell(8).setCellValue(a[7]);
		return row;
	}

	public void run() {

		writeToExcel();

	}

}