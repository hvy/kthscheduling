import java.util.*;

public class TimeTable {
  // the fitness of this individual, calculated by GA
  // TODO: store it here? needed?
  private int fitness;
  
  // The timetables for each room
  private RoomTimeTable[] roomTimeTables;
  
  public TimeTable(int numRooms) {
    roomTimeTables = new RoomTimeTable[numRooms];
  }

  public int getFitness() {
    return fitness;
  }

  public RoomTimeTable[] getRoomTimeTables() {
    return roomTimeTables;
  }
}
