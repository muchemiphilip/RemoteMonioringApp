package com.okotheffie.remotemonioringapp.models;

public class Task {

    private String assignedTo;
    private String name;
    private String description;
    private String taskId;
    private String dueDate;
    private String assignedBy;
    private String dateAssigned;

//    private boolean accepted;
//    private boolean completed;
//    private boolean declined;
//    private String timeStamp;


    public Task() {
    }

    public Task(String assignedTo, String name, String description, String taskId, String dueDate, String assignedBy) {
        this.assignedTo = assignedTo;
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.dueDate = dueDate;
        this.assignedBy = assignedBy;
    }
    public Task(String name, String description,String taskId,String dateAssigned, String dueDate){
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.dateAssigned = dateAssigned;
        this.dueDate = dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

}
