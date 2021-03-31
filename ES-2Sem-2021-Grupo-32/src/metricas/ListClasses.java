package metricas;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;

public class ListClasses {

	public static void listClasses(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						System.out.println(" * " + n.getName());
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
		listClasses(projectDir);
	}
}