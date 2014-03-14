import java.util.*;

public class GA {
  // performs the GA

  private final int POPULATION_SIZE = 20; // TODO: test different sizes
  private final int DESIRED_FITNESS = 0;

  private KTH kth;

  public GA() {
    kth = new KTH();
  }
  
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
      
      // have small chance of keeping a bad one
      // different chances for different intervals of fitness
      cullPopulation(population);
      breed(population);

      // check whether java random is good enough

      // save some of the good parent as well
      
      // output information?

      // TODO: test fitness function time early

      // TODO: make roomtimetable inner structure to a vector instead
      // should make it faster

      // TODO: optimize the check of double bookings by only iterating over 
      // the roomtimetables only once,
      // fast functions are important to GA, since they are slow
    }

    return population.getTopIndividual();
  }
  

  //////////////////////////
  // SETUP
  //////////////////////////

  private Population createPopulation() {

    return null;
  }

  private void loadConstraints() {
    // TODO change return val and parameters
    
    // read the input file and
    // add all data to the kth object
  }

  // TODO: do we need to check that the input data
  // is valid? maybe later?

  //////////////////////////
  // GENETIC ALGORITHMS
  //////////////////////////


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
  
  // TODO: will this be needed?
  private int eventDoubleBooked(TimeTable tt) {
    return 0;
  }

  // num times a studentgroup is double booked
  private int studentGroupDoubleBooked(TimeTable tt) {
    int numBreaches = 0;
  
    RoomTimeTable[] rtts = tt.getRoomTimeTables();
    List<StudentGroup> studentGroups = kth.getStudentGroups();
    
    for (StudentGroup sg : studentGroups) {

      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS; 
                                                          timeslot++) {
        
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int numBookings = 0;

          for (RoomTimeTable rtt : rtts) {
            int eventID = rtt.getBookedEventID(timeslot, day);
            
            // 0 is unbooked
            if (eventID != 0) {
              // if the event that this eventID belongs to
              // is an event that this studentgroup attends
              // increment numBookings
              // TODO
            }
          }
          
          // only one booking per time is allowed
          if (numBookings > 1) {
            
            // add all extra bookings to the number of constraint breaches
            numBreaches += numBookings - 1;
          }
        }
      }
    }

    return numBreaches;
  }

  // num times a lecturer is double booked
  private int lecturerDoubleBooked(TimeTable tt) {
    int numBreaches = 0;
  
    RoomTimeTable[] rtts = tt.getRoomTimeTables();
    List<Lecturer> lecturers = kth.getLecturers();
    
    for (Lecturer lecturer : lecturers) {

      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS; 
                                                           timeslot++) {
        
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int numBookings = 0;

          for (RoomTimeTable rtt : rtts) {
            int eventID = rtt.getBookedEventID(timeslot, day);
            
            // 0 is unbooked
            if (eventID != 0) {
              // if the event that this eventID belongs to
              // is an event that this lecturer attends
              // increment numBookings
              // TODO
            }
          }
          
          // only one booking per time is allowed
          if (numBookings > 1) {
            
            // add all extra bookings to the number of constraint breaches
            numBreaches += numBookings - 1;
          }
        }
      }
    }

    return numBreaches;
  }

  // num times a room is too small for the event booked
  private int roomCapacityConstraint(TimeTable tt) {
    int numBreaches = 0;
    
    RoomTimeTable[] rtts = tt.getRoomTimeTables();

    for (RoomTimeTable rtt : rtts) {
      int roomSize = rtt.getRoom().getCapacity();
      
      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS;
                                                          timeslot++) {
        
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int eventID = rtt.getBookedEventID(timeslot, day);
          
          // only look at booked timeslots
          if (eventID != 0) {
            // TODO: find the size of the studentgroup that this event
            // belongs to
            int eventSize = 0; // temp
            if (roomSize < eventSize) {
              numBreaches++;
            }
          }
        }
      }
    }

    return numBreaches;
  }

  // num times an event is booked to the wrong room type
  private int eventTypeRoomMismatch(TimeTable tt) {
    int numBreaches = 0;
    
    RoomTimeTable[] rtts = tt.getRoomTimeTables();

    for (RoomTimeTable rtt : rtts) {
      Event.Type roomType = rtt.getRoom().getType(); 
      
      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS;
                                                          timeslot++) {
        
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int eventID = rtt.getBookedEventID(timeslot, day);
          
          // only look at booked timeslots
          if (eventID != 0) {
            // TODO: find the type of the event booked here
            Event.Type type = Event.Type.LECTURE; // temp 
            if (roomType != type) {
              numBreaches++;
            }
          }
        }
      }
    }

    return numBreaches;
  }

  ///////////////////
  // Soft constraints, each function returns the number of constraint breaches
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

    // räkna antal håltimmar?
    return 0.0;
  }
}
