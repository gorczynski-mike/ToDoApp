package com.gorczynskimike.todoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileDatabaseInterface implements DatabaseInterface{

    @Override
    public List<Task> loadUserTasks(User user) {
        Path userTasksFile = getUserTasksFile(user);
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

    private Path getUserTasksFile(User user){
        return Paths.get("Users", user.getName() + ".txt");
    }
}
