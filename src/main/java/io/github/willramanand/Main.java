package io.github.willramanand;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

  static Connection conn = null;

  public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
    System.out.println("---------Iteration 1---------");
    readExampleFile();

    System.out.println("\n---------Iteration 2----------");
    System.out.println("Reading from CSV and inserting into database.");
    CSVtoDB();

    System.out.println("Reading from JSON and inserting into database.");
    JSONtoDB();

  }

  public static void readExampleFile() {
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

  public static void CSVtoDB() throws SQLException {
    initializeDb();
    try {
      // Read file path and use as input for csvreader.
      Reader reader = Files
          .newBufferedReader(
              Paths.get(ClassLoader.getSystemResource("bookstore_report2.csv").toURI()));
      CSVReader csvReader = new CSVReader(reader);

      // Read initial categories at header
      String[] categories;
      categories = csvReader.readNext();

      // Begin reading through records
      String[] record;
      while ((record = csvReader.readNext()) != null) {
        // Create query
        String sql = "INSERT INTO book(isbn, book_title, author_name, publisher_name ) VALUES (?, ?, ?, ? )";

        // Set as prepared statement to put dynamic values
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        // Insert dynamic values
        preparedStatement.setString(1, record[0]);
        preparedStatement.setString(2, record[1]);
        preparedStatement.setString(3, record[2]);
        preparedStatement.setString(4, record[3]);

        // Execute query
        preparedStatement.executeUpdate();
        preparedStatement.close();
      }

    } catch (IOException | URISyntaxException | SQLException e) {
      e.printStackTrace();
    }
   closeDb();
  }

  public static void JSONtoDB() throws FileNotFoundException, SQLException {
    initializeDb();

    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src\\main\\resources\\authors.json"));
    AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

    for (var author : authors) {
      // Create query
      String sql = "INSERT INTO author(author_name, author_email, author_url ) VALUES (?, ?, ? )";

      // Set as prepared statement to put dynamic values
      PreparedStatement preparedStatement = conn.prepareStatement(sql);

      // Insert dynamic values
      preparedStatement.setString(1, author.getName());
      preparedStatement.setString(2, author.getEmail());
      preparedStatement.setString(3, author.getURL());
      // Execute query
      preparedStatement.executeUpdate();

      preparedStatement.close();
    }
    closeDb();
  }

  public static void initializeDb() throws SQLException {
    String url = "jdbc:sqlite:./src/main/resources/db/bookstore.db";
    System.out.println("Connection to Database has been established");
    conn = DriverManager.getConnection(url);
  }

  public static void closeDb() throws SQLException {
    conn.close();
    System.out.println("Connection to database has been successfully closed!");
  }
}

