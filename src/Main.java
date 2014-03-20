import java.util.*;
import java.io.*;

/*
  TODO
  - New crossover with point
  - Do not eliminate all bad time tables but letting some join the crossoever
  - When selecting crossoever parents, use the random wheel
*/

public class Main {
  // URL to the university data file
  private static final String UNIVERSITY_DATA_URL = "../input/ficUni";
  private static final String UNIVERSITY_CONSTRAINTS_URL = "../input/constraints";
  private static final String OUTPUT_FILE_NAME_URL = "../timetable";

  public static void main(String[] args) {
    GA ga = new GA();
    ga.loadData(UNIVERSITY_DATA_URL);
    ga.loadConstraints(UNIVERSITY_CONSTRAINTS_URL); // not yet implemented
    TimeTable bestTimeTable = ga.generateTimeTable();
    ga.printTimeTable(bestTimeTable);
    /*
    for(RoomTimeTable rtt : bestTimeTable.getRoomTimeTables()) {
        System.out.println("=============================================");
        System.out.println(rtt);
    }
    */
    writeToFile(bestTimeTable, OUTPUT_FILE_NAME_URL);
  }

  /**
   * Write the best timetable to a file
   */
  public static void writeToFile(TimeTable tt, String fileName) {
    try {
      File file = new File(fileName);
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      // TODO write the timetable to file
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
