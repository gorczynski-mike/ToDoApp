package com.gorczynskimike.todoapp;

import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class User {

    private String name;
    private List<Task> tasksList;
    private TasksManager tasksManager;

    public User(String name) {
        this.name = name;
        this.tasksList = new ArrayList<>();
        this.tasksManager = new TasksManager(this, tasksList);
    }
    public User(String name, List<Task> tasksList) {
        this.name = name;
        this.tasksList = tasksList;
        this.tasksManager = new TasksManager(this, tasksList);
    }

    public void registerTasksManager(TasksManager tasksManager){
        this.tasksManager = tasksManager;
    }

    public TasksManager getTasksManager() {
        return this.tasksManager;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "User: " + this.name;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof User)) {
            return false;
        } else {
            return this.name.equalsIgnoreCase(((User) other).name);
        }
    }

    @Override
    public int hashCode() {
        return 34 * (17 + this.name.hashCode());
    }

}
