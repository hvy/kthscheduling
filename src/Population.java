import java.util.*;

public class Population {
  // keeps all the TimeTables for a generation

  // should be ordered when selecting the best individuals
  private LinkedList<TimeTable> individuals;

  public Population() {
    individuals = new LinkedList<TimeTable>();
  }

  public void createRandomIndividuals(int numIndividuals, KTH kth) {
    int numRooms = kth.getRooms().size();
    Map<Integer, Room> rooms = kth.getRooms();    
    for(int i = 0; i < numIndividuals; i++) {
      int rttId = 0;
      int day = 0;
      int timeSlot = 0;
      TimeTable tt = new TimeTable(numRooms);
      // create a room timetable for each available room
      for(int roomId : rooms.keySet()) {
        Room r = rooms.get(roomId);
        RoomTimeTable rtt = new RoomTimeTable(r);
        tt.putRoomTimeTable(roomId, rtt);
      }
      // assign all event to any timeslot in any room
      RoomTimeTable rtt = tt.getRoomTimeTables()[rttId];
      for(Event e : kth.getEvents().values()) {
        if(timeSlot == RoomTimeTable.NUM_TIMESLOTS) {
            day++;
            timeSlot = 0;
            if(day == RoomTimeTable.NUM_DAYS) {
              // room is now fully booked so we start to fill the next room with events
              rtt = tt.getRoomTimeTables()[++rttId];
              day = 0;
              timeSlot = 0;
            }
        } 
        
        // make sure that the data set allows for a valid timetabling
        if(rttId >= numRooms) {
          System.out.println("ERROR: Too few rooms or too many events in the data set");
          System.exit(1);
        }
        
        int eventId = e.getId();
        rtt.setEvent(day, timeSlot, eventId);  
        timeSlot++; 
        
        // DEBUG
        System.out.println("==============");
        System.out.println("ROOM TIME TABLE ID: " + rttId);
        System.out.println("Day: " + day + " Timeslot: " + timeSlot + " Event ID: " + eventId);
        // END_DEBUG
      }
      individuals.add(tt);
    }
  }
  
  // assumes sorted
  public TimeTable getTopIndividual() {
    return individuals.get(0);
  }

  public void addIndividual(TimeTable tt) {
    individuals.add(tt);
  }

  public void addIndividualSorted(TimeTable tt) {
    ListIterator<TimeTable> it = individuals.listIterator();
    ListIterator<TimeTable> it2 = individuals.listIterator();

    while (it.hasNext()) {
      if (it.next().getFitness() < tt.getFitness()) {
        it2.add(tt);
        break;
      }

      it2.next();
    } 
  }

  public ListIterator<TimeTable> listIterator() {
    return individuals.listIterator();
  }

  public void sortIndividuals() {
    Collections.sort(individuals);
  }

  public int size() {
    return individuals.size();
  }
}
