package gui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import metrics.GetMetrics;
import metrics.JavaToExcel;

public class OpenDir extends Thread {
	private File file;
	private HashMap<Integer, String[]> metrics;
	private JLabel Npackages;
	private JLabel Nclasses;
	private JLabel Nmethods;
	private JLabel Nlinhas;
	private JTextField textField;
	private Gui gui;

	public OpenDir(HashMap<Integer, String[]> metrics, File file, JLabel Npackages, JLabel Nclasses, JLabel Nmethods,
			JLabel Nlinhas, JTextField textField, Gui gui) {
		this.metrics = metrics;
		this.file = file;
		this.Npackages = Npackages;
		this.Nclasses = Nclasses;
		this.Nmethods = Nmethods;
		this.textField = textField;
		this.Nlinhas = Nlinhas;
		this.gui = gui;
	}

	public void run() {
		ProgressBar.Bar();
		if (GetMetrics.JavaFilesArePresent(file)) {
			excelGui.dataToExcel(metrics);
			(new JavaToExcel(file.getPath())).start();
			Npackages.setText("Numero total de packages:" + String.valueOf(GetMetrics.NumberOfPack(metrics)));

			Nclasses.setText("Numero total de classes:" + String.valueOf(GetMetrics.NumberOfClasses(metrics)));

			Nmethods.setText("Numero total de metodos:" + String.valueOf(GetMetrics.NumberOfMethods(metrics)));
			Nlinhas.setText(
					"Numero de linhas de codigo do projeto:" + String.valueOf(GetMetrics.NumberOfLines(metrics)));

			try {
				gui.dataToGraphic(metrics);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "Operaçao concluida", "CodeQualityAssessor",
					JOptionPane.PLAIN_MESSAGE);

			ProgressBar.removeBar();
		} else {
			textField.setText("");
			JFrame warning = new JFrame("");
			JOptionPane.showMessageDialog(warning, "Por favor, introduza um projeto com ficheiros .java!",
					"CodeQualityAssessor", JOptionPane.WARNING_MESSAGE);
			ProgressBar.removeBar();
		}

	}
}
