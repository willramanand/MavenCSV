package io.github.willramanand;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) {
    try {
      // Read file path and use as input for csvreader.
      Reader reader = Files
          .newBufferedReader(Paths.get(ClassLoader.getSystemResource("SEOExample.csv").toURI()));
      CSVReader csvReader = new CSVReader(reader);

      // Read initial categories at header
      String[] categories;
      categories = csvReader.readNext();

      // Begin reading through records
      String[] record;
      int count = 1;
      while ((record = csvReader.readNext()) != null) {
        System.out.println("============= Entry " + count + " ================");
        System.out.println(categories[0] + ": " + record[0]);
        System.out.println(categories[1] + ": " + record[1]);
        System.out.println(categories[2] + ": " + record[2]);
        System.out.println(categories[3] + ": " + record[3]);
        System.out.println(categories[4] + ": " + record[4]);
        System.out.println(categories[5] + ": " + record[5]);
        System.out.println(categories[6] + ": " + record[6]);
        System.out.println(categories[7] + ": " + record[7]);
        System.out.println(categories[8] + ": " + record[8]);
        System.out.println(categories[9] + ": " + record[9]);
        System.out.println("======================================");
        count++;
      }
    } catch (IOException | URISyntaxException e) {
      System.out.println("Error finding file!");
      e.printStackTrace();
    }

  }
}
