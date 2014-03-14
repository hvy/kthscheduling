import java.util.*;

/*
* Represents all the persistent information from the input
*/
public class KTH {
  // TODO: decide which of these are needed
  private static int nextRoomId = 0;
  private static int nextCourseId = 0;
  private static int nextStudentGroupId = 0;
  private static int nextLecturerId = 0;
  private static int nextEventId = 0;

  private Map<Integer, Room> rooms;
  private Map<Integer, Course> courses;
  private Map<Integer, StudentGroup> studentGroups;
  private Map<Integer, Lecturer> lecturers;
  private Map<Integer, Event> events;

  // TODO: add some maps over the IDs?

  public KTH() {
    rooms = new HashMap<Integer, Room>();
    courses = new HashMap<Integer, Course>();
    studentGroups = new HashMap<Integer, StudentGroup>();
    lecturers = new HashMap<Integer, Lecturer>();
    events = new HashMap<Integer, Event>();
  }

  public void createEvents(StudentGroup studentGroup, Course course) {
    int numLectures = course.getNumLectures();
    int numLessons = course.getNumLessons();
    int numLabs = course.getNumLabs();
    for(int i = 0; i < numLectures; i++) {
      Event lecture = new Event(Event.Type.LECTURE, 2, course);
      addEvent(lecture);
    }
    for(int i = 0; i < numLessons; i++) {
      Event lesson = new Event(Event.Type.LESSON, 2, course);
      addEvent(lesson);
    }
    for(int i = 0; i < numLabs; i++) {
      Event lab = new Event(Event.Type.LAB, 2, course);
      addEvent(lab);
    }
  }

  public int addRoom(Room room) {;
    rooms.put(nextRoomId, room);
    return nextRoomId++;
  }

  public Map<Integer, Room> getRooms() {
    return rooms;
  }

  public int addCourse(Course course) {
    courses.put(nextCourseId, course);
    return nextCourseId++;
  }

  public Map<Integer, Course> getCourses() {
    return courses;
  }

  public int addStudentGroup(StudentGroup studentGroup) {
    studentGroups.put(nextStudentGroupId, studentGroup);
    return nextStudentGroupId++;
  }

  public Map<Integer, StudentGroup> getStudentGroups() {
    return studentGroups;
  }

  public int addLecturer(Lecturer lecturer) {
    lecturers.put(nextLecturerId, lecturer);
    return nextLecturerId++;
  }

  public Map<Integer, Lecturer> getLecturers() {
    return lecturers;
  }

  public int addEvent(Event event) {
    events.put(nextEventId, event);
    return nextEventId++;
  }

  public Map<Integer, Event> getEvents() {
    return events;
  }
}
