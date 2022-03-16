package com.example.javatest.test;

public class TestStudy {

    private StudyStatus status;

    private int limit;

    private String name;

    public TestStudy(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLimit() {
        return limit;
    }

    public TestStudy(int limit) {
        if (limit == 0) {
            System.out.println("throw");
            throw new IllegalArgumentException("0");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }
}
