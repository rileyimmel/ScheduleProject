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
                statement.execute("PRAGMA foreign_keys=ON");
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

    protected void createCourseTable() {
        String getCourseTable = getSQLForCreateCourseTable();
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is " + (connection == null ? "null" : "closed"));
            } else {
                Statement statement = connection.createStatement();
                statement.execute(getCourseTable);
            }
        } catch (SQLException e) {
            System.out.println("SQL error with creating course table");
            e.printStackTrace();
        }
    }

    protected void dropCourseTable(){
        try{
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is " + (connection == null ? "null" : "closed"));
            } else {
                String query = "DROP TABLE IF EXISTS course";
                Statement statement = connection.createStatement();
                statement.execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void dropAllTables(){
        try{
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is " + (connection == null ? "null" : "closed"));
            } else {
                String query = "DROP TABLE IF EXISTS student";
                Statement statement = connection.createStatement();
                statement.execute(query);
                query = "DROP TABLE IF EXISTS course";
                statement.execute(query);
                query = "DROP TABLE IF EXISTS section";
                statement.execute(query);
                query = "DROP TABLE IF EXISTS schedule";
                statement.execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getSQLForCheckForTables(){
        return """
                SELECT count(*) FROM sqlite_master WHERE type='table' AND name='student' OR name='course' OR name='schedule';
                """;
    }

    private static String getSQLForCreateStudentTable(){
        return """
                CREATE TABLE IF NOT EXISTS student(
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL
                )
                """;
    }

    private static String getSQLForCreateCourseTable(){
        //id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,

        return """
                CREATE TABLE IF NOT EXISTS course(
                    course_id INTEGER PRIMARY KEY NOT NULL UNIQUE,
                    name VARCHAR(255) NOT NULL,
                    subject VARCHAR(255) NOT NULL,
                    number VARCHAR(255) NOT NULL
                )
                """;
    }

    private static String getSQLForCreateSectionTable(){
        return """
                CREATE TABLE IF NOT EXISTS section(
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    course_id INTEGER NOT NULL,
                    instructor VARCHAR(255) NOT NULL,
                    section INTEGER NOT NULL,
                    start_time VARCHAR(255) NOT NULL,
                    end_time VARCHAR(255) NOT NULL,
                    days VARCHAR(255) NOT NULL,
                    FOREIGN KEY (course_id) REFERENCES course (course_id) ON DELETE CASCADE
                )
                """;
    }

    private static String getSQLForCreateScheduleTable(){
        return """
                CREATE TABLE IF NOT EXISTS schedule(
                    id INTEGER PRIMARY KEY NOT NULL,
                    student_id INTEGER NOT NULL UNIQUE,
                    section_id INTEGER NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
                    FOREIGN KEY (section_id) REFERENCES section(id) ON DELETE CASCADE
                )
                """;
    }


}
