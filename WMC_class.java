package metricas;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.google.common.base.Strings;

public class WMC_class {
	static int complexity = 0;

	public static void listCase(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {

				CompilationUnit cu = StaticJavaParser.parse(file);
				for (SwitchStmt c : cu.getNodesByType(SwitchStmt.class)) {
					// We found an "if" - cool, add one.
					if (c.isSwitchStmt()) {
						complexity++;
					}
					System.out.println(c);

				}

				System.out.println(complexity);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void listForEach(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				for (ForEachStmt forEStmt : cu.getChildNodesByType(ForEachStmt.class)) {
					// We found an "if" - cool, add one.
					if (forEStmt.isForEachStmt()) {
						complexity++;
					}
					System.out.println(forEStmt);

				}

				System.out.println(complexity);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void listFor(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				for (ForStmt forStmt : cu.getChildNodesByType(ForStmt.class)) {
					// We found an "if" - cool, add one.
					if (forStmt.isForEachStmt()) {
						complexity++;
					}
					System.out.println(forStmt);

				}

				System.out.println(complexity);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void listWhile(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				for (WhileStmt w : cu.getChildNodesByType(WhileStmt.class)) {
					// We found an "if" - cool, add one.
					if (w.isWhileStmt()) {
						complexity++;
					}
					System.out.println(w);

				}

				System.out.println(complexity);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void listIf(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				for (IfStmt ifStmt : cu.getChildNodesByType(IfStmt.class)) {
					// We found an "if" - cool, add one.
					complexity++;
					System.out.println(ifStmt);
					if (ifStmt.getElseStmt().isPresent()) {
						// This "if" has an "else"
						Statement elseStmt = ifStmt.getElseStmt().get();
						if (elseStmt instanceof IfStmt) {
							// it's an "else-if". We already count that by counting the "if" above.
						} else {
							// it's an "else-something". Add it.
							complexity++;
							System.out.println(elseStmt);
						}
					}
				}
				System.out.println(complexity);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void main(String[] args) {
		File projectDir = new File("src/files");
		listIf(projectDir);
		listCase(projectDir);
		listWhile(projectDir);
		listFor(projectDir);
		listForEach(projectDir);
		System.out.println(complexity);
	}
}
