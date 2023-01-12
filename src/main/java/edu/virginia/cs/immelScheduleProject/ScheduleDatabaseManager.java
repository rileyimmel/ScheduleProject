package edu.virginia.cs.immelScheduleProject;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleDatabaseManager {

    Connection connection;

    public void connect() {

        if(connection != null){
            throw new IllegalStateException("Already connected");
        }
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + ConfigSingleton.getInstance().getDatabaseFilename());
            System.out.println("Connected to database");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error with database connection");
            e.printStackTrace();
        }
    }


    public void createTables(){
        String checkForTables = getSQLForCheckForTables();

        createTableSQLQueries();
        ArrayList<String> createTableSQL = createTableSQLQueries();

        try{
            if(connection == null || connection.isClosed()){
                throw new IllegalStateException("Connection is " + (connection == null ? "null" : "closed"));
            } else{
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(checkForTables);
                if(rs.getInt(1) > 0){
                    throw new IllegalStateException("Tables already exist");
                } else{
                    for(String createTableQuery : createTableSQL) {
                        statement.execute(createTableQuery);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error with creating tables");
            e.printStackTrace();
        }
    }

    private ArrayList<String> createTableSQLQueries() {
        ArrayList<String> createTableSQL = new ArrayList<>();
        createTableSQL.add(getSQLForCreateStudentTable());
        createTableSQL.add(getSQLForCreateCourseTable());
        createTableSQL.add(getSQLForCreateSectionTable());
        createTableSQL.add(getSQLForCreateScheduleTable());
        return createTableSQL;
    }

    private static String getSQLForCheckForTables(){
        return """
                SELECT count(*) FROM sqlite_master WHERE type='table' AND name='student' OR name='course' OR name='schedule';
                """;
    }

    private static String getSQLForCreateStudentTable(){
        return """
                CREATE TABLE IF NOT EXISTS student(
                    id INTEGER PRIMARY KEY NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL
                );
                """;
    }

    private static String getSQLForCreateCourseTable(){
        return """
                CREATE TABLE IF NOT EXISTS course(
                    id INTEGER PRIMARY KEY NOT NULL,
                    course_id INTEGER NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    number VARCHAR(255) NOT NULL,
                    credits VARCHAR(255) NOT NULL
                );
                """;
    }

    private static String getSQLForCreateSectionTable(){
        return """
                CREATE TABLE IF NOT EXISTS section(
                    id INTEGER PRIMARY KEY NOT NULL,
                    course_id INTEGER NOT NULL,
                    number VARCHAR(255) NOT NULL,
                    instructor VARCHAR(255) NOT NULL,
                    time VARCHAR(255) NOT NULL,
                    location VARCHAR(255) NOT NULL,
                    FOREIGN KEY (course_id) REFERENCES course(id)
                );
                """;
    }

    private static String getSQLForCreateScheduleTable(){
        return """
                CREATE TABLE IF NOT EXISTS schedule(
                    id INTEGER PRIMARY KEY NOT NULL,
                    student_id INTEGER NOT NULL,
                    section_id INTEGER NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES student(id),
                    FOREIGN KEY (section_id) REFERENCES section(id)
                );
                """;
    }


}
