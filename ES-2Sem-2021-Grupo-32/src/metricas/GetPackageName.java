package metricas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class GetPackageName {
	static List<String> p = new ArrayList<>();

	// Fazer hashmap
	public static void listPackageName(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			// CompilationUnit statement = StaticJavaParser.parse(file);
			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				if (cu.getPackageDeclaration().isPresent()) {
					String pack = cu.getPackageDeclaration().get().toString();
					String b =pack.trim();
					System.out.println(b.replace(";", "") + "\n");
				} else {
					System.out.println("default package" + "\n");
				}
				// p.add(packageName);
				 //System.out.println(); // empty line
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void main(String[] args) {
		File projectDir = new File("src");
		listPackageName(projectDir);
	}
}
