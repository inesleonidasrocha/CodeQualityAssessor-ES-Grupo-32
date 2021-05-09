package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

import codeSmells.CodeSmellsAnalyser;
import metrics.GetMetrics;
import metrics.JavaToExcel;

@SuppressWarnings("deprecation")
public class Gui extends javax.swing.JFrame {

	private MainGui frameProduct = new MainGui();
	private excelGui excelGui = new excelGui();
	private JFrame hist;
	private JTextField textField;
	private JLabel Nlinhas;
	private JLabel Npackages;
	private JLabel Nclasses;
	private JLabel Nmethods;
	private String path = "";
	private File file = new File(path);
	private JFileChooser chooser = new JFileChooser();
	private int LOC_method = 50;
	private int CYCLO_method = 10;
	private int WMC_class = 50;
	private int NOM_class = 10;
	private static HashMap<Integer, String[]> methodID;
	private JPanel importProject;
	// Grafico
	private JLabel GVPositive;
	private JLabel GVNegative;
	private JLabel GFPositive;
	private JLabel GFNegative;
	private JLabel LVPositive;
	private JLabel LVNegative;
	private JLabel LFPositive;
	private JLabel LFNegative;
	private JPanel graph;

	private ChartPanel chartPanelGod;
	private ChartPanel chartPanelLong;
	private DefaultPieDataset<String> pieDataSet2 = new DefaultPieDataset<String>();
	private DefaultPieDataset<String> pieDataSetGod = new DefaultPieDataset<String>();

	private int VPGod = 0;
	private int VNGod = 0;
	private int FPGod = 0;
	private int FNGod = 0;
	private int VPLong = 0;
	private int VNLong = 0;
	private int FPLong = 0;
	private int FNLong = 0;
	// Criar regra
	private JFormattedTextField textField_LocMethod = new JFormattedTextField();
	private JFormattedTextField textField_CycloMethod = new JFormattedTextField();
	private JTextField textField_WmcClass = new JTextField();
	private JTextField textField_NomClass = new JTextField();
	private String oLogico = "AND"; // operador logico
	private Gui guiFrame = this;
	private boolean isJasml = true;

	public Gui() throws IOException {
		initialize();
	}

	public void dataToGraphic(HashMap<Integer, String[]> methodID) throws IOException {

		ExcelReader rc = new ExcelReader(methodID);
		rc.getCodeSmell();

		graph.removeAll();
		VPGod = rc.indicatorToGui("VPGod");
		VNGod = rc.indicatorToGui("VNGod");
		FPGod = rc.indicatorToGui("FPGod");
		FNGod = rc.indicatorToGui("FNGod");
		VPLong = rc.indicatorToGui("VPLong");
		VNLong = rc.indicatorToGui("VNLong");
		FPLong = rc.indicatorToGui("FPLong");
		FNLong = rc.indicatorToGui("FNLong");

		if (rc.indicatorToGui("VPGod") != 0 || rc.indicatorToGui("VNGod") != 0 || FPGod != 0 || FNGod != 0
				|| VPLong != 0 || VNLong != 0 || FPLong != 0 || FNLong != 0) {
			isJasml = true;

			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(522, 6, 12, 411);
			graph.add(separator);

			JLabel godClass = new JLabel("IS_GOD_CLASS");
			godClass.setBounds(199, 6, 113, 16);
			graph.add(godClass);

			JLabel longMethod = new JLabel("IS_LONG_METHOD");
			longMethod.setBounds(766, 6, 148, 16);
			graph.add(longMethod);
			GVPositive = new JLabel("Numero total de Verdadeiros Positivos: " + VPGod);
			GVPositive.setBounds(18, 61, 300, 21);
			graph.add(GVPositive);

			GVNegative = new JLabel("Numero total de Verdadeiros Negativos: " + VNGod);
			GVNegative.setBounds(18, 83, 300, 21);
			graph.add(GVNegative);

			GFPositive = new JLabel("Numero total de Falsos Positivos: " + FPGod);
			GFPositive.setBounds(18, 106, 300, 21);
			graph.add(GFPositive);

			GFNegative = new JLabel("Numero total de Falsos Negativos: " + FNGod);
			GFNegative.setBounds(18, 128, 300, 21);
			graph.add(GFNegative);

			LVPositive = new JLabel("Numero total de Verdadeiros Positivos: " + VPLong);
			LVPositive.setBounds(546, 63, 300, 21);
			graph.add(LVPositive);

			LVNegative = new JLabel("Numero total de Verdadeiros Negativos: " + VNLong);
			LVNegative.setBounds(546, 85, 300, 21);
			graph.add(LVNegative);

			LFPositive = new JLabel("Numero total de Falsos Positivos: " + FPLong);
			LFPositive.setBounds(546, 108, 300, 21);
			graph.add(LFPositive);

			LFNegative = new JLabel("Numero total de Falsos Negativos: " + FNLong);
			LFNegative.setBounds(546, 130, 300, 21);
			graph.add(LFNegative);

			createGraph();
		} else {
			isJasml = false;
			JLabel pieImage = new JLabel("");
			pieImage.setIcon(new ImageIcon("Pie-Chart250.png"));
			pieImage.setBounds(29, 261, 345, 156);
			graph.add(pieImage);
			JLabel pie = new JLabel("Calculo de graficos indisponivel para este projeto.");
			pie.setBounds(270, 161, 1000, 100);
			pie.setFont(new Font("Lucida Grande", Font.BOLD, 23));
			graph.add(pie);

		}

	}

