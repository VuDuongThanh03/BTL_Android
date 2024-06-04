package com.example.btl_android.add_subject;

public class Subject {
    private String name;
    private String code;
    private int credits;
    private String semester;

    public Subject(String name, String code, int credits, String semester) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getCredits() {
        return credits;
    }

    public String getSemester() {
        return semester;
    }
}

