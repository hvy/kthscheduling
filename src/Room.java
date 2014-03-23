public class Room {
	private static int nextId = 0;
  // store information about a room
  // num seats, equipment etc
	private String name;
	private int id;
	private int capacity;
	private Event.Type type;

	public Room(String name, int capacity, Event.Type type) {
		this.name = name;
		this.capacity = capacity;
		this.type = type;
		id = nextId++;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getCapacity() {
		return capacity;
	}

	public Event.Type getType() {
		return type;
	}
	
	public static void resetId() {
	  nextId = 0;
	}
}
