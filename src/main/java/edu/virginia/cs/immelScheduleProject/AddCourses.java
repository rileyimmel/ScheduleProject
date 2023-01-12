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
import java.util.List;

public class AddCourses {

    public List<Course> getCourses(){
       try{
           URL apiURL = new URL(ConfigSingleton.getInstance().getCourses_url());
           String json = IOUtils.toString(apiURL, StandardCharsets.UTF_8);
           JSONObject allCourses = new JSONObject(json);
           JSONObject test = allCourses.getJSONObject("class_schedules");
           JSONArray courses = test.getJSONArray("records");
           for(int i = 0; i < 2 /*courses.length()*/; i++) {
               try {
                   JSONArray course = courses.getJSONArray(i);
                   System.out.println(course);
               } catch (JSONException e){
                   continue;
               }
           }

//           System.out.println(courses);
       } catch (IOException e) {
           e.printStackTrace();
       }

        //TODO: get courses from API
        return null;
    }
}
