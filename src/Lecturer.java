import java.util.List;
import java.util.ArrayList;

public class Lecturer {
  public static int ID = 0;

  private String name;
  private int id;
	private List<Course> courses;

  public Lecturer(String name) {
		this.name = name;
    this.id = ID++;
		courses = new ArrayList<Course>();
  }

	public void addCourse(Course course) {
		courses.add(course);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public List<Course> getCourses() {
		return courses;
	}
}
