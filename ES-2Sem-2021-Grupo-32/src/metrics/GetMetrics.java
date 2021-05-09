package metrics;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tiago Bombas
 * 
 */
public class GetMetrics {
	private static HashMap<Integer, String[]> metrics;
	private static Integer metricsID = 0;
	private static CompilationUnit compUnit;

	public static boolean JavaFilesArePresent(File projectDir) {

		DirExplorer a = new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
		});
		a.isDirEmpty(0, "", projectDir);
		return a.isDirEmpty(0, "", projectDir);

	}

	public static HashMap<Integer, String[]> extractMetrics(File projectDir, int LOC_method, int CYCLO_method,
			int WMC_class, int NOM_class, String oLogico) {
		metrics = new HashMap<Integer, String[]>();
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			try {
				StaticJavaParser.getConfiguration().setAttributeComments(false);

				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration declaration, Object arg) {
						super.visit(declaration, arg);
						String packageName = "";

						try {
							compUnit = StaticJavaParser.parse(file);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (compUnit.getPackageDeclaration().isPresent()) {
							String packageBeforeTrim = compUnit.getPackageDeclaration().get().toString();

							String packageAfterTrim = packageBeforeTrim.trim().replace(";", "");

							String[] pathSplit = path.split("/");
							String[] pName = { packageAfterTrim.replace("package ", ""),
									pathSplit[pathSplit.length - 1] };
							packageName = pName[0];

						} else {

							String[] packageAfterS = path.split("/");
							String defaultPac = "default package";
							String[] pName = { defaultPac, packageAfterS[packageAfterS.length - 1] };
							packageName = pName[0];

						}
						List<MethodDeclaration> methods = declaration.getMethods();

						for (MethodDeclaration a : methods) {

							if (!declaration.isInterface()) {
								String[] allMetrics = new String[10];
								allMetrics[2] = packageName; // Package Name
								allMetrics[3] = NomClass(methods); // NOM_CLASS
								allMetrics[0] = declaration.getName().toString(); // Class Name
								allMetrics[4] = LOC_Class(declaration, arg); // LOC_Class
								allMetrics[5] = WMC_class(declaration, arg); // WMC_Class
								allMetrics[6] = LOC_method(a); // LOC_method
								allMetrics[7] = CYCLO_method(a);
								metricsID++;

								// Extraction of the parameters of the methods
								String gParameters = a.getParameters().toString().replace("[", "").replace("]", "")
										.replace(",", "");
								String[] parameterAfterSplit = gParameters.split(" ");
								String parameters = "";
								for (int i = 0; i < parameterAfterSplit.length; i = i + 2) {
									parameters = parameters + parameterAfterSplit[i] + ",";
								}
								// Method Name and its parameters
								allMetrics[1] = a.getName().toString() + "("
										+ parameters.substring(0, parameters.length() - 1) + ")";

								// LongMethod and GodClass
								allMetrics[8] = LongMethod(LOC_method, CYCLO_method, Integer.valueOf(allMetrics[6]),
										Integer.valueOf(allMetrics[7]), oLogico);
								allMetrics[9] = GodClass(WMC_class, NOM_class, Integer.valueOf(allMetrics[5]),
										Integer.valueOf(allMetrics[3]), oLogico);
								metrics.put(metricsID, allMetrics);
							}
						}

					}

				}.visit(StaticJavaParser.parse(file), null);

			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
		metricsID = 0;
		return metrics;
	}

	public static HashMap<Integer, String[]> calculateIndicatores(HashMap<Integer, String[]> extractedMetrics,
			int LOC_method, int CYCLO_method, int WMC_class, int NOM_class, String oLogico) {
		for (int i = 1; i <= extractedMetrics.size(); i++) {
			String[] metricData = new String[10];
			metricData = extractedMetrics.get(i);
			metricData[8] = LongMethod(LOC_method, CYCLO_method, Integer.valueOf(metricData[6]),
					Integer.valueOf(metricData[7]), oLogico);
			metricData[9] = GodClass(WMC_class, NOM_class, Integer.valueOf(metricData[5]),
					Integer.valueOf(metricData[3]), oLogico);
			extractedMetrics.put(i, metricData);

		}
		return extractedMetrics;
	}

	public static String NomClass(List<MethodDeclaration> method) {
		return Integer.toString(method.size());
	}

	public static String LOC_method(MethodDeclaration method) {
		return Long.toString(method.toString().lines().count());
	}

	public static String LongMethod(int LOC_method, int CYCLO_method, int ruleLoc, int ruleCyclo,
			String logicalOperator) {
		if (logicalOperator.equals("AND")) {
			if (ruleLoc > LOC_method && ruleCyclo > CYCLO_method)
				return "True";
			else
				return "False";
		} else {
			if (ruleLoc > LOC_method || ruleCyclo > CYCLO_method)
				return "True";
			else
				return "False";
		}
	}

	public static String GodClass(int WMC_class, int NOM_class, int ruleWmc, int ruleNom, String logicalOperator) {
		if (logicalOperator.equals("AND")) {
			if (ruleWmc > WMC_class && ruleNom > NOM_class)
				return "True";
			else
				return "False";
		} else {
			if (ruleWmc > WMC_class || ruleNom > NOM_class)
				return "True";
			else
				return "False";
		}
	}

	public static int NumberOfPack(HashMap<Integer, String[]> HashMetrics) {
		List<String> pack = new ArrayList<>();
		for (int i = 1; i < HashMetrics.size(); i++) {
			String[] allMetrics = HashMetrics.get(i);

			if (!pack.contains(allMetrics[2])) {
				pack.add(allMetrics[2]);

			}
		}
		return pack.size();
	}

	public static int NumberOfClasses(HashMap<Integer, String[]> HashMetrics) {
		List<String> pack = new ArrayList<>();
		for (int i = 1; i < HashMetrics.size(); i++) {
			String[] allMetrics = HashMetrics.get(i);
			if (!pack.contains(allMetrics[0])) {
				pack.add(allMetrics[0]);

			}
		}
		return pack.size();
	}

	public static int NumberOfLines(HashMap<Integer, String[]> HashMetrics) {
		List<String> pack = new ArrayList<>();
		int lines = 0;
		for (int i = 1; i < HashMetrics.size(); i++) {
			String[] allMetrics = HashMetrics.get(i);
			if (!pack.contains(allMetrics[0])) {
				pack.add(allMetrics[0]);
				lines = lines + Integer.valueOf(allMetrics[4]);
			}
		}
		return lines;
	}

	public static int NumberOfMethods(HashMap<Integer, String[]> HashMetrics) {
		return HashMetrics.size();
	}

	public static String WMC_class(ClassOrInterfaceDeclaration declaration, Object arg) {
		int complexity = 0;

		for (IfStmt ifStmt : declaration.getChildNodesByType(IfStmt.class)) {
			complexity++;
			if (ifStmt.getElseStmt().isPresent()) {

				Statement elseStmt = ifStmt.getElseStmt().get();
				if (elseStmt instanceof IfStmt) {

				} else {
					complexity++;
				}
			}
		}
		complexity = complexity + declaration.findAll(WhileStmt.class).size();
		complexity = complexity + declaration.findAll(ForStmt.class).size();
		complexity = complexity + declaration.findAll(ForEachStmt.class).size();
		complexity = complexity + declaration.findAll(SwitchStmt.class).size();

		return Integer.toString(complexity);
	}

	public static String LOC_Class(ClassOrInterfaceDeclaration classDeclaration, Object arg) {
		int blankLines = nBlank(classDeclaration);
		int totalLines = (int) classDeclaration.toString().lines().count();

		return Integer.toString(totalLines - blankLines);

	}

	private static int nBlank(ClassOrInterfaceDeclaration classDeclaration) {
		int nBlank = 0;
		Object[] lines = classDeclaration.toString().lines().toArray();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].toString().isBlank())
				nBlank++;
		}
		return nBlank;
	}

	public static String CYCLO_method(MethodDeclaration method) {
		int complexity = 0;

		for (IfStmt ifStmt : method.getChildNodesByType(IfStmt.class)) {

			complexity++;
			if (ifStmt.getElseStmt().isPresent()) {

				Statement elseStmt = ifStmt.getElseStmt().get();
				if (elseStmt instanceof IfStmt) {

				} else {

					complexity++;
				}
			}
		}
		complexity = complexity + method.findAll(WhileStmt.class).size();
		complexity = complexity + method.findAll(ForStmt.class).size();
		complexity = complexity + method.findAll(ForEachStmt.class).size();
		complexity = complexity + method.findAll(SwitchStmt.class).size();

		return Integer.toString(complexity);
	}

}
