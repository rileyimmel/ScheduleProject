package edu.virginia.cs.immelScheduleProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ScheduleDatabaseManager dbManager = new ScheduleDatabaseManager();
        dbManager.connect();
        dbManager.dropAllTables();
        dbManager.createTables();


//        dbManager.dropCourseTable();
//        dbManager.createCourseTable();

//        try {
//            URL url = new URL("https://api.devhub.virginia.edu/v1/courses");
//            URLConnection con = url.openConnection();
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(
//                            con.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null)
//                System.out.println(inputLine);
//            in.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
        //Test amend

        AddCourses addCourses = new AddCourses();
        addCourses.getCourses();
//        addCourses.test();

    }
}