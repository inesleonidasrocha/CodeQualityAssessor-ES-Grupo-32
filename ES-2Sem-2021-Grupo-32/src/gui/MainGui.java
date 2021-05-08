package gui;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.HeadlessException;

public class MainGui {
	private JFrame frame;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void createFrame(Gui guiFrame) throws HeadlessException {
		guiFrame.setFrame(new JFrame("CodeQualityAssessor"));
		frame.setResizable(false);
		frame.setBounds(100, 100, 1145, 528);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}