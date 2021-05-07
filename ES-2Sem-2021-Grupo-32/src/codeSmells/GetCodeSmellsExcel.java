package codeSmells;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gui.ExcelReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import java.util.HashMap;

public class GetCodeSmellsExcel {
	private FileInputStream file;

	public String ReadCellData(int vRow, int vColumn) {
		String value = null;
		Workbook workBook = null;
		try {
			file = new FileInputStream("src/Code_Smells.xlsx");
			workBook = new XSSFWorkbook(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = workBook.getSheetAt(0);
		Row row = sheet.getRow(vRow);
		Cell cell = row.getCell(vColumn);
		value = cell.toString();
		return value;
	}

	public HashMap<Integer, String[]> getProfCodeSmells() {
		ExcelReader.codeSmellsProf = new HashMap<Integer, String[]>();
		for (int i = 1; i < ExcelReader.FILELENGHT; i++) {
			ExcelReader.colunas = new String[4];
			ExcelReader.colunas[0] = this.ReadCellData(i, ExcelReader.GODROWNUMBER);
			ExcelReader.colunas[1] = this.ReadCellData(i, ExcelReader.LONGROWNUMBER);
			ExcelReader.colunas[2] = this.ReadCellData(i, ExcelReader.CLASSROWNUMBER);
			ExcelReader.colunas[3] = this.ReadCellData(i, ExcelReader.METHODROWNUMBER);
			ExcelReader.codeSmellsProf.put(i, ExcelReader.colunas);
		}
		return ExcelReader.codeSmellsProf;
	}
}