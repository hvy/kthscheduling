public class RoomTimeTable {
  
  public static final int NUM_DAYS = 5;
  public static final int NUM_TIMESLOTS = 4;


  //matrix of timeslots
  // with id of what class (or event?) is booked there
  // rows are timeslots, columns are days
  private int[][] timeSlots;

  // Holds the constraints for this room
  private Room room;

  public RoomTimeTable() {
    timeSlots = new int[NUM_TIMESLOTS][NUM_DAYS];
  }

  public int getBookedEventID(int timeslot, int day) {
    return timeSlots[timeslot][day];
  }

  public Room getRoom() {
    return room;
  }
}
