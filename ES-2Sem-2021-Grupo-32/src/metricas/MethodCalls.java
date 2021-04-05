package metricas;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MethodCalls {
	static HashMap<Integer, String> methodID = new HashMap<Integer, String>();
	static Integer count = 0;

	public static void MethodCalls(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			//System.out.println(path);
			//System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				StaticJavaParser.getConfiguration().setAttributeComments(false);

				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						List<MethodDeclaration> methods = n.getMethods();
						
						for (MethodDeclaration a : methods) {
							count++;
							methodID.put(count, a.getName().toString());
							
						}
						
					}
				}.visit(StaticJavaParser.parse(file), null);
			//	System.out.println(); // empty line
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
		System.out.println(methodID);
	}

	public static void main(String[] args) {
		File projectDir = new File("C:\\Users\\Bombas\\Pictures\\ES_eclipse\\test");
		MethodCalls(projectDir);
	}
}
