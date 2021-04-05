package metricas;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class WMC_class {
	static int complexity = 0;

	public static void ComplexityClass(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				StaticJavaParser.getConfiguration().setAttributeComments(false);

				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						System.out.println(" * " + n.getName());
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
						System.out.println("while : " + n.findAll(WhileStmt.class).size());
						System.out.println("for : " + n.findAll(ForStmt.class).size());
						System.out.println("forEach : " + n.findAll(ForEachStmt.class).size());
						System.out.println("if : " + complexity); // Este if terá que ser um método à parte
						System.out.println("case : " + n.findAll(SwitchStmt.class).size());
						complexity = 0;

					}
				}.visit(StaticJavaParser.parse(file), null);
				System.out.println(); // empty line
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void main(String[] args) {
		File projectDir = new File("src/files");
		ComplexityClass(projectDir);

	}
}
