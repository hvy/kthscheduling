import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

  // GUI properties
  private final String APPLICATION_TITLE = "KTH Scheduler";
  private final int WIDTH = 400;
  private final int HEIGHT = 400;

  // GUI components
  JPanel mainPanel;
  JButton runButton;
  JTextField inputDataUrlTextField;
  JTextField constraintsDataUrlTextField;
  JTextField mutationProbabilityTextField;
  JTextField crossoverProbabilityTextField;
  JTextField populationSizeTextField;
  JTextField culledPopulationSizeTextField;

  public GUI() {
    init();
  }
  
  private void init() {
    // create GUI components
    mainPanel = new JPanel();
    runButton = new JButton("Run");    
    
    inputDataUrlTextField = new JTextField("../input/ficUni", 30);
    constraintsDataUrlTextField = new JTextField("../input/constraints", 30);
    mutationProbabilityTextField = new JTextField("50", 30);
    crossoverProbabilityTextField = new JTextField("50", 30);
    populationSizeTextField = new JTextField("100", 30);
    culledPopulationSizeTextField = new JTextField("30", 30);
    
    runButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
         run();
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
    mainPanel.add(culledPopulationSizeTextField);
    mainPanel.add(runButton);
    add(mainPanel);
    
    setTitle(APPLICATION_TITLE);
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  private void run() {
    GA ga = new GA();
    
    // setup the genetic algorithm
    ga.loadData(inputDataUrlTextField.getText());
    ga.loadConstraints(constraintsDataUrlTextField.getText()); // not yet implemented
    ga.setMutationProbability(Integer.parseInt(mutationProbabilityTextField.getText()));
    ga.setCrossoverProbability(Integer.parseInt(crossoverProbabilityTextField.getText()));
    ga.setPopulationSize(Integer.parseInt(populationSizeTextField.getText()));
    ga.setCulledPopulationSize(Integer.parseInt(culledPopulationSizeTextField.getText()));
    // run the genetil algorithm
    TimeTable bestTimeTable = ga.generateTimeTable();
    ga.printTimeTable(bestTimeTable);
  }
  
  @Override
  public void actionPerformed(ActionEvent event) {
  
  }  
}
