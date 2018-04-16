package com.gorczynskimike.todoapp;

import java.util.List;

public interface DatabaseInterface {

    public List<Task> loadUserTasks(User user);

    public boolean saveUserTasks(User user, List<Task> userTasks);

    public boolean userExists(String userName);

    public User createUser(String userName);

    public User loadUser(String userName);

    public void initialize();

    public void cleanup();

}