	public void createGraph() {
		pieDataSet2.clear();
		pieDataSetGod.clear();
		pieDataSetGod.setValue("Verdadeiro Positivo", Double.valueOf(VPGod));
		pieDataSetGod.setValue("Verdadeiro Negativo", Double.valueOf(VNGod));
		pieDataSetGod.setValue("Falso Positivo", Double.valueOf(FPGod));
		pieDataSetGod.setValue("Falso Negativo", Double.valueOf(FNGod));

		JFreeChart chartGod = ChartFactory.createPieChart3D("God Class Chart", pieDataSetGod, true, true, true);

		PiePlot3D plotGod = (PiePlot3D) chartGod.getPlot();
		plotGod.setStartAngle(0);
		plotGod.setDirection(Rotation.CLOCKWISE);
		plotGod.setForegroundAlpha(0.8f);
		chartPanelGod = new ChartPanel(chartGod);
		chartPanelGod.setBounds(18, 179, 491, 227);
		chartPanelGod.setVisible(true);
		graph.add(chartPanelGod);

		// cores do grafico god class
		plotGod.setSectionPaint("Verdadeiro Positivo", Color.GREEN);
		plotGod.setSectionPaint("Verdadeiro Negativo", new Color(0, 181, 37));
		plotGod.setSectionPaint("Falso Positivo", new Color(245, 55, 55));
		plotGod.setSectionPaint("Falso Negativo", new Color(247, 160, 4));

		pieDataSet2.setValue("Verdadeiro Positivo", Double.valueOf(VPLong));
		pieDataSet2.setValue("Verdadeiro Negativo", Double.valueOf(VNLong));
		pieDataSet2.setValue("Falso Positivo", Double.valueOf(FPLong));
		pieDataSet2.setValue("Falso Negativo", Double.valueOf(FNLong));

		JFreeChart chartLong = ChartFactory.createPieChart3D("Long Method Chart ", pieDataSet2, true, true, true);

		@SuppressWarnings("rawtypes")
		PiePlot plotLong = (PiePlot) chartLong.getPlot();
		chartPanelLong = new ChartPanel(chartLong);
		chartPanelLong.setBounds(546, 179, 532, 227);
		plotLong.setStartAngle(0);
		plotLong.setDirection(Rotation.CLOCKWISE);
		plotLong.setForegroundAlpha(0.8f);
		chartPanelLong = new ChartPanel(chartLong);
		chartPanelLong.setBounds(546, 179, 532, 227);
		chartPanelLong.setVisible(true);
		graph.add(chartPanelLong);

		// Cores do grafico long method
		plotLong.setSectionPaint("Verdadeiro Positivo", Color.GREEN);
		plotLong.setSectionPaint("Verdadeiro Negativo", new Color(0, 181, 37));
		plotLong.setSectionPaint("Falso Positivo", new Color(245, 55, 55));
		plotLong.setSectionPaint("Falso Negativo", new Color(247, 160, 4));

	}

