import java.util.*;

public class TimeTable implements Comparable<TimeTable> {
  // TODO: change to double?
  private int fitness;

  // The timetables for each room
  private RoomTimeTable[] roomTimeTables;

  public TimeTable(int numRooms) {
    roomTimeTables = new RoomTimeTable[numRooms];
  }

  public int getFitness() {
    return fitness;
  }

  public void setFitness(int fitness) {
    this.fitness = fitness;
  }

  public RoomTimeTable[] getRoomTimeTables() {
    return roomTimeTables;
  }

  public void putRoomTimeTable(int i, RoomTimeTable rtt) {
    roomTimeTables[i] = rtt;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (RoomTimeTable rtt : roomTimeTables) {
      sb.append(rtt.toString());
      sb.append("==============\n");
    }

    return sb.toString();
  }

  @Override
  public int compareTo(TimeTable other) {
    int otherFitness = other.getFitness();

    if (fitness > otherFitness)
      return -1;
    else if (fitness == otherFitness)
      return 0;
    else
      return 1;
  }
}
