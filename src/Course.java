import java.util.List;
import java.util.ArrayList;

/*
 * Course containing ID and set of events
 */
public class Course {

	private String id;
	private int numLectures;
	private int numLessons;
	private int numLabs;

	/*
	 * Course class constructor
	 * @param id Course ID
	 */
	public Course(String id, int numLectures, int numLessons, int numLabs) {
		this.id = id;
		this.numLectures = numLectures;
		this.numLessons = numLessons;
		this.numLabs = numLabs;
	}

  public String getId() {
    return id;
  }

	public int getNumLectures() {
		return numLectures;
	}

	public int getNumLessons() {
		return numLessons;
	}

	public int getNumLabs() {
		return numLabs;
	}
}
