package codeSmells;

import java.util.HashMap;

import gui.excelGui;
import metrics.GetMetrics;

/**
 * @author Mauro Afonso
 * @author Sofia Correia
 * @author Tiago Bombas
 */
public class CodeSmellsAnalyser extends Thread {
	private HashMap<Integer, String[]> MCalls;
	private static int LOC_method;
	private static int CYCLO_method;
	private static int WMC_class;
	private static int NOM_class;
	private static String oLogico;

	public CodeSmellsAnalyser(HashMap<Integer, String[]> MCalls, int LOC_method, int CYCLO_method, int WMC_class,
			int NOM_class, String oLogico) {
		this.MCalls = MCalls;
		this.LOC_method = LOC_method;
		this.CYCLO_method = CYCLO_method;
		this.WMC_class = WMC_class;
		this.NOM_class = NOM_class;
		this.oLogico = oLogico;
	}

	public HashMap<Integer, String[]> calculateIndicatores() {
		for (int i = 1; i <= MCalls.size(); i++) {
			String[] metricData = new String[10];
			metricData = MCalls.get(i);
			metricData[8] = GetMetrics.LongMethod(LOC_method, CYCLO_method, Integer.valueOf(metricData[6]),
					Integer.valueOf(metricData[7]), oLogico);
			metricData[9] = GetMetrics.GodClass(WMC_class, NOM_class, Integer.valueOf(metricData[5]),
					Integer.valueOf(metricData[3]), oLogico);
			MCalls.put(i, metricData);

		}
		return MCalls;
	}

	public void run() {
		excelGui.dataToExcel(calculateIndicatores());
	}
}
