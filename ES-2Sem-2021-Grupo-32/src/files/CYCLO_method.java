package files;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


/*correspondente à complexidade ciclomática do método. 
 * (Para efeitos deste projeto, a complexidade ciclomática de um método deve ser 
 * calculada da seguinte forma: Somatório da quantidade de instruções 
 * “if”, “case”, “for”, “while” e outros ciclos, acrescentado de 1);*/

public class CYCLO_method {
	private static int ifCount = 0;
	private static int caseCount = 0;
	private static int forCount = 0;
	private static int whileCount = 0;

	public static void main(String[] args) {

		String path = "src/files";
		nComplexidades(path);

	}
//if
	public static void nComplexidades(String path) {
		File[] files = new File(path).listFiles();
		for (File a : files) {
			try {
				Scanner b = new Scanner(a);
				String body = "";

				while (b.hasNext()) {
					body = b.nextLine();
					if (body.contains("if")) {
						ifCount++;
					}
					if (body.contains("case")) {
						caseCount++;
					}
					if (body.contains("for")) {
						forCount++;
					}
					if (body.contains("while")) {
						whileCount++;
					}
				}
				b.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}
			System.out.println(ifCount + caseCount + forCount + whileCount);
			ifCount = 0;
			caseCount = 0;
			forCount = 0;
			whileCount = 0;

		}

	}
	public class Cars {
		int i = 0;
		int a = 0;
	public Cars() {
		if (i==0)
			a++;
	}
		
	}
}

	

