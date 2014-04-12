import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

  // genetic algorithm
  GA ga;

  // GUI properties
  private final String APPLICATION_TITLE = "KTH Scheduler";
  private final int WIDTH = 400;
  private final int HEIGHT = 400;

  // GUI components
  JPanel mainPanel;
  JButton runButton;
  JButton setupButton;
  JTextField inputDataUrlTextField;
  JTextField constraintsDataUrlTextField;
  JTextField mutationProbabilityTextField;
  JTextField crossoverProbabilityTextField;
  JTextField populationSizeTextField;
  JTextField selectionSizeTextField;
  JComboBox selectionTypeComboBox;
  JComboBox mutationTypeComboBox;

  public GUI() {
    ga = new GA();
    init();
  }
  
  private void init() {
    // create GUI components
    mainPanel = new JPanel();
    runButton = new JButton("Run");    
    setupButton = new JButton("Setup");    
    inputDataUrlTextField = new JTextField("../input/ficUni", 30);
    constraintsDataUrlTextField = new JTextField("../input/constraints", 30);
    mutationProbabilityTextField = new JTextField("50", 30);
    crossoverProbabilityTextField = new JTextField("50", 30);
    populationSizeTextField = new JTextField("100", 30);
    selectionSizeTextField = new JTextField("30", 30);
    selectionTypeComboBox = new JComboBox(GA.SELECTION_TYPE.getNames());
    mutationTypeComboBox = new JComboBox(GA.MUTATION_TYPE.getNames());
    
    // run button action listener
    runButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        run();
      }
    });
    
    // setup button action listener
    setupButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        setup();
        printConf();
      }
    });
    
    mainPanel.add(new JLabel("Input file URL"));
    mainPanel.add(inputDataUrlTextField);
    mainPanel.add(new JLabel("Constraints file URL"));
    mainPanel.add(constraintsDataUrlTextField);
    mainPanel.add(new JLabel("Mutation probability"));
    mainPanel.add(mutationProbabilityTextField);
    mainPanel.add(new JLabel("Crossover probability"));
    mainPanel.add(crossoverProbabilityTextField);
    mainPanel.add(new JLabel("Population size"));    
    mainPanel.add(populationSizeTextField);
    mainPanel.add(new JLabel("Culled population size"));    
    mainPanel.add(selectionSizeTextField);
    mainPanel.add(new JLabel("Selection type"));    
    mainPanel.add(selectionTypeComboBox);
    mainPanel.add(new JLabel("Mutation type"));
    mainPanel.add(mutationTypeComboBox);
    mainPanel.add(setupButton);    
    mainPanel.add(runButton);
    add(mainPanel);
    
    setTitle(APPLICATION_TITLE);
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  
  private void setup() {    
    // setup the genetic algorithm
    ga.loadData(inputDataUrlTextField.getText());
    ga.setMutationProbability(Integer.parseInt(mutationProbabilityTextField.getText()));
    ga.setCrossoverProbability(Integer.parseInt(crossoverProbabilityTextField.getText()));
    ga.setPopulationSize(Integer.parseInt(populationSizeTextField.getText()));
    ga.setSelectionSize(Integer.parseInt(selectionSizeTextField.getText()));
    ga.setSelectionType(selectionTypeComboBox.getSelectedIndex());
    ga.setMutationType(mutationTypeComboBox.getSelectedIndex());    
  }
  
  private void run() {
    TimeTable bestTimeTable = ga.generateTimeTable();
    ga.printTimeTable(bestTimeTable);
  }  
  
  private void printConf() {
    ga.printConf();
  }  

  @Override
  public void actionPerformed(ActionEvent event) {
  
  }  
}