	public void creatLong() {

		JFrame longMethod = new JFrame("Long Method");
		longMethod.getContentPane().setForeground(SystemColor.controlHighlight);
		longMethod.setBounds(100, 100, 473, 192);
		longMethod.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		longMethod.getContentPane().setLayout(null);

		JLabel loc = new JLabel("LOC_method  >");
		loc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loc.setBounds(10, 56, 118, 24);
		longMethod.getContentPane().add(loc);

		JLabel cycloMethod = new JLabel("CYCLO_method  >");
		cycloMethod.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cycloMethod.setBounds(262, 51, 138, 35);
		longMethod.getContentPane().add(cycloMethod);

		textField_LocMethod = new JFormattedTextField(LOC_method);
		textField_LocMethod.setBounds(120, 61, 50, 19);
		longMethod.getContentPane().add(textField_LocMethod);
		textField_LocMethod.setColumns(10);
		textField_LocMethod.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					textField_LocMethod.setEditable(true);

				} else {
					textField_LocMethod.setEditable(false);
					if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE
							|| e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
						textField_LocMethod.setEditable(true);
					}
				}
			}

		});
		textField_CycloMethod = new JFormattedTextField(CYCLO_method);
		textField_CycloMethod.setColumns(10);
		textField_CycloMethod.setBounds(399, 61, 50, 19);
		longMethod.getContentPane().add(textField_CycloMethod);
		textField_CycloMethod.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					textField_CycloMethod.setEditable(true);

				} else {
					textField_CycloMethod.setEditable(false);
					if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE
							|| e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
						textField_CycloMethod.setEditable(true);
					}
				}
			}

		});
		JLabel longMethodLabel = new JLabel("Long Method");
		longMethodLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		longMethodLabel.setBounds(10, 10, 133, 31);
		longMethod.getContentPane().add(longMethodLabel);

		JButton createButton = new JButton("Create");
		createButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		createButton.setBounds(325, 113, 85, 21);
		longMethod.getContentPane().add(createButton);
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!file.exists()) {
					JOptionPane.showMessageDialog(null, "Por favor, introduza um projeto.");
				} else {

					if (!textField_LocMethod.getText().equals("0") && !textField_CycloMethod.getText().equals("0")) {

						LOC_method = Integer.valueOf(textField_LocMethod.getText());
						CYCLO_method = Integer.valueOf(textField_CycloMethod.getText());

						(new CodeSmellsAnalyser(methodID, LOC_method, CYCLO_method, WMC_class, NOM_class, oLogico))
								.run();
						longMethod.dispose();
						try {
							if (isJasml == true) {

								HashMap<Integer, String[]> metrics = GetMetrics.calculateIndicatores(methodID,
										LOC_method, CYCLO_method, WMC_class, NOM_class, oLogico);
								dataToGraphic(metrics);

							}
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							saveRules(path, oLogico, textField_LocMethod.getText(), textField_CycloMethod.getText(),
									"LOC_method", "CYCLO_method");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Concluido");
					} else {
						JFrame frame2 = new JFrame("");
						JOptionPane.showMessageDialog(frame2, "Por favor, introduza valores superiores a 0!",
								"CodeQualityAssessor", JOptionPane.WARNING_MESSAGE);

					}

				}

			}
		});

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("AND");
		model.addElement("OR");
		JComboBox<String> list = new JComboBox<String>(model);
		list.setBounds(195, 61, 70, 19);
		longMethod.getContentPane().add(list);
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oLogico = (String) list.getSelectedItem();
			}
		});
		longMethod.setVisible(true);
	}

	public void creatGod() {
		JFrame godClassFrame = new JFrame("God Class");
		godClassFrame.getContentPane().setForeground(SystemColor.controlHighlight);
		godClassFrame.setBounds(100, 100, 473, 192);
		godClassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		godClassFrame.getContentPane().setLayout(null);

		JLabel loc = new JLabel("WMC_class  >");
		loc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loc.setBounds(10, 56, 118, 24);
		godClassFrame.getContentPane().add(loc);

		JLabel nomClassLabel = new JLabel("NOM_class  >");
		nomClassLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nomClassLabel.setBounds(262, 51, 138, 35);
		godClassFrame.getContentPane().add(nomClassLabel);

		textField_WmcClass = new JFormattedTextField(WMC_class);
		textField_WmcClass.setBounds(120, 61, 50, 19);
		godClassFrame.getContentPane().add(textField_WmcClass);
		textField_WmcClass.setColumns(10);
		textField_WmcClass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					textField_WmcClass.setEditable(true);
				} else {
					textField_WmcClass.setEditable(false);
					if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE
							|| e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
						textField_WmcClass.setEditable(true);
					}
				}
			}

		});
		textField_NomClass = new JFormattedTextField(NOM_class);
		textField_NomClass.setColumns(10);
		textField_NomClass.setBounds(399, 61, 50, 19);
		godClassFrame.getContentPane().add(textField_NomClass);
		textField_NomClass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					textField_NomClass.setEditable(true);

				} else {
					textField_NomClass.setEditable(false);
					if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE
							|| e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
						textField_NomClass.setEditable(true);
					}
				}
			}

		});
		JLabel godClassLabel = new JLabel("God Class");
		godClassLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		godClassLabel.setBounds(10, 10, 133, 31);
		godClassFrame.getContentPane().add(godClassLabel);

		JButton createButton = new JButton("Create");
		createButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		createButton.setBounds(325, 113, 85, 21);
		godClassFrame.getContentPane().add(createButton);
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!file.exists()) {
					JOptionPane.showMessageDialog(null, "Por favor, introduza um projeto.");
				} else {
					if (!textField_WmcClass.getText().equals("0") && !textField_NomClass.getText().equals("0")) {
						WMC_class = Integer.valueOf(textField_WmcClass.getText());
						NOM_class = Integer.valueOf(textField_NomClass.getText());

						(new CodeSmellsAnalyser(methodID, LOC_method, CYCLO_method, WMC_class, NOM_class, oLogico))
								.run();
						godClassFrame.dispose();
						try {
							if (isJasml == true) {
								HashMap<Integer, String[]> metrics = GetMetrics.calculateIndicatores(methodID,
										LOC_method, CYCLO_method, WMC_class, NOM_class, oLogico);
								dataToGraphic(metrics);
							}
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							saveRules(path, oLogico, textField_WmcClass.getText(), textField_NomClass.getText(),
									"WMC_class", "NOM_class");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JFrame frame = new JFrame("");
						JOptionPane.showMessageDialog(frame, "Concluido", "CodeQualityAssessor",
								JOptionPane.PLAIN_MESSAGE);
					} else {
						JFrame frame = new JFrame("");
						JOptionPane.showMessageDialog(frame, "Por favor, introduza valores superiores a 0!",
								"CodeQualityAssessor", JOptionPane.WARNING_MESSAGE);

					}
				}
			}
		});
		JComboBox<String> list = listRules();
		godClassFrame.getContentPane().add(list);
		godClassFrame.setVisible(true);
	}

	private JComboBox<String> listRules() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("AND");
		model.addElement("OR");
		JComboBox<String> list = new JComboBox<String>(model);
		list.setBounds(195, 61, 50, 19);
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) list.getSelectedItem();
			}
		});
		return list;
	}

	public void ReadRules(String path) throws IOException {
		hist = new JFrame("Historico");
		hist.setBounds(100, 100, 300, 228);
		hist.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		File rules = new File(path + "/HistoryRules.txt");

		DefaultListModel<Object> modelList = new DefaultListModel<>();
		if (rules.exists()) {

			FileReader reader = new FileReader(rules);
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			String text = "";
			while ((line = bufferedReader.readLine()) != null) {
				text = line.replace("\n", "");
				modelList.addElement(text);
			}

			JList<Object> listj = new JList<Object>(modelList);
			listj.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			hist.getContentPane().add(listj, BorderLayout.CENTER);

			JScrollPane scrollPane = new JScrollPane(listj);
			hist.getContentPane().add(scrollPane, BorderLayout.CENTER);

			JButton visualize = new JButton("Visualizar");
			hist.getContentPane().add(visualize, BorderLayout.SOUTH);

			visualize.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selectedValue = (String) listj.getSelectedValue();
					String selectedValueSplit[] = selectedValue.split(" ");
					if (selectedValueSplit[0].equals("LOC_method")) {
						LOC_method = Integer.valueOf(selectedValueSplit[2]);
						CYCLO_method = Integer.valueOf(selectedValueSplit[6]);

						(new CodeSmellsAnalyser(methodID, LOC_method, CYCLO_method, WMC_class, NOM_class,
								selectedValueSplit[3])).run();

						try {
							if (isJasml == true) {
								HashMap<Integer, String[]> metrics = GetMetrics.calculateIndicatores(methodID,
										LOC_method, CYCLO_method, WMC_class, NOM_class, selectedValueSplit[3]);
								dataToGraphic(metrics);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						hist.dispose();
					} else {
						WMC_class = Integer.valueOf(selectedValueSplit[2]);
						NOM_class = Integer.valueOf(selectedValueSplit[6]);

						(new CodeSmellsAnalyser(methodID, LOC_method, CYCLO_method, WMC_class, NOM_class,
								selectedValueSplit[3])).run();
						try {
							if (isJasml == true) {
								HashMap<Integer, String[]> metrics = GetMetrics.calculateIndicatores(methodID,
										LOC_method, CYCLO_method, WMC_class, NOM_class, selectedValueSplit[3]);
								dataToGraphic(metrics);
							}

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						hist.dispose();
					}
					JFrame frame = new JFrame("");
					JOptionPane.showMessageDialog(frame, "Concluido", "CodeQualityAssessor", JOptionPane.PLAIN_MESSAGE);

				}
			});
			hist.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Ainda nao criou nenhuma regra para o projeto importado!");

		}

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	public void saveRules(String path, String oLogico, String firstNumber, String secondNumber, String firstMetric,
			String secondMetric) throws IOException {

		File file = new File(path + "/HistoryRules.txt");
		if (!file.exists()) {
			try {
				FileWriter myWriter = writeSavedRule(oLogico, firstNumber, secondNumber, firstMetric, secondMetric,
						file);
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			String text = "";
			String regra = firstMetric + " > " + firstNumber + " " + oLogico + " " + secondMetric + " > "
					+ secondNumber;
			boolean regraExiste = false;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.equals(regra)) {
					text = text + (line) + "\n";
				} else {
					regraExiste = true;
					System.out.println("Erro regra ja exite");
					break;
				}

			}
			if (regraExiste == false) {
				FileWriter myWriter = new FileWriter(file);
				text = text + (firstMetric + " > " + firstNumber + " " + oLogico + " " + secondMetric + " > "
						+ secondNumber);
				myWriter.write(text);
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			}
		}

	}

	private FileWriter writeSavedRule(String oLogico, String firstNumber, String secondNumber, String firstMetric,
			String secondMetric, File file) throws IOException {
		FileWriter myWriter = new FileWriter(file);
		myWriter.write(
				firstMetric + " > " + firstNumber + " " + oLogico + " " + secondMetric + " > " + secondNumber + "\n");
		myWriter.close();
		return myWriter;
	}

	public void searchDir() {

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(".."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {

			file = chooser.getSelectedFile();
			path = chooser.getSelectedFile().toString();
			textField.setText(path);

		} else {
			if (result == JFileChooser.CANCEL_OPTION) {
				file = null;
				path = "";
				textField.setText(path);
				System.out.println("Cancel was selected");

			}
		}

	}

	private void initialize() throws IOException {

		frameProduct.createFrame(this);
		getFrame().getContentPane().setForeground(SystemColor.controlHighlight);
		getFrame().getContentPane().setLayout(null);

		ImageIcon iconHome = new ImageIcon("homeicon.gif");
		ImageIcon iconExcel = new ImageIcon("Excel-icon.png");
		ImageIcon iconMetric = new ImageIcon("ruler.gif");
		ImageIcon iconGraph = new ImageIcon("graphIcon.gif");

		JTabbedPane importPane = new JTabbedPane(JTabbedPane.TOP);
		importPane.setForeground(SystemColor.inactiveCaptionText);
		importPane.setBounds(6, 6, 1126, 469);
		getFrame().getContentPane().add(importPane);

		importProject = getTabImportProject();
		importPane.addTab("Importar Projeto", iconHome, importProject, null);
		JPanel excelPanel = new JPanel();
		importPane.addTab("Excel", iconExcel, excelPanel, null);
		excelPanel.setLayout(null);

		JLabel metricsImage = new JLabel("");
		metricsImage.setIcon(new ImageIcon("codeQA.png"));
		metricsImage.setBounds(375, 0, 249, 139);
		importProject.add(metricsImage);

		JSeparator separator = new JSeparator();
		separator.setBounds(950, 6, 12, 411);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.GRAY);
		excelPanel.add(separator);

		JButton butaoLongRegra = new JButton("Long Method");
		butaoLongRegra.setFont(new Font("Dialog", Font.PLAIN, 12));
		butaoLongRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creatLong();

			}
		});
		butaoLongRegra.setBounds(961, 6, 131, 42);
		excelPanel.add(butaoLongRegra);

		JButton butaoGodRegra = new JButton("God Class");
		butaoGodRegra.setFont(new Font("Dialog", Font.PLAIN, 12));
		butaoGodRegra.setBounds(961, 60, 131, 42);
		excelPanel.add(butaoGodRegra);
		butaoGodRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creatGod();
			}
		});

		JButton butaoHRegra = new JButton("Historico Regras");
		butaoHRegra.setFont(new Font("Dialog", Font.PLAIN, 12));
		butaoHRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReadRules(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		butaoHRegra.setBounds(961, 114, 131, 42);
		excelPanel.add(butaoHRegra);

		excelGui.setTabelaExcel(new JTable(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED));

		excelGui.setScrollPane(new JScrollPane(excelGui.getTabelaExcel()));
		excelGui.getScrollPane().setBounds(10, 11, 928, 388);
		excelPanel.add(excelGui.getScrollPane());

		excelGui.getTabelaExcel()
				.setModel(new DefaultTableModel(
						new Object[][] { { "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-" }, },
						new String[] { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
								"is_God_class", "LOC_method", "CYCLO_method", "is_Long_Method" }));

		excelGui.getTabelaExcel().getColumnModel().getColumn(0).setMinWidth(70);
		excelGui.getTabelaExcel().getColumnModel().getColumn(1).setMinWidth(70);
		excelGui.getTabelaExcel().getColumnModel().getColumn(2).setMinWidth(20);
		excelGui.getTabelaExcel().getColumnModel().getColumn(3).setMinWidth(70);
		excelGui.getTabelaExcel().getColumnModel().getColumn(4).setMinWidth(70);
		excelGui.getTabelaExcel().getColumnModel().getColumn(5).setMinWidth(100);
		excelGui.getTabelaExcel().getColumnModel().getColumn(6).setMinWidth(100);
		excelGui.getTabelaExcel().getColumnModel().getColumn(7).setMinWidth(100);
		excelGui.getTabelaExcel().getColumnModel().getColumn(8).setMinWidth(100);
		excelGui.getTabelaExcel().getColumnModel().getColumn(9).setMinWidth(100);
		excelGui.getTabelaExcel().getColumnModel().getColumn(10).setMinWidth(100);

		excelGui.getTabelaExcel().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		excelGui.getTabelaExcel().getTableHeader().setReorderingAllowed(false);

		excelGui.getScrollPane().setViewportView(excelGui.getTabelaExcel());

		JPanel featuresPanel = new JPanel();
		importPane.addTab("Caracteristicas Gerais", iconMetric, featuresPanel, null);
		featuresPanel.setLayout(null);

		Npackages = new JLabel("Numero total de packages: ");
		Npackages.setFont(new Font("Dialog", Font.PLAIN, 14));
		Npackages.setBounds(422, 172, 436, 21);
		featuresPanel.add(Npackages);

		Nclasses = new JLabel("Numero total de classes:");
		Nclasses.setFont(new Font("Dialog", Font.PLAIN, 14));
		Nclasses.setBounds(422, 172, 436, 21);
		featuresPanel.add(Nclasses);

		Nmethods = new JLabel("Numero total de metodos:");
		Nmethods.setFont(new Font("Dialog", Font.PLAIN, 14));
		Nmethods.setBounds(422, 227, 436, 21);
		featuresPanel.add(Nmethods);

		Nlinhas = new JLabel("Numero de linhas de codigo do projeto:");
		Nlinhas.setFont(new Font("Dialog", Font.PLAIN, 14));
		Nlinhas.setBounds(422, 281, 436, 21);
		featuresPanel.add(Nlinhas);

		JLabel labelCaract = getLabelFeatures();
		featuresPanel.add(labelCaract);

		graph = new JPanel();
		importPane.addTab("Graficos", iconGraph, graph, null);
		graph.setLayout(null);

		JLabel labelR = getLabelRodape();
		getFrame().getContentPane().add(labelR);
	}

	private JPanel getTabImportProject() {
		JPanel importPanel = new JPanel();
		importPanel.setLayout(null);
		JButton importButton = new JButton("Importar Projeto");
		importButton.setFont(new Font("Dialog", Font.PLAIN, 14));
		importButton.setBounds(618, 261, 171, 50);
		importPanel.add(importButton);

		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				file = chooser.getSelectedFile();
				if (file != null) {
					methodID = GetMetrics.extractMetrics(file, 50, 10, 50, 10, "AND");
					(new OpenDir(methodID, file, Npackages, Nclasses, Nmethods, Nlinhas, textField, guiFrame)).start();
				} else {
					JFrame warning = new JFrame("");
					JOptionPane.showMessageDialog(warning, "Por favor, introduza um projeto com ficheiros .java!",
							"CodeQualityAssessor", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JLabel insertPathLabel = new JLabel("Por favor insira o path do projeto, que pretenda analisar:");
		insertPathLabel.setFont(new Font("Dialog", Font.PLAIN, 17));
		insertPathLabel.setBounds(345, 138, 431, 26);
		importPanel.add(insertPathLabel);
		textField = new JTextField();
		textField.setBounds(300, 190, 521, 43);
		importPanel.add(textField);
		textField.setColumns(10);
		JButton browse = new JButton("Selecionar Projeto");
		browse.setFont(new Font("Dialog", Font.PLAIN, 14));
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				searchDir();
			}
		});
		browse.setBounds(344, 261, 171, 50);
		importPanel.add(browse);
		return importPanel;
	}

	private JLabel getLabelRodape() {
		JLabel projectLabel = new JLabel("Projeto de Engenharia de Software 2020/2021 ");
		projectLabel.setForeground(new Color(128, 128, 128));
		projectLabel.setBounds(444, 468, 292, 24);
		return projectLabel;
	}

	private JLabel getLabelFeatures() {
		JLabel featuresLabel = new JLabel("Caracteristicas gerais do projeto");
		featuresLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		featuresLabel.setBounds(399, 53, 323, 30);
		return featuresLabel;
	}

	public JFrame getFrame() {
		return frameProduct.getFrame();
	}

	public void setFrame(JFrame frame) {
		frameProduct.setFrame(frame);
	}
}
