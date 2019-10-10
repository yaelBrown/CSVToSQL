/*
* Purpose of this application is to convert CSV into sql file.
*
* References: https://stackabuse.com/reading-and-writing-csvs-in-java/
* */

// Output format:
// INSERT INTO albums (artist, name, release_date, sales, genre) VALUES (x,x,x,x,x);

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    // Paths
    // Path to file in CSV (Third parameter is the file to convert.
    private static Path p = Paths.get("src", "CSV", "thirtyMil.csv");

    // Path to file to save in SQL folder
    private static Path op = Paths.get("src", "SQL", "thirtyMill.sql");

    // Database
    // This controls the name of the database;
    private static String dbName = "codeup_test_db";


    // Names of Columns
    private static String artist;
    private static String name;
    private static String release_date;
    private static float sales;
    private static String genre;

    private static String row;


    private static BufferedReader csvReader;

    static {
        try {
            csvReader = new BufferedReader(new FileReader(String.valueOf(p)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        String dbToUse = "USE " + dbName + ";";

        // Use this database add's to beginning of SQL file.
        try {
            Files.write(op, (dbToUse + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        } catch(IOException e) {
            e.printStackTrace();
        }


        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");


            int iend = row.indexOf(",");


            // Get artist
            if (iend != -1) {
                artist = row.substring(0,iend);
                row = row.substring(iend + 1);
            };

            // Get name
            iend = row.indexOf(",");

            if (iend != -1) {
                name = row.substring(0,iend);
                row = row.substring(iend + 1);
            };

            // Get release date
            iend = row.indexOf(",");

            if (iend != -1) {
                release_date = row.substring(0,iend);
                row = row.substring(iend + 1);
            };

            // Get genre
            iend = row.indexOf(",");

            if (iend != -1) {
                genre = row.substring(0,iend);
                row = row.substring(iend);
            };


            // Get sales
            iend = row.indexOf(",");

            if (iend != -1) {
                sales =  Float.parseFloat(row.substring(1));
            };

            // INSERT INTO albums (artist, name, release_date, sales, genre) VALUES (x,x,x,x,x);
            // Generate output string based on the above
            String outputStr = "INSERT INTO albums (artist, name, release_date, sales, genre) " +
                    "VALUES ('" + artist + "','" + name + "','" + release_date + "','" + sales + "','" + genre + "');";


            // Write to SQL file in SQL directory the generated output string
            try {
                Files.write(op, (outputStr + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            } catch(IOException e) {
                e.printStackTrace();
            }

        }



    }


}
