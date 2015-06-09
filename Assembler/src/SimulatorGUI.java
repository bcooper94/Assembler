/**
*Text genereted by Simple GUI Extension for BlueJ
*/
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.border.Border;
import javax.swing.*;


public class SimulatorGUI extends JFrame {

	private JMenuBar menuBar;
	private JTextArea Console;
	private JButton ConsoleClear;
	private JLabel consoleLabel;
	private JList instructionList;
	private JLabel instructionsLabel;
	private JButton runButton;
	private JButton stepButton;
	
	private Simulator simulator;

	//Constructor 
	public SimulatorGUI(){
	    // Set up simulator
	    simulator = new Simulator();
	    
	    // Set up GUI
		this.setTitle("SimulatorGUI");
		this.setSize(820,553);
		//menu generate method
		generateMenu();
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(820,553));
		contentPane.setBackground(new Color(192,192,192));


		Console = new JTextArea();
		Console.setBounds(413,32,400,425);
		Console.setBackground(new Color(255,255,255));
		Console.setForeground(new Color(0,0,0));
		Console.setEnabled(true);
		Console.setFont(new Font("sansserif",0,12));
		Console.setText("JTextArea");
		Console.setBorder(BorderFactory.createBevelBorder(1));
		Console.setVisible(true);

		ConsoleClear = new JButton();
		ConsoleClear.setBounds(577,485,120,35);
		ConsoleClear.setBackground(new Color(214,217,223));
		ConsoleClear.setForeground(new Color(0,0,0));
		ConsoleClear.setEnabled(true);
		ConsoleClear.setFont(new Font("sansserif",0,12));
		ConsoleClear.setText("Clear Console");
		ConsoleClear.setVisible(true);

		consoleLabel = new JLabel();
		consoleLabel.setBounds(413,5,90,35);
		consoleLabel.setBackground(new Color(214,217,223));
		consoleLabel.setForeground(new Color(0,0,0));
		consoleLabel.setEnabled(true);
		consoleLabel.setFont(new Font("sansserif",0,12));
		consoleLabel.setText("Console");
		consoleLabel.setVisible(true);

		instructionList = new JList<String>();
		instructionList.setBounds(10,32,400,425);
		instructionList.setBackground(new Color(255,255,255));
		instructionList.setForeground(new Color(0,0,0));
		instructionList.setEnabled(true);
		instructionList.setFont(new Font("sansserif",0,12));
		instructionList.setVisible(true);

		instructionsLabel = new JLabel();
		instructionsLabel.setBounds(13,5,90,35);
		instructionsLabel.setBackground(new Color(214,217,223));
		instructionsLabel.setForeground(new Color(0,0,0));
		instructionsLabel.setEnabled(true);
		instructionsLabel.setFont(new Font("sansserif",0,12));
		instructionsLabel.setText("Instructions");
		instructionsLabel.setVisible(true);

		runButton = new JButton();
		runButton.setBounds(233,485,90,35);
		runButton.setBackground(new Color(214,217,223));
		runButton.setForeground(new Color(0,0,0));
		runButton.setEnabled(true);
		runButton.setFont(new Font("sansserif",0,12));
		runButton.setText("Run");
		runButton.setVisible(true);
		//Set methods for mouse events
		//Call defined methods
		runButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				simulatorRun(evt);
			}
		});


		stepButton = new JButton();
		stepButton.setBounds(108,485,90,35);
		stepButton.setBackground(new Color(214,217,223));
		stepButton.setForeground(new Color(0,0,0));
		stepButton.setEnabled(true);
		stepButton.setFont(new Font("sansserif",0,12));
		stepButton.setText("Step");
		stepButton.setVisible(true);
		//Set methods for mouse events
		//Call defined methods
		stepButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				simulatorStep(evt);
			}
		});


		//adding components to contentPane panel
		contentPane.add(Console);
		contentPane.add(ConsoleClear);
		contentPane.add(consoleLabel);
		contentPane.add(instructionList);
		contentPane.add(instructionsLabel);
		contentPane.add(runButton);
		contentPane.add(stepButton);

		//adding panel to JFrame and seting of window position and close operation
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	//Method mouseClicked for runButton
	private void simulatorRun (MouseEvent evt) {
		simulator.run();
	}

	//Method mouseClicked for stepButton
	private void simulatorStep (MouseEvent evt) {
	    simulator.singleStep();
	}

	//method for generate menu
	public void generateMenu(){
		menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu tools = new JMenu("Tools");
		JMenu help = new JMenu("Help");

		JMenuItem open = new JMenuItem("Open   ");
		JMenuItem save = new JMenuItem("Save   ");
		JMenuItem exit = new JMenuItem("Exit   ");
		JMenuItem preferences = new JMenuItem("Preferences   ");
		JMenuItem about = new JMenuItem("About   ");
		
		open.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		    JFileChooser fc = new JFileChooser();

		    int returnVal = fc.showOpenDialog(SimulatorGUI.this);

		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = fc.getSelectedFile();

		        try {
		            Parser parser = new Parser(instructionList);

		            //pass 1 = labelsB
		            FileReader symRdr = new FileReader(file);
		            SymbolTable symTab = parser.parseLabels(symRdr);
		            symRdr.close();

		            Instruction.useSymbolTable(symTab);

		            //pass 2 = instructions
		            FileReader instRdr = new FileReader(file);
		            Program program = parser.parseInstructions(instRdr);
		            instRdr.close();
		            
		            FileOutputStream fileOutStrm = new FileOutputStream(file);
		            program.writeObjectFile(fileOutStrm);
		            fileOutStrm.close();
		            
		            FileInputStream fileInStrm = new FileInputStream(file);
		            simulator.loadProgram(fileInStrm);
		            simulator.loadData(file, symTab);
		            fileInStrm.close();
		        } 
		        catch (IOException e) {
		            System.out.println("Error parsing selected file.");
		        }
		    }
	      }
	    });


		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		tools.add(preferences);
		help.add(about);

		menuBar.add(file);
		menuBar.add(tools);
		menuBar.add(help);
	}



	 public static void main(String[] args){
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SimulatorGUI();
			}
		});
	}

}