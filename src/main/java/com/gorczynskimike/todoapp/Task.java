package com.gorczynskimike.todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class Task extends AbstractTask{

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

    public void completeTask() {
        this.taskStatus = TaskStatus.DONE;
    }

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
//        System.out.printf("%s %s %s %d %d %d", dateItems[0], dateItems[1], dateItems[2], Integer.parseInt(dateItems[0]),
//                Integer.parseInt(dateItems[1]), Integer.parseInt(dateItems[2]));
        LocalDate newTaskDate = LocalDate.of(Integer.parseInt(dateItems[0]), Integer.parseInt(dateItems[1]), Integer.parseInt(dateItems[2]));
        String newTaskPlace = taskItems.get(4);
        String newTaskComments = taskItems.get(5);

        return new Task(newTaskStatus, newTaskNumber, newTaskName, newTaskDate, newTaskPlace, newTaskComments);
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

}
