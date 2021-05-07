package Excel;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetMetricas {
	static HashMap<Integer, String[]> methodID = new HashMap<Integer, String[]>();
	static Integer count = 0;
	static CompilationUnit cu;
	int c = 0;

	public static HashMap MCalls(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			// System.out.println(path);
			// System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				StaticJavaParser.getConfiguration().setAttributeComments(false);

				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						String px = "";

						try {
							cu = StaticJavaParser.parse(file);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (cu.getPackageDeclaration().isPresent()) {
							String pack = cu.getPackageDeclaration().get().toString();
							String b = pack.trim().replace(";", "");

							String[] p = path.split("/");
							String[] pName = { b.replace("package ", ""), p[p.length - 1] };
							px = pName[0];

						} else {

							String[] p = path.split("/");
							String x = "default package";
							String[] pName = { x, p[p.length - 1] };
							px = pName[0];

						}
						List<MethodDeclaration> methods = n.getMethods();

						for (MethodDeclaration a : methods) {
							String[] c = new String[8];
							c[2] = px;
							c[3] = Integer.toString(methods.size()); // NOM_CLASS
							c[0] = n.getName().toString();
							c[4] = LOC_Class(n, arg);
							c[5] = WMC_class(n, arg);
							c[6] = Long.toString(a.toString().lines().count()); // LOC_method
							c[7] = CYCLO_method(a);
							count++;

							String d = a.getParameters().toString().replace("[","").replace("]", "").replace(",", "");
							String []x = d.split(" ");
							System.out.println(x[2]);
							c[1] = d;

							methodID.put(count, c);

						}

					}
				}.visit(StaticJavaParser.parse(file), null);

			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

		return methodID;
	}

	public static String WMC_class(ClassOrInterfaceDeclaration n, Object arg) {
		int complexity = 0;
	//System.out.println(" * " + n.getName());
		for (IfStmt ifStmt : n.getChildNodesByType(IfStmt.class)) {
			// We found an "if" - cool, add one.
			complexity++;
			if (ifStmt.getElseStmt().isPresent()) {
				// This "if" has an "else"
				Statement elseStmt = ifStmt.getElseStmt().get();
				if (elseStmt instanceof IfStmt) {
					// it's an "else-if". We already count that by counting the "if" above.
				} else {
					// it's an "else-something". Add it.
					complexity++;
				}
			}
		}
		complexity = complexity + n.findAll(WhileStmt.class).size();
		complexity = complexity + n.findAll(ForStmt.class).size();
		complexity = complexity + n.findAll(ForEachStmt.class).size();
		complexity = complexity + n.findAll(SwitchStmt.class).size();

		return Integer.toString(complexity);
	}

	public static String LOC_Class(ClassOrInterfaceDeclaration n, Object arg) {
		int numberOfLines = 0;

		//System.out.println(" * " + n.getName());

		numberOfLines = (int) n.toString().lines().count();
		//System.out.println("Number of lines: " + numberOfLines);

		return Integer.toString(numberOfLines);

	}

	public static String CYCLO_method(MethodDeclaration a) {
		int complexity = 0;
		//System.out.println(a.toString());
		for (IfStmt ifStmt : a.getChildNodesByType(IfStmt.class)) {
			// We found an "if" - cool, add one.
			complexity++;
			if (ifStmt.getElseStmt().isPresent()) {
				// This "if" has an "else"
				Statement elseStmt = ifStmt.getElseStmt().get();
				if (elseStmt instanceof IfStmt) {
					// it's an "else-if". We already count that by counting the "if" above.
				} else {
					// it's an "else-something". Add it.
					complexity++;
				}
			}
		}
		complexity = complexity + a.findAll(WhileStmt.class).size();
		complexity = complexity + a.findAll(ForStmt.class).size();
		complexity = complexity + a.findAll(ForEachStmt.class).size();
		complexity = complexity + a.findAll(SwitchStmt.class).size();

		return Integer.toString(complexity);
	}

	public static void main(String[] args) {
		File projectDir = new File("src");
		MCalls(projectDir);
	}
}
