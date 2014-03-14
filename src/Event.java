public class Event {

	public static enum Type { LECTURE, LESSON, LAB };
	private static int nextID = 1;

	private Type type;
	private int id;
	private int length;
	private Course course;

	public Event(Type type, int length, Course course) {
		this.id = nextID++;
		this.length = length;
		this.course = course;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public int getLength() {
		return length;
	}

	public Course getCourse() {
		return course;
	}

	public Type getType() {
		return type;
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
