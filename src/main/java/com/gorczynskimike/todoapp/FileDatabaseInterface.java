package com.gorczynskimike.todoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileDatabaseInterface implements DatabaseInterface{

    private static final Path usersFile = Paths.get("users.txt");
    private static final Path usersFolder = Paths.get("Users");
    private static List<User> usersList = new ArrayList<>();

    @Override
    public List<Task> loadUserTasks(User user) {
        return loadUserTasks(user.getName());
    }

    private List<Task> loadUserTasks(String userName) {
        Path userTasksFile = getUserTasksFile(userName);
        List<Task> userTasks = new ArrayList<>();
        try (
                BufferedReader br = Files.newBufferedReader(userTasksFile)
        ) {
            String line;
            while((line = br.readLine()) != null) {
                userTasks.add(Task.decodeTask(line));
            }
        } catch (IOException e) {e.printStackTrace();}
        return userTasks;
    }

    @Override
    public boolean saveUserTasks(User user, List<Task> userTasks) {
        Path userTasksFile = getUserTasksFile(user);
        try (
                BufferedWriter bw = Files.newBufferedWriter(userTasksFile);
                PrintWriter pw = new PrintWriter(bw)
        ) {
            for(Task task : userTasks) {
                pw.println(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void initialize() {
        validateUsersFile();
        loadUsers();
        validateUsersTasksFiles();
    }

    @Override
    public void cleanup() {

    }

    private Path getUserTasksFile(User user){
        return Paths.get("Users", user.getName() + ".txt");
    }

    private Path getUserTasksFile(String userName){
        return Paths.get("Users", userName + ".txt");
    }

    private static void loadUsers() {
        try {
            List<String> userNames = Files.readAllLines(usersFile);
            System.out.println("Users found in the database: " + userNames);
            for(String userName : userNames) {
                usersList.add(new User(userName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(User user) {
        return usersList.contains(user);
    }

    @Override
    public boolean userExists(String userName) {
        return userExists(new User(userName));
    }

    @Override
    public User loadUser(String userName) {
        validateUserTasksFile(userName);
        List<Task> tasksList = loadUserTasks(userName);
        User user = new User(userName,tasksList);
        return user;
    }

    public static void createUser(User user) {
        validateUsersFile();
        try (
                BufferedWriter bw = Files.newBufferedWriter(usersFile, StandardOpenOption.APPEND);
                PrintWriter pw = new PrintWriter(bw)
        ) {
            pw.println(user);
            Files.createFile(Paths.get(usersFolder.toString(), user.getName().toLowerCase() + ".txt"));
            usersList.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User createUser(String userName) {
        validateUsersFile();
        User user = new User(userName);
        try (
                BufferedWriter bw = Files.newBufferedWriter(usersFile, StandardOpenOption.APPEND);
                PrintWriter pw = new PrintWriter(bw)
        ) {
            pw.println(userName);
            Files.createFile(Paths.get(usersFolder.toString(), userName.toLowerCase() + ".txt"));
            usersList.add(new User(userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private static void validateUsersFile() {
        if(!Files.exists(usersFile)) {
            System.out.println("No users file found, trying to create a new file.");
            try {
                Files.createFile(usersFile);
                System.out.println("New users file created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void validateUsersTasksFiles() {
        if(!Files.exists(usersFolder)) {
            System.out.println("No users folder found. Trying to create a new folder");
            try {
                Files.createDirectory(usersFolder);
                System.out.println("New users folder created successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(User user : usersList) {
            Path userTasksFile = Paths.get(usersFolder.toString(), user.getName().toLowerCase() + ".txt");
            if(!Files.exists(userTasksFile)) {
                System.out.printf("No tasks file found for %s. Trying to create a new file.%n", user);
                try {
                    Files.createFile(userTasksFile);
                    System.out.printf("New task file for %s created successfully.%n", user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void validateUserTasksFile(User user) throws IllegalStateException {
        Path userTasksFile = Paths.get(usersFolder.toString(), user.getName() + ".txt");
        if(!Files.exists(userTasksFile)) {
            throw new IllegalStateException("Could not find user's tasks file.");
        }
        System.out.printf("%s, your tasks file is present.%n", user.getName());
    }

    private void validateUserTasksFile(String userName) throws IllegalStateException {
        Path userTasksFile = Paths.get(usersFolder.toString(), userName + ".txt");
        if(!Files.exists(userTasksFile)) {
            throw new IllegalStateException("Could not find user's tasks file.");
        }
        System.out.printf("%s, your tasks file is present.%n", userName);
    }
}
