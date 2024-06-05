package com.example.btl_android.add_subject;

public class Subject {
    private int id;
    private String name;
    private String code;
    private int credits;
    private String semester;

    public Subject() {
    }

    public Subject(String name, String code, int credits, String semester) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.semester = semester;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}