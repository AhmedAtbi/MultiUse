package com.example.multipurposeapp.model;

public class Task {

    private int id,priority;
    private String task_name,description;

    public Task(int id,String task_name, String description,int priority) {
        this.id = id;
        this.task_name = task_name;
        this.description = description;
        this.priority = priority;
    }

    public Task(String task_name, String description,int priority) {
        this.priority = priority;
        this.task_name = task_name;
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task() {
    }
}
