package metricas;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metrics.GetMetricas;
import metrics.JavaToExcel;

class JavaToExcelTest {
	private static HashMap<Integer, String[]> map;
	private static final int LOC_method = 50;
	private static final int CYCLO_method = 10;
	private static final int WMC_class = 50;
	private static final int NOM_class = 10;
	private static final String operadorLogico = "AND";
	private static final String PATH = "C:\\Users\\Bombas\\Pictures\\ES_eclipse\\met_es";
	private static JavaToExcel excelFile;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		excelFile = new JavaToExcel(PATH);

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		excelFile.writeToExcel();
		assertNotNull(excelFile.getWorkBook());
	}

}
