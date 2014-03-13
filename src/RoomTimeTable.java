public class RoomTimeTable {
  
  private final int NUM_DAYS = 5;
  private final int NUM_TIME_SLOTS = 4;


  //matrix of timeslots
  // with id of what class (or event?) is booked there

  private int[][] timeSlots;

  // Holds the constraints for this room
  private Room room;

  public RoomTimeTable() {
    timeSlots = new int[NUM_TIME_SLOTS][NUM_DAYS];
  }
}
