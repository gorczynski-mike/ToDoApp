package com.gorczynskimike.todoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("WeakerAccess")
public class User {

    private String name;
    private List<Task> tasksList;
    private TasksManager tasksManager;

    public User(String name) {
        this.name = name;
        tasksList = new ArrayList<>();
        tasksManager = new TasksManager(this, tasksList);
    }

    public TasksManager getTasksManager() {
        return tasksManager;
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
