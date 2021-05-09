package metricsTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metrics.DirExplorer;

class DirExplorerTest {
	private static File file = new File("src");
	private static final int numberOfJavaFiles = 11;
	private static int countNumberOfFiles = 0;
	private static boolean dirEmpty;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			countNumberOfFiles++;
		}).explore(file);

		DirExplorer a = new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
		});
		a.isDirEmpty(0, "", file);
		dirEmpty = a.isDirEmpty(0, "", file);

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
	void testNumberFilesInSrc() {
		assertEquals(numberOfJavaFiles,countNumberOfFiles);
	}

	@Test
	void testIsDirEmpty() {
		assertEquals(dirEmpty, true);
	}

}
