import java.io.*;

public class Main {

  // URL to the university data file
  private static final String DATA_URL = "../input/minikth";

  public static void main(String[] args) {
    GA ga = new GA();
    ga.loadData(DATA_URL);
    TimeTable timeTable = ga.generateTimeTable();
  }
}
