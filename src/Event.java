public class Event {

	public static enum Type { LECTURE, LESSON, LAB };
	private static int nextID = 1;

	private Type type;
	private int id;
	private int length;
  private int size;
	private Course course;
  private StudentGroup studentGroup;

	public Event(Type type, int length, int size, Course course, StudentGroup sg) {
		this.id = nextID++;
		this.length = length;
    this.size = size;
		this.course = course;
		this.type = type;
    this.studentGroup = sg;
	}

	public int getId() {
		return id;
	}

	public int getLength() {
		return length;
	}

  public int getSize() {
    return size();
  }

	public Course getCourse() {
		return course;
	}

	public Type getType() {
		return type;
	}

  public StudentGroup getStudentGroup() {
    return studentGroup;
  }

	public static Type generateType(int i) {
		switch (i) {
			case 0:
				return Type.LECTURE;
			case 1:
				return Type.LESSON;
			case 2:
				return Type.LAB;
			default:
				break;
		}
		return null;
	}
}
