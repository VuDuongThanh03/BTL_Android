package com.example.btl_android.them_hoc_phan;

/** @noinspection ALL*/
public class HocPhan {
    private int id;
    private String name;
    private String code;
    private int credits;
    private String semester;

    public HocPhan() {
    }

    public HocPhan(final String name, final String code, final int credits, final String semester) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.semester = semester;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(final int credits) {
        this.credits = credits;
    }

    public String getSemester() {
        return this.semester;
    }

    public void setSemester(final String semester) {
        this.semester = semester;
    }
}