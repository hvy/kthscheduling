import java.util.*;

public class Population {
  // keeps all the TimeTables for a generation

  // should be ordered when selecting the best individuals
  private List<TimeTable> individuals;

  public Population() {
    individuals = new ArrayList<TimeTable>();
  }

  public void createRandomIndividuals(int numIndividuals, KTH kth) {
    int numRooms = kth.getRooms().size();
    int numCourses = kth.getCourses().size();
    int numStudentGroups = kth.getStudentGroups().size();
    int numLecturers = kth.getLecturers().size();
    int numEvents = kth.getEvents().size();
    for(int i = 0; i < numIndividuals; i++) {
      TimeTable tt = new TimeTable(numRooms);
      for(Event e : kth.getEvents().values()) {
        // assign the event to a timeslot
      }
    }
  }

  public TimeTable getTopIndividual() {
    return individuals.get(0);
  }

  public Iterator<TimeTable> iterator() {
    return individuals.iterator();
  }

  public void sortIndividuals() {
    Collections.sort(individuals);
  }

  public int size() {
    return individuals.size();
  }
}
