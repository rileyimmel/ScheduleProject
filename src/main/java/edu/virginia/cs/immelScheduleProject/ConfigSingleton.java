package edu.virginia.cs.immelScheduleProject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class ConfigSingleton {

    private static final String configurationFileName = "config.json";

    private static ConfigSingleton instance;

    private String databaseName;

    private String courses_url;

    private ConfigSingleton() {
        setFieldsFromJSON();
    }

    public static ConfigSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigSingleton();
        }
        return instance;
    }

    private void setFieldsFromJSON() {
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            String packageName = "edu.virginia.cs.immelScheduleProject";
            String fileName = classLoader.getResource(packageName + "/" + configurationFileName).getFile();
            InputStream is = new FileInputStream(fileName);
            String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonTxt);

            databaseName = json.getString("database");
            courses_url = json.getString("courses_url");
        } catch (IOException e){
            System.out.println("Error reading config.json");
            e.printStackTrace();
        }
    }


    public String getDatabaseFilename(){
        return databaseName;
    }
    public String getCourses_url() {
        return courses_url;
    }


}
