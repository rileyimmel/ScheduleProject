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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    private String subject;
    private int number;
    private double credits;
    private String instructor;
    private String startTime;

    private String endTime;
    private String days;
    private String location;

    private int section;

    public Course(int course_id, String name, String subject, int number, int section, /*double Credits,*/ String instructor, String startTime, String endTime, String days/*, String location*/){
        this.course_id = course_id;
        this.name = name;
        this.subject = subject;
        this.number = number;
        this.section = section;
//        this.credits = Credits;
        this.instructor = instructor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
//        this.location = location;
    }
    @Override
    public String toString(){
        return "Course ID: " + course_id + " Name: " + name + " Subject: " + subject + " Number: " + number + " Section: " + section +/*" Credits: " + credits +*/ " Instructor: " + instructor + " Start Time: " + startTime + " End Time: " + endTime + " Days: " + days /*+ " Location: " + location*/;
    }


}
