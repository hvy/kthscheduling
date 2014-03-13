import java.util.*;

/*
* Represents all the persistent information from the input
*/
public class KTH {
  // TODO: decide which of these are needed
  private List<Room> rooms;
  private List<Course> courses;
  private List<StudentGroup> studentGroups;
  private List<Lecturer> lecturers;
  private List<Event> events;

  // TODO: add some maps over the IDs?

  public KTH() {
    rooms = new ArrayList<Room>();
    courses = new ArrayList<Course>();
    studentGroups = new ArrayList<StudentGroup>();
    lecturers = new ArrayList<Lecturer>();
    events = new ArrayList<Event>();
  }

  public void addRoom(Room room) {
    rooms.add(room);
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void addCourse(Course course) {
    courses.add(course);
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void addStudentGroup(StudentGroup studentGroup) {
    studentGroups.add(studentGroup);
  }

  public List<StudentGroup> getStudentGroups() {
    return studentGroups;
  }

  public void addLecturer(Lecturer lecturer) {
    lecturers.add(lecturer);
  }

  public List<Lecturer> getLecturers() {
    return lecturers;
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public List<Event> getEvents() {
    return events;
  }
}
