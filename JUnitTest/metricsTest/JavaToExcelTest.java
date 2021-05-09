package metricsTest;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metrics.JavaToExcel;

class JavaToExcelTest {
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
		excelFile.run();
		assertNotNull(excelFile.getWorkBook());
	}

}
