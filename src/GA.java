import java.util.*;
import java.io.*;

/**
 * Performs the Genetic Algorithm(GA) on the KTH data set.
 */
public class GA {
  private final int MAX_POPULATION_SIZE = 20; // TODO: test different sizes
  private final int DESIRED_FITNESS = 0;

  private Population population;
  private KTH kth;

  public GA() {
    kth = new KTH();
    population = new Population();
  }

  /*
  * Returns a schedule based on the given constraints
  */
  public TimeTable generateTimeTable() {
    // create the initial randomized population
    kth.createEvents();
    createPopulation();

    // run until the fitness is high enough
    // high enough should at least mean that
    // all hard constraints are solved
    // adjust for the number of soft constraints to be solved too
    // use another stop criteria too, in order to not run forever?

    // initial sorting by fitness
    ListIterator<TimeTable> it = population.listIterator();
    while(it.hasNext()) {
      TimeTable tt = it.next();
      fitness(tt);
    }
    population.sortIndividuals();

    while (population.getTopIndividual().getFitness() < DESIRED_FITNESS) {
      System.out.println("Best fitness: " + population.getTopIndividual().getFitness());

      // have small chance of keeping a bad one
      // different chances for different intervals of fitness
      population = cullPopulation(population);
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
      TimeTable bestTimeTable = population.getTopIndividual();
      for(RoomTimeTable rtt : bestTimeTable.getRoomTimeTables()) {
        System.out.println("=============================================");
        System.out.println(rtt);
      }
    }
    return population.getTopIndividual();
  }


  //////////////////////////
  // SETUP
  //////////////////////////

  public void loadData(String dataFileUrl) {
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
          System.out.println("ID: " + room.getId());
          System.out.println("Name: " + room.getName());
          System.out.println("Capacity: " + room.getCapacity());
          System.out.println("Type: " + room.getType());
          //
          kth.addRoom(room);

        } else if(readingSection.equals("COURSES")) {
          courseName = data[0];
          int numLectures = Integer.parseInt(data[1]);
          int numLessons = Integer.parseInt(data[2]);
          int numLabs = Integer.parseInt(data[3]);
          Course course = new Course(courseName, numLectures, numLessons, numLabs);
          // DEBUG
          System.out.println("=== COURSE ===");
          System.out.println("ID: " + course.getId());
          System.out.println("#Lectures: " + course.getNumLectures());
          System.out.println("#Lessons: " + course.getNumLessons());
          System.out.println("#Labs: " + course.getNumLabs());
          // END_DEBUG
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

          // DEBUG
          System.out.println("=== LECTURER ===");
          System.out.println("ID: " + lecturer.getId());
          System.out.println("Name: " + lecturer.getName());
          System.out.print("Courses: ");
          List<Course> courses = lecturer.getCourses();
          for(Course c : courses) {
            System.out.print(c.getId() + " ");
          }
          System.out.println();
          // END_DEBUG

          kth.addLecturer(lecturer);

        } else if(readingSection.equals("STUDENTGROUPS")) {
          studentGroupName = data[0];
          int size = Integer.parseInt(data[1]);
          StudentGroup studentGroup = new StudentGroup(studentGroupName, size);
          for(int i = 2; i < data.length; i++) {
            courseName = data[i];
            courseId = courseNameToId.get(courseName);
            studentGroup.addCourse(kth.getCourses().get(courseId));
          }
          // DEBUG
          System.out.println("=== STUDENT GROUP ===");
          System.out.println("ID: " + studentGroup.getId());
          System.out.println("Name: " + studentGroup.getName());
          System.out.println("Number of students: " + studentGroup.getSize());
          //
          kth.addStudentGroup(studentGroup);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadConstraints(String constraintsFileUrl) {
    // read the input file and
    // add all data to the kth object
  }

  // TODO: do we need to check that the input data
  // is valid? maybe later?

  //////////////////////////
  // GENETIC ALGORITHMS
  //////////////////////////

  private void createPopulation() {
    population.createRandomIndividuals(MAX_POPULATION_SIZE, kth);

  }

  private Population cullPopulation(Population population) {
    Population culledPopulation = new Population();

    ListIterator<TimeTable> it = population.listIterator();

    // take the top half of population
    // assumes individuals are sorted
    for (int i = 0; i < MAX_POPULATION_SIZE / 2; i++) {
      culledPopulation.addIndividual(it.next());
    }

    // replace the population with the culled population
    return culledPopulation;
  }

  // implement different selection/crossover algorithms here
  // mutate according to mutation rate
  private void breed(Population population) {
    Random rand = new Random(System.currentTimeMillis());

    List<Integer> parentIndices = new ArrayList<Integer>();
    for (int i = 0; i < MAX_POPULATION_SIZE / 2; i++) {
      parentIndices.add(i);
    }

    // breed until the population is restored to its normal size
    while (population.size() < MAX_POPULATION_SIZE) {
      // pick two parents randomly among the population
      Collections.shuffle(parentIndices);
      int p1 = parentIndices.get(0);
      int p2 = parentIndices.get(1);
      TimeTable t1 = population.getIndividual(p1);
      TimeTable t2 = population.getIndividual(p2);

      TimeTable child = crossover(t1, t2);
      population.addIndividual(child);
    }

    population.sortIndividuals();
  }

  // For each gene (booking in a timeslot), take with equal
  // probability from either parent
  private TimeTable crossover(TimeTable t1, TimeTable t2) {
    TimeTable child = new TimeTable(kth.getNumRooms());

    RoomTimeTable[] rtts1 = t1.getRoomTimeTables();
    RoomTimeTable[] rtts2 = t2.getRoomTimeTables();

    Random rand = new Random(System.currentTimeMillis());

    // for every roomtimetable
    for (int i = 0; i < kth.getNumRooms(); i++) {
      RoomTimeTable rtt = new RoomTimeTable(rtts1[i].getRoom());

      // for each available time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS; timeslot++) {
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int allele;
          if (rand.nextBoolean()) {
            // take from parent 1
            allele = rtts1[i].getEvent(day, timeslot);

          } else {
            // take from parent 2
            allele = rtts2[i].getEvent(day, timeslot);
          }

          rtt.setEvent(day, timeslot, allele);
        }
      }

      child.putRoomTimeTable(i, rtt);
    }

    repairTimeTable(child);

    // calculate the fitness
    fitness(child);

    return child;
  }

  private TimeTable crossoverWithPoints(TimeTable t1, TimeTable t2,
                                                      int numPoints) {

    return null;
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
    long startTime = System.nanoTime();
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

    long endTime = System.nanoTime();

    tt.setFitness(fitness);

    // temp
    System.out.println("Fitness calculated in " + (endTime - startTime) + " ns");
    System.out.println(fitness);
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

    for (StudentGroup sg : kth.getStudentGroups().values()) {

      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS; timeslot++) {
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int numDoubleBookings = 0;

          boolean evTypeSet = false;
          Event.Type eventType = null;
          for (RoomTimeTable rtt : rtts) {
            int eventID = rtt.getEvent(day, timeslot);

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

    for (Lecturer lecturer : kth.getLecturers().values()) {

      // for each time
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS;
                                                           timeslot++) {

        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int numBookings = 0;

          for (RoomTimeTable rtt : rtts) {
            int eventID = rtt.getEvent(day, timeslot);

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
          int eventID = rtt.getEvent(day, timeslot);

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
          int eventID = rtt.getEvent(day, timeslot);

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
