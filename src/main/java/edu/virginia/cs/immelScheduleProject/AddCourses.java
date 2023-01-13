package edu.virginia.cs.immelScheduleProject;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddCourses {


    ArrayList<Integer> dupeIDS = new ArrayList<Integer>();

    public List<Course> getCourses(){
       try{
           URL apiURL = new URL(ConfigSingleton.getInstance().getCourses_url());
           String json = IOUtils.toString(apiURL, StandardCharsets.UTF_8);
           JSONObject allCourses = new JSONObject(json);
           JSONObject test = allCourses.getJSONObject("class_schedules");
           JSONArray courses = test.getJSONArray("records");
           for(int i = 0; i < courses.length(); i++) {
               try {
                   JSONArray course = courses.getJSONArray(i);
                   Course newCourse = getCourseFromJSON(course);
//                   System.out.println(course);
                   addCourseToDB(List.of(newCourse));
               } catch (JSONException e){
                   continue;
               }
           }
           System.out.println(dupeIDS);
           System.out.println(courses.length());

//           System.out.println(courses);
       } catch (IOException e) {
           e.printStackTrace();
       }

        //TODO: get courses from API
        return null;
    }


    private Course getCourseFromJSON(JSONArray course){
        int courseID = course.getInt(3);
        String name = course.getString(4);
        String subject = course.getString(0);
        int number = course.getInt(1);
        String instructor = course.getString(5);
        int section = course.getInt(2);
        String startTime = course.getString(9);
        String endTime = course.getString(10);
        String days = course.getString(8);

        Course newCourse = new Course(courseID, name, subject, number, section, instructor, startTime, endTime, days);
//        System.out.println(newCourse);
        return newCourse;
    }

    private void addCourseToDB(List<Course> courses){
        for(Course course : courses){
            int course_id = course.getCourse_id();
            String name = course.getName();
            String subject = course.getSubject();
            int number = course.getNumber();
            String instructor = course.getInstructor();
            int section = course.getSection();
            String startTime = course.getStartTime();
            String endTime = course.getEndTime();
            String days = course.getDays();

            String courseTableQuery = """
                    INSERT INTO course ("course_id", "name", "subject", "number")
                    VALUES (?, ?, ?, ?);
                    """;
            String sectionTableQuery = """
                    INSERT INTO section ("course_id", "instructor", "section", "start_time", "end_time", "days")
                    VALUES (?, ?, ?, ?, ?, ?);
                    """;
            try {
                ScheduleDatabaseManager db = new ScheduleDatabaseManager();
                db.connect();

                PreparedStatement stmt = db.connection.prepareStatement(courseTableQuery);
                stmt.setInt(1, course_id);
                stmt.setString(2, name);
                stmt.setString(3, subject);
                stmt.setInt(4, number);
                stmt.execute();

                stmt = db.connection.prepareStatement(sectionTableQuery);
                stmt.setInt(1, course_id);
                stmt.setString(2, instructor);
                stmt.setInt(3, section);
                stmt.setString(4, startTime);
                stmt.setString(5, endTime);
                stmt.setString(6, days);
                stmt.execute();
            } catch (SQLException e){
                dupeIDS.add(course_id);
                e.printStackTrace();
            }
        }
    }
    public void test(){
        String query = """
            Delete from course where course_id = 10303
            """;
        try {
            ScheduleDatabaseManager db = new ScheduleDatabaseManager();
            db.connect();
            Statement stmt = db.connection.createStatement();
            stmt.execute(query);
            String query2 = """
                Select * from section
                """;
            stmt = db.connection.createStatement();
            stmt.execute(query2);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
