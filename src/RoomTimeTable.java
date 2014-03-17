public class RoomTimeTable {
  
  public static final int NUM_DAYS = 5;
  public static final int NUM_TIMESLOTS = 4;

  // matrix of timeslots
  // with id of what event that is bookes in each slot
  // rows are timeslots, columns are days
  private int[][] timeSlots;

  // Holds the constraints for this room
  private Room room;

  public RoomTimeTable(Room room) {
    this.room = room;
    timeSlots = new int[NUM_TIMESLOTS][NUM_DAYS];
  }

  public boolean hasEvent(int day, int timeslot) {
    if(timeSlots[timeslot][day] == 0) {
      return false;
    } else {
      return true;
    }
  }

  public void setEvent(int day, int timeslot, int eventId) {
    timeSlots[timeslot][day] = eventId;
  }

  public int getBookedEventID(int timeslot, int day) {
    return timeSlots[timeslot][day];
  }

  public Room getRoom() {
    return room;
  }
}
