package com.gorczynskimike.todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

/**
 * Every user's task is stored as an instance of the Task class
 */
@SuppressWarnings("WeakerAccess")
public class Task{

    private static final Pattern taskItemPattern = Pattern.compile("\\[.*?\\]");

    private TaskStatus taskStatus = TaskStatus.TODO;
    private int taskNumber = 123;
    private String taskName = "name";
    private LocalDate taskDate;
    private String taskPlace = "place";
    private String taskComments = "comment";

    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskPlace() {
        return taskPlace;
    }

    public String getTaskComments() {
        return taskComments;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskPlace(String taskPlace) {
        this.taskPlace = taskPlace;
    }

    public void setTaskComments(String taskComments) {
        this.taskComments = taskComments;
    }

    public Task(int taskNumber, String taskName, LocalDate taskDate, String taskPlace, String
            taskComments) {
        this.taskNumber = taskNumber;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskPlace = taskPlace;
        this.taskComments = taskComments;
    }

    private Task (TaskStatus taskStatus, int taskNumber, String taskName, LocalDate taskDate, String taskPlace, String
            taskComments) {
        this(taskNumber, taskName, taskDate, taskPlace, taskComments);
        this.taskStatus = taskStatus;
    }

    /**
     * Mark this task as done
     */
    public void completeTask() {
        this.taskStatus = TaskStatus.DONE;
    }

    /**
     * Decodes task coded as a String
     * @param encodedTask the task coded as a String, standard form created by Task.toString() method
     * @return returns decoded task
     * @throws IllegalArgumentException
     */
    public static Task decodeTask(String encodedTask) throws IllegalArgumentException {
        Matcher matcher = taskItemPattern.matcher(encodedTask);
        List<String> taskItems = new ArrayList<>();
        while(matcher.find()) {
            String item = matcher.group();
            taskItems.add(item.substring(1, item.length()-1));
        }
        if(taskItems.size() != 6) {
            throw new IllegalArgumentException("Corrupted row in task database");
        }

        TaskStatus newTaskStatus = TaskStatus.valueOf(taskItems.get(0));
        int newTaskNumber = Integer.parseInt(taskItems.get(1));
        String newTaskName = taskItems.get(2);
        String dateString = taskItems.get(3);
        String[] dateItems = dateString.split("-");
        if(dateItems.length != 3) {
            throw new IllegalArgumentException("Corrupted row in task database");
        }
        LocalDate newTaskDate = LocalDate.of(Integer.parseInt(dateItems[0]), Integer.parseInt(dateItems[1]), Integer.parseInt(dateItems[2]));
        String newTaskPlace = taskItems.get(4);
        String newTaskComments = taskItems.get(5);

        return new Task(newTaskStatus, newTaskNumber, newTaskName, newTaskDate, newTaskPlace, newTaskComments);
    }

    /**
     * String that can be used to print task to console
     * @return formatted String representation of the task
     */
    public String getConsoleStringRepresentation() {
        return String.format("|%6s | %05d | %30s | %10s | %30s | %30s|",
                taskStatus,
                taskNumber,
                taskName,
                taskDate,
                taskPlace,
                taskComments);
    }

    @Override
    public String toString() {
        return String.format("[%s] - [%d] - [%s] - [%s] - [%s] - [%s]",
                taskStatus,
                taskNumber,
                taskName,
                taskDate,
                taskPlace,
                taskComments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskNumber == task.taskNumber &&
                taskStatus == task.taskStatus &&
                Objects.equals(taskName, task.taskName) &&
                Objects.equals(taskDate, task.taskDate) &&
                Objects.equals(taskPlace, task.taskPlace) &&
                Objects.equals(taskComments, task.taskComments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(taskStatus, taskNumber, taskName, taskDate, taskPlace, taskComments);
    }
}
