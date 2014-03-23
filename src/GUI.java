import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

  // GUI properties
  private final String APPLICATION_TITLE = "KTH Scheduler";
  private final int WIDTH = 300;
  private final int HEIGHT = 300;

  // GUI components
  JPanel mainPanel;
  JButton runButton;
  JTextField inputDataUrlTextField;
  JTextField constraintsDataUrlTextField;
  JTextField mutationProbabilityTextField;
  JTextField crossoverProbabilityTextField;
  JTextField populationSizeTextField;

  public GUI() {
    init();
  }
  
  private void init() {
    // create GUI components
    mainPanel = new JPanel();
    runButton = new JButton("Run");    
    
    inputDataUrlTextField = new JTextField("../input/ficUni", 20);
    constraintsDataUrlTextField = new JTextField("../input/constraints", 20);
    mutationProbabilityTextField = new JTextField("Mutation probability", 10);
    crossoverProbabilityTextField = new JTextField("Crossover probability", 10);
    populationSizeTextField = new JTextField("Population size", 10);
    
    runButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
         run();
      }
    });
    
    mainPanel.add(inputDataUrlTextField);
    mainPanel.add(constraintsDataUrlTextField);
    mainPanel.add(mutationProbabilityTextField);
    mainPanel.add(crossoverProbabilityTextField);
    mainpanel.add(populationSizeTextField);
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
    
    // run the genetil algorithm
    TimeTable bestTimeTable = ga.generateTimeTable();
    ga.printTimeTable(bestTimeTable);
  }
  
  @Override
  public void actionPerformed(ActionEvent event) {
  
  }  
}
