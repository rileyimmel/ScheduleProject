package edu.virginia.cs.immelScheduleProject;

public class Course {

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private int course_id;
    private String name;
    private int number;
    private double credits;
    private String instructor;
    private String time;
    private String days;
    private String location;

    public Course(int course_id, String name, int number, double Credits, String instructor, String time, String days, String location){
        this.course_id = course_id;
        this.name = name;
        this.number = number;
        this.credits = Credits;
        this.instructor = instructor;
        this.time = time;
        this.days = days;
        this.location = location;
    }
    @Override
    public String toString(){
        return "Course ID: " + course_id + " Name: " + name + " Number: " + number + " Credits: " + credits + " Instructor: " + instructor + " Time: " + time + " Days: " + days + " Location: " + location;
    }


}
