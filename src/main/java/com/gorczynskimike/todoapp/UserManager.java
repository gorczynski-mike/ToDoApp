package com.gorczynskimike.todoapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class UserManager {

    private static final Path usersFile = Paths.get("users.txt");
    private static final Path usersFolder = Paths.get("Users");

    private static List<User> usersList = new ArrayList<>();

    public static void initialize() {

        validateUsersFile();
        loadUsers();
        validateUsersTasksFiles();

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

    public static boolean userExists(String userName) {
        return userExists(new User(userName));
    }

    public static boolean userExists(User user) {
        return usersList.contains(user);
    }

    public static void createUser(String userName) {

        validateUsersFile();

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

    public static void validateUserTasksFile(String userName) throws IllegalStateException {
        Path userTasksFile = Paths.get(usersFolder.toString(), userName.toLowerCase() + ".txt");
        if(!Files.exists(userTasksFile)) {
            throw new IllegalStateException("Could not find user's tasks file.");
        }
        System.out.printf("%s, your tasks file is present.%n", userName);
    }

}
