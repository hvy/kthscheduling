public class GA {
  // performs the GA

  private final int POPULATION_SIZE = 20; // TODO: test different sizes
  private final int DESIRED_FITNESS = 0;
  
  /*
  * Returns a schedule based on the given constraints
  * TODO: take the input file as parameter?
  */
  public TimeTable generateTimeTable() {
    // setup everything first
    Population population = createPopulation();

    // run until the fitness is high enough
    // high enough should at least mean that
    // all hard constraints are solved
    // adjust for the number of soft constraints to be solved too
    // use another stop criteria too, in order to not run forever?

    while (population.getTopIndividual().getFitness() < DESIRED_FITNESS) {
      
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
  
  // Idea for fitness:
  // Each of the softconstraints met should give a positive value
  
  // Each of the hard constraints that are not met should give a negative value
  
  // Each hard constraints negative contribution should be higher than the 
  // highest possible sum of the positive contributions of the soft constraints

  // A working schedule is then a schedule with positive fitness
  // A higher fitness is more desirable
  private void fitness(TimeTable tt) {
    // TODO 
    // set the fitness to this time table

    // call each of the constraints functions and (weight)
    // the scores together
  }

  //////////////////////////
  // CONSTRAINTS
  //////////////////////////

  ///////////////////
  // Hard constraints, each function returns the number of constraint breaches
  ///////////////////
  
  // NOTE: Two of the hard constraints are solved by the chosen datastructure
  // Invalid timeslots may not be used
  // A room can not be double booked at a certain timeslot

  // TODO: better name please
  private int coursesRequiredEvents(TimeTable tt) {
    return 0;
  }

  // num times a studentgroup is double booked
  private int studentGroupDoubleBooked(TimeTable tt) {
    return 0;
  }

  // num times a lecturer is double booked
  private int lecturerDoubleBooked(TimeTable tt) {
    return 0;
  }

  // num times a room is too small for the event booked
  private int roomCapacityConstraint(TimeTable tt) {
    return 0;
  }

  // num times an event is booked to the wrong room type
  private int eventTypeRoomMismatch(TimeTable tt) {
    return 0;
  }

  ///////////////////
  // Hard constraints, each function returns the number of constraint breaches
  ///////////////////
  
  // TODO: how to represent the soft constraints?
  // This is harder to represent

  // timetables with rooms closer together should be given 
  // higher scores
  private double distanceBetweenRoomsStudentGroup(TimeTable tt) {
    return 0.0;
  }

  // timetables with rooms closer together should be given 
  // higher scores
  private double distanceBetweenRoomsLecturer(TimeTable tt) {
    return 0.0;
  }

  // should schedules be "tightly" packed?
  private double unusedTimeSlots() {
    return 0.0;
  }
}
