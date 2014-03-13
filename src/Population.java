import java.util.*;

public class Population {
  // keeps all the TimeTables for a generation

  // should be ordered when selecting the best individuals
  private List<TimeTable> individuals;

  public Population() {
    individuals = new ArrayList<TimeTable>();
  }

  public TimeTable getTopIndividual() {
    return individuals.get(0);
  }

}
