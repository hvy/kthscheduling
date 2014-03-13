public class GA {
  // performs the GA

  private final int POPULATION_SIZE = 20; // TODO: test different sizes
  private final int DESIRED_FITNESS = 0;
  

  public TimeTable generateTimeTable() {
    // setup everything first
    Population population = createPopulation();

    // run until the fitness is high enough
    // high enough should at least mean that
    // all hard constraints are solved
    // adjust for the number of soft constraints to be solved too
    // use another stop criteria too, in order to not run forever?

    while (population.getTopIndividual().getFitness() > DESIRED_FITNESS) {
      
      cullPopulation(population);
      breed(population);
      
      // output information?
    }

    return population.getTopIndividual();
  }
  

  // =================
  // Setup functions

  private Population createPopulation() {

    return null;
  }

  private void loadConstraints() {
    // TODO change return val and parameters
  }

  // TODO: do we need to check that the input data
  // is valid? maybe later?

  // ================
  // Genetic algorithm functions

  private void cullPopulation(Population population) {
    // remove the baddies 
  }

  private void breed(Population population) {
    // implement different crossover algorithms here
    // mutate according to mutation rate
  }
  
  private void repairTimeTable(TimeTable tt) {
    // TODO
  }

  private void mutate(TimeTable tt) {
    // TODO
  }
  
  // TODO: other name?
  // low fitness score is good here
  // alt: gör så att score 0 är accepterbart
  // en hardconstraint ger så mkt minus poäng att 
  // även om alla soft uppfylls så är det fortfarande negativt
  private void fitness(TimeTable tt) {
    // TODO 
    // set the fitness to this time table
  }
  

}
