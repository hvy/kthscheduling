import java.util.*;

public class Population {
  // keeps all the TimeTables for a generation

  // should be ordered when selecting the best individuals
  private List<TimeTable> individuals;

  public Population() {
    individuals = new ArrayList<TimeTable>();
  }

  public void createRandomIndividuals(int numIndividuals, KTH kth) {
    int numRooms = kth.getRooms().size();
    Map<Integer, Room> rooms = kth.getRooms();
    for(int i = 0; i < numIndividuals; i++) {
      // create a time table
      TimeTable tt = new TimeTable(numRooms);
      // create a room timetable for each available room
      for(int roomId : rooms.keySet()) {
        Room r = rooms.get(roomId);
        RoomTimeTable rtt = new RoomTimeTable(r);
        tt.putRoomTimeTable(roomId, rtt);
      }
      // assign all event to any timeslot
      int rttId = 0;
      int day = 0;
      int timeSlot = 0;
      RoomTimeTable rtt = tt.getRoomTimeTables()[rttId];
      for(Event e : kth.getEvents().values()) {
        if(rttId >= numRooms) {
          System.out.println("ERROR: Too few rooms or too many events in the data set");
          System.exit(1);
        } else if(timeSlot == RoomTimeTable.NUM_TIMESLOTS) {
          day++;
          timeSlot = 0;
        } else if(day == RoomTimeTable.NUM_DAYS) {
          // room is now fully booked so we start to fill the next room with events
          rtt = tt.getRoomTimeTables()[++rttId];
        }
        int eventId = e.getId();
        System.out.println("==============");
        System.out.println("ROOM TIME TABLE ID: " + rttId);
        System.out.println("Day: " + day + " Timeslot: " + timeSlot + " Event ID: " + eventId);
        rtt.setEvent(day, timeSlot, eventId);  
        timeSlot++; 
      }
      individuals.add(tt);
    }
  }

  public TimeTable getTopIndividual() {
    return individuals.get(0);
  }

  public Iterator<TimeTable> iterator() {
    return individuals.iterator();
  }

  public void sortIndividuals() {
    Collections.sort(individuals);
  }

  public int size() {
    return individuals.size();
  }
}
