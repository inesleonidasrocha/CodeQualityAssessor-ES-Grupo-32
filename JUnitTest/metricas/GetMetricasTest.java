package metricas;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import metrics.DirExplorer;
import metrics.GetMetricas;

class GetMetricasTest {
	static List<String> actualList = new ArrayList<String>();
	static List<String> expectedlList = new ArrayList<String>();
	private static File projectDir = new File("C:\\Users\\Bombas\\Pictures\\ES_eclipse\\test");
	private static GetMetricas metrics = new GetMetricas();
	private static final int numberLines = 4409;
	private static HashMap<Integer, String[]> map;
	private static final int LOC_method = 50;
	private static final int CYCLO_method = 10;
	private static final int WMC_class = 50;
	private static final int NOM_class = 10;
	private static final String operadorLogico = "AND";
 
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			try {
				StaticJavaParser.getConfiguration().setAttributeComments(false);

				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						List<MethodDeclaration> methods = n.getMethods();

						for (MethodDeclaration a : methods) {
							if (!n.isInterface()) {
								actualList.add(metrics.LOC_method(a));
							}
						}
					}

				}.visit(StaticJavaParser.parse(file), null);

			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
		map = metrics.MCalls(projectDir, LOC_method, CYCLO_method, WMC_class, NOM_class, operadorLogico);
		for (int i = 1; i <= map.size(); i++) {
			String[] c = new String[10];
			c = map.get(i);
			expectedlList.add(c[6]);
		}
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
	void testLocMethod() {
		assertEquals(expectedlList, actualList);

	}

	@Test
	void testNumberOfLines() {
		assertEquals(numberLines, metrics.NumberOfLines(map));

	}

}
