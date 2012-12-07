package species;

import java.util.Comparator;

public class FirstNameComparator implements Comparator<DataSpeciesRecord> {
 public int compare(DataSpeciesRecord sr, DataSpeciesRecord anotherSR) {
    String speciesName1 = (sr).speciesName;
    String speciesName2 = (anotherSR).speciesName;
      return speciesName1.compareTo(speciesName2);
  }
}