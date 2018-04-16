package com.gorczynskimike.todoapp;

import java.util.List;

public interface DatabaseInterface {

    public List<Task> loadUserTasks(User user);

    public boolean saveUserTasks(User user, List<Task> userTasks);

}
