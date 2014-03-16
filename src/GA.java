import java.util.*;
import java.io.*;

/**
 * Performs the Genetic Algorithm(GA) on the KTH data set.
 */
public class GA {
  private final int POPULATION_SIZE = 20; // TODO: test different sizes
  private final int MAX_POPULATION_SIZE = 20; // TODO: test different sizes
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

      // TODO: a lab or class for a course should result in several events
      // when checking if a studentgroup is double booked
      // it should be allowed to have a studentgroup id double booked
      // if the double bookings are class or lab
    }

    return population.getTopIndividual();
  }


  //////////////////////////
  // SETUP
  //////////////////////////

  public KTH loadData(String dataFileUrl) {
    try {
      File file = new File(dataFileUrl);
      BufferedReader in = new BufferedReader(new FileReader(file));
      String line = null;
      // input data sections are read in the following order separated by #
      // #rooms <name> <capacity> <type>
      // #courses <id> <name> <numLectures> <numClasses> <numLabs>
      // #lecturers <name> <course>+
      // #studentgroups <name> <numStudents> <course>+
      String readingSection = null;
      String roomName = null;
      String courseName = null;
      String lecturerName = null;
      String studentGroupName = null;
      int courseId = 0;
      HashMap<String, Integer> courseNameToId = new HashMap<String, Integer>();
      while((line = in.readLine()) != null) {
        String[] data = line.split(" ");
        if(data[0].charAt(0) == '#') {
          readingSection = data[1];
          data = in.readLine().split(" ");
        }
        if(readingSection.equals("ROOMS")) {
          roomName = data[0];
          int cap = Integer.parseInt(data[1]);
          Event.Type type = Event.generateType(Integer.parseInt(data[2]));
          Room room = new Room(roomName, cap, type);
          // DEBUG
          System.out.println("=== ROOM ===");
          System.out.println("ID: " + room.getID());
          System.out.println("Name:" + room.name());
          System.out.println("");
          //
          kth.addRoom(room);
        } else if(readingSection.equals("COURSES")) {
          courseName = data[0];
          int numLectures = Integer.parseInt(data[1]);
          int numLessons = Integer.parseInt(data[2]);
          int numLabs = Integer.parseInt(data[3]);
          Course course = new Course(courseName, numLectures, numLessons, numLabs);
          courseId = kth.addCourse(course);
          courseNameToId.put(courseName, courseId);
        } else if(readingSection.equals("LECTURERS")) {
          lecturerName = data[0];
          Lecturer lecturer = new Lecturer(lecturerName);
          for(int i = 1; i < data.length; i++) {
            // register all courses that this lecturer may teach
            courseName = data[i];
            courseId = courseNameToId.get(courseName);
            lecturer.addCourse(kth.getCourses().get(courseId));
          }
          kth.addLecturer(lecturer);
        } else if(readingSection.equals("STUDENTGROUPS")) {
          studentGroupName = data[0];
          int size = Integer.parseInt(data[1]);
          StudentGroup studentGroup = new StudentGroup(studentGroupName, size);
          kth.addStudentGroup(studentGroup);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return kth;
  }

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
    Iterator<TimeTable> it = population.iterator();

    while (it.hasNext()) {
      TimeTable tt = it.next();
      fitness(tt);
    }

    // TODO: remove this and simply keep it sorted at all time
    // for optimization
    population.sortIndividuals();
    
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

    int studentGroupDoubleBookings = studentGroupDoubleBookings(tt);
    int lecturerDoubleBookings = lecturerDoubleBookings(tt);
    int roomCapacityBreaches = roomCapacityBreaches(tt);
    int roomTypeBreaches = roomTypeBreaches(tt);

    int numBreaches = studentGroupDoubleBookings +
                      lecturerDoubleBookings +
                      roomCapacityBreaches + 
                      roomTypeBreaches;

    // temporary
    // simply one minus point for each breach
    int fitness = -1 * numBreaches;
    tt.setFitness(fitness);
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
  // probably not needed
  private int coursesRequiredEvents(TimeTable tt) {
    return 0;
  }

  // TODO: will this be needed?
  private int eventDoubleBooked(TimeTable tt) {
    return 0;
  }

  // num times a studentgroup is double booked
  // OPTIMIZE: just iterate over the rooms once instead?
  private int studentGroupDoubleBookings(TimeTable tt) {
    int numBreaches = 0;
  
    RoomTimeTable[] rtts = tt.getRoomTimeTables();
    
    while (StudentGroup sg : kth.getStudentGroups.values()) {

      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS; timeslot++) {
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int numDoubleBookings = 0;
          
          boolean evTypeSet = false;
          Event.Type eventType = null;
          for (RoomTimeTable rtt : rtts) {
            int eventID = rtt.getBookedEventID(timeslot, day);

            // 0 is unbooked
            if (eventID != 0) {
              Event event = kth.getEvent(eventID);
              int sgID = event.getStudentGroup().getId();
              
              if (sgID == sg.getId()) {
                
                if (!evTypeSet) {
                  // first one
                  eventType = event.getType(); 
                  evTypeSet = true;
                
                } else {
                  if (eventType == Event.Type.LECTURE) {
                    numDoubleBookings++;
                  
                  } else if (eventType != event.getType()) {
                    numDoubleBookings++;
                  
                  } else {
                    // labs and classes may be double booked
                    // to account for the extra events created for each studentgroup
                    // for each lesson/lab
                  }
                }
              }
            }
          }

          if (numDoubleBookings > 0) {
            numBreaches += numDoubleBookings;
          }
        }
      }
    }

    return numBreaches;
  }

  // num times a lecturer is double booked
  // NOTE: lecturers are only booked to lectures
  // for the labs and classes, TAs are used and they are assumed to always
  // be available
  private int lecturerDoubleBookings(TimeTable tt) {
    int numBreaches = 0;

    RoomTimeTable[] rtts = tt.getRoomTimeTables();

    while (Lecturer lecturer : kth.getLecturers().values()) {

      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS;
                                                           timeslot++) {

        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int numBookings = 0;

          for (RoomTimeTable rtt : rtts) {
            int eventID = rtt.getBookedEventID(timeslot, day);

            // 0 is unbooked
            if (eventID != 0) {
              Event event = kth.getEvent(eventID);
              // only check lectures since lecturers are only
              // attached to lecture events
              if (event.getType() == Event.Type.LECTURE) {
                numBookings++;
              }
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
  private int roomCapacityBreaches(TimeTable tt) {
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
            int eventSize = kth.getEvent(eventID).getSize();
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
  private int roomTypeBreaches(TimeTable tt) {
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
            Event.Type type = kth.getEvent(eventID).getType(); 
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
