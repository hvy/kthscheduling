import java.util.List;
import java.util.ArrayList;

/*
 * Course containing ID and set of events
 */
public class Course {
	
	private String id;
	private List<Event> events;

	/*
	 * Course class constructor
	 * @param id Course ID
	 */
	public Course(String id) {
		this.id = id;
		events = new ArrayList<Event>();
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public List<Event> getEvents() {
		return events;	
	}
}
