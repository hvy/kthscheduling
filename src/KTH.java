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

  public Event getEvent(int id) {
    return events.get(id);
  }

  public Map<Integer, Event> getEvents() {
    return events;
  }

  public void createEvents() {
    for (StudentGroup sg : studentGroups.values()) {
      for (Course course : sg.getCourses()) {
        // create lecture events
        for (int i = 0; i < course.getNumLectures(); i++) {
          //Event event = new Event(Event.Type.LECTURE,
                                  //sg.getSize(),

                   // TODO: how do we choose a lecturer, randomly?
                                  
        }
        
        // TODO: should maxsize of a subgroup be 40? to fit in the rooms
        int lessonSize = 40;
        
        // create lesson events
        for (int i = 0; i < course.getNumLessons(); i++) {
          int sgSize = sg.getSize();
         
          // create several events with a part of this studentgroup's
          // size until their combined size is the same as
          // the studentgroup's size
          while (sgSize > 0) {
            int evSize = sgSize > lessonSize ? lessonSize : sgSize;
            Event event = new Event(Event.Type.LESSON,
                                    evSize,
                                    null, // should this be null or some default TA value?
                                    course,
                                    sg);

            events.put(event.getID(), event);
            sgSize = sgSize - evSize;

          }
        }
        
        // TODO: is this size good?
        int labSize = 25;

        // create lab events
        for (int i = 0; i < course.getNumLabs(); i++) {
          int sgSize = sg.getSize();

          while (sgSize > 0) {
            int evSize = sgSize > labSize ? labSize : sgSize;
            Event event = new Event(Event.Type.LAB,
                                    evSize,
                                    null,
                                    course,
                                    sg);

            events.put(event.getID(), event);
            sgSize = sgSize - evSize;
          }
        }
      }
    }
  }
}
