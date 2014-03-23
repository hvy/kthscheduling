import java.util.*;

/*
* Represents all the persistent information from the input
*/
public class KTH {
  private Map<Integer, Room> rooms;
  private Map<Integer, Course> courses;
  private Map<Integer, StudentGroup> studentGroups;
  private Map<Integer, Lecturer> lecturers;
  private Map<Integer, Event> events;
  private ArrayList<Integer> eventIds;

  // TODO: add some maps over the IDs?

  public KTH() {
    rooms = new HashMap<Integer, Room>();
    courses = new HashMap<Integer, Course>();
    studentGroups = new HashMap<Integer, StudentGroup>();
    lecturers = new HashMap<Integer, Lecturer>();
    events = new HashMap<Integer, Event>();
    eventIds = new ArrayList<Integer>();
  }

  public int addRoom(Room room) {
    rooms.put(room.getId(), room);
    return room.getId();
  }

  public Map<Integer, Room> getRooms() {
    return rooms;
  }

  public int getNumRooms() {
    return rooms.size();
  }

  public int addCourse(Course course) {
    courses.put(course.getId(), course);
    return course.getId();
  }

  public Map<Integer, Course> getCourses() {
    return courses;
  }

  public int addStudentGroup(StudentGroup studentGroup) {
    studentGroups.put(studentGroup.getId(), studentGroup);
    return studentGroup.getId();
  }

  public Map<Integer, StudentGroup> getStudentGroups() {
    return studentGroups;
  }

  public int addLecturer(Lecturer lecturer) {
    lecturers.put(lecturer.getId(), lecturer);
    return lecturer.getId();
  }

  public Map<Integer, Lecturer> getLecturers() {
    return lecturers;
  }
  
  public Event getEvent(int id) {
    return events.get(id);
  }

  public int getRandomEventId(Random rand) {
    return eventIds.get(rand.nextInt(eventIds.size()));
  }

  public Map<Integer, Event> getEvents() {
    return events;
  }

  public void createEvents() {
    for (StudentGroup sg : studentGroups.values()) {
      for (Course course : sg.getCourses()) {
        
        // create lecture events
        for (int i = 0; i < course.getNumLectures(); i++) {
          // find a lecturer for this course
          // TODO: put lecturers on the course object instead?
          List<Lecturer> possibleLecturers = new ArrayList<Lecturer>();
          for (Lecturer lecturer : lecturers.values()) {
            if (lecturer.canTeach(course)) {
              possibleLecturers.add(lecturer);
            }
          }

          // temp, just take the first possible teacher
          Event event = new Event(Event.Type.LECTURE,
                                  sg.getSize(),
                                  possibleLecturers.get(0),
                                  course,
                                  sg);

          events.put(event.getId(), event);
          eventIds.add(event.getId());
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

            events.put(event.getId(), event);
            eventIds.add(event.getId());
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

            events.put(event.getId(), event);
            eventIds.add(event.getId());
            sgSize = sgSize - evSize;
          }
        }
      }
    }
  }
  
  public void clear() {
    Room.resetId();
    Event.resetId();
    Lecturer.resetId();
    StudentGroup.resetId();
    rooms.clear();
    courses.clear();
    studentGroups.clear();
    lecturers.clear();
    events.clear();
    eventIds.clear();
  }
  
  public void printTimeTable(TimeTable tt) {
    StringBuilder sb = new StringBuilder();
    for(RoomTimeTable rtt : tt.getRoomTimeTables()) {
      sb.append("========================================");    
      sb.append("Room: " + rtt.getRoom().getName() + " Capacity: " + rtt.getRoom().getCapacity() + "\n");
      for (int timeslot = 0; timeslot < RoomTimeTable.NUM_TIMESLOTS; timeslot++) {
        for (int day = 0; day < RoomTimeTable.NUM_DAYS; day++) {
          int eventId = rtt.getEvent(day, timeslot);
          if(eventId != 0) {
            Event event = events.get(eventId);
            sb.append("[ " + event.getCourse().getId() + " " + event.getStudentGroup().getName() + " ");
            if(event.getType() == Event.Type.LECTURE) {
              sb.append(event.getLecturer().getName() + " ]");
            } else {
              sb.append("    ]");
            }
          } else {
            sb.append("[    -    ]");
          }
        }
        sb.append("\n");
      }    
    }
    System.out.println(sb.toString());
  }
  
}
