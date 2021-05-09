package gui;

import java.awt.*;
import javax.swing.*;
/**
 * @author Beatriz Lima
 * @author Maria Inês Monteiro
 */
public class ProgressBar {
	static JFrame frame;

	public static void Bar() {

		frame = new JFrame("CodeQualityAssessor");
		frame.setBounds(500, 350, 500, 500);
		// creates progress bar
		JLabel aguarde = new JLabel("Por favor, aguarde...");
		aguarde.setBounds(250, 100, 148, 16);
		frame.add(aguarde);
		JProgressBar bar = new JProgressBar();

		bar.setPreferredSize(new Dimension(450, 50));
		bar.setIndeterminate(true);
		bar.setBackground(new Color(255, 255, 255));
		frame.setLayout(new FlowLayout());
		frame.add(bar);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(300, 80);

		frame.setVisible(true);

	}

	public static void removeBar() {
		frame.dispose();
	}
}
