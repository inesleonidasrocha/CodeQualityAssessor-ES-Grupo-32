package metricas;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//.substring(line.indexOf(" "),line.indexOf("("))/
public class LOC_class {
	private static int intFlag;
	private static boolean flag;
	private static ArrayList<String> docJava = new ArrayList<String>();
	private static boolean isCommented = false;
	private static File javaFile = new File("src/files");

	public static void readFile(String path) throws FileNotFoundException {
		File[] files = new File(path).listFiles();
		for (File a : files) {
			try {
				Scanner scanner = new Scanner(a);
				int i = 0;
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine().trim();

					if (!line.isBlank() && !line.contains("import") && !line.startsWith("//") || line.contains("/*")) {
						if ((line.startsWith("/") && !line.contains("/") /* && isCommentLine(line) */ )
								|| line.startsWith("*")) {
							continue;
						} else {
							docJava.add(line);
							System.out.println(docJava.get(i));
						}
						i++;
					}
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}
		}

	}

	/*
	 * public static boolean isCommentLine(String line) { String comment; int
	 * indexStart =0; int indexEnd = 0; for(int j = 1; j < line.length(); j++) {
	 * if(line.charAt(j-1) == '/' && line.charAt(j) == '*' ) { indexStart = j-1; }
	 * if(line.charAt(line.length()-2) =='*' && line.charAt(line.length()-1) == '/')
	 * { indexEnd = line.length()-1; }
	 * 
	 * } comment = line.substring(indexStart, indexEnd); System.out.println(line +
	 * " comment:" + comment + " indeStart:" + indexStart + " indexEnd:" +
	 * indexEnd); return line.equals(comment); }
	 */

	public int Loc_class() {

		return docJava.size();
	}

	public static void main(String[] args) throws FileNotFoundException {
		readFile("src/files");

		// System.out.println(docJava.size());
	}

	public static ArrayList<String> getDocJava() {
		return docJava;
	}

}