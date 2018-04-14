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
import java.util.regex.Pattern;

public class User {

    private String name;
    private List<Task> tasksList;
    public final TasksManager tasksManager;

    public User(String name) {
        this.name = name;
        this.tasksManager = new TasksManager();
        tasksList = new ArrayList<>();
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

    public class TasksManager {

        private final Path userTasksFile = Paths.get("Users", name + ".txt");
        private String tableHeader = String.format("| %s| %5s| %30s | %10s | %30s | %30s|",
                "status","number","name","date","place","comments");
        private String tableBorder = tableHeader.replaceAll(".","=");

        public void addTask(String taskName, LocalDate taskDate, String taskPlace, String taskComment) {
            int taskNumber = findMaxTaskNumber() + 1;
            Task task = new Task(taskNumber, taskName, taskDate, taskPlace, taskComment);
            addTask(task);
        }

        public void addTask(Task task) {
            String taskName = task.getTaskName();
            if (findTask(taskName) != null) {
                System.out.println("Task with name " + taskName + " already exists. Cannot add task.");
                return;
            } else {
                User.this.tasksList.add(task);
                System.out.println("Task added successfully. Task details:");
                System.out.println(task.getConsoleStringRepresentation());
            }
        }

        private void printTableHead(){
            System.out.println(tableBorder);
            System.out.println(tableHeader);
            System.out.println(tableBorder);
        }

        private void printTableFoot() {
            System.out.println(tableBorder);
        }


        /**
         *  Prints all tasks sorting by task number.
         */
        public void printAllTasks() {
            printTableHead();
            User.this.tasksList.stream()
                    .sorted(Comparator.comparing(Task::getTaskNumber))
                    .forEach(task -> System.out.println(task.getConsoleStringRepresentation()));
            printTableFoot();
        }

        /**
         *  Prints all tasks for today, sorting first by task status, then by task number.
         */
        public void printAllTasksForToday() {
            LocalDate today = LocalDate.now();
            printTableHead();
            User.this.tasksList.stream()
                    .filter( task -> task.getTaskDate().equals(today))
                    .sorted(Comparator.comparing(Task::getTaskStatus).thenComparing(Task::getTaskNumber))
                    .forEach(task -> System.out.println(task.getConsoleStringRepresentation()));
            printTableFoot();
        }

        public void loadUserTasks() {
            try (
                    BufferedReader br = Files.newBufferedReader(userTasksFile);
            ) {
                String line = "";
                while((line = br.readLine()) != null) {
                    User.this.tasksList.add(Task.decodeTask(line));
                }
            } catch (IOException e) {e.printStackTrace();}
        }

        public void saveUserTasks() {
            try (
                    BufferedWriter bw = Files.newBufferedWriter(userTasksFile);
                    PrintWriter pw = new PrintWriter(bw);
            ) {
                for(Task task : User.this.tasksList) {
                    pw.println(task);
                }
            } catch (IOException e) {e.printStackTrace();}
        }

        /**
         *  Prints all tasks to do chronologically
         */
        public void printAllTodoTasks() {
            printTableHead();
            User.this.tasksList.stream()
                    .filter(task -> task.getTaskStatus().equals(TaskStatus.TODO))
                    .sorted(Comparator.comparing(Task::getTaskDate))
                    .forEach(task -> System.out.println(task.getConsoleStringRepresentation()));
            printTableFoot();
        }

        /**
         *  Prints all finished tasks chronologically
         */
        public void printAllDoneTasks() {
            printTableHead();
            User.this.tasksList.stream()
                    .filter(task -> task.getTaskStatus().equals(TaskStatus.DONE))
                    .sorted(Comparator.comparing(Task::getTaskDate))
                    .forEach(task -> System.out.println(task.getConsoleStringRepresentation()));
            printTableFoot();
        }

        public boolean taskExists(int taskNumber) {
            return findTask(taskNumber) != null;
        }

        public boolean taskExists(String taskName) {
            return findTask(taskName) != null;
        }

        public void printTask(int taskNumber) {
            Task task = findTask(taskNumber);
            if(task != null) {
                System.out.println(task);
            } else {
                System.out.println("Task #" + taskNumber + " not found in database.");
            }
        }

        public void printTask(String taskName) {
            Task task = findTask(taskName);
            if(task != null) {
                System.out.println(task);
            } else {
                System.out.println("Task with name " + taskName + " not found in database");
            }
        }

        private Task findTask(int taskNumber) {
            for(Task task : User.this.tasksList) {
                if(task.getTaskNumber() == taskNumber) {
                    return task;
                }
            }
            return null;
        }

        private Task findTask(String taskName) {
            for(Task task : User.this.tasksList) {
                if(task.getTaskName().equals(taskName)) {
                    return task;
                }
            }
            return null;
        }

        public void deleteTask(int taskNumber) {
            Iterator<Task> taskIterator = User.this.tasksList.iterator();
            while(taskIterator.hasNext()) {
                Task current = taskIterator.next();
                if(current.getTaskNumber() == taskNumber) {
                    System.out.printf("I have found task with number %d. Deleting task.%n", taskNumber);
                    taskIterator.remove();
                    return;
                }
            }
            System.out.printf("I haven't found task with number %d. No tasks deleted.%n", taskNumber);
            return;
        }

        public void completeTask(int taskNumber) {
            Task task = findTask(taskNumber);
            if(task != null){
                System.out.printf("I have found task with number %d. Marking task as complete.%n", taskNumber);
                task.completeTask();
                return;
            } else {
                System.out.printf("I haven't found task with number %d. No changes made.%n", taskNumber);
                return;
            }
        }

        public void completeTask(String taskName) {
            Task task = findTask(taskName);
            if(task != null) {
                System.out.printf("I have found task with name %s. Marking task as complete.%n", taskName);
                task.completeTask();
                return;
            } else {
                System.out.printf("I haven't found task with number %s. No changes made.%n", taskName);
                return;
            }
        }


        public void deleteTask(String taskName) {
            Iterator<Task> taskIterator = User.this.tasksList.iterator();
            while(taskIterator.hasNext()) {
                Task current = taskIterator.next();
                if(current.getTaskName().equals(taskName)) {
                    System.out.printf("I have found task with name %s. Deleting task.%n", taskName);
                    taskIterator.remove();
                    return;
                }
            }
            System.out.printf("I haven't found task with name %s. No tasks deleted.%n", taskName);
            return;
        }

        private int findMaxTaskNumber() {
            if(User.this.tasksList.size() == 0) return 0;

            int maxValue = -1;
            for(Task task : User.this.tasksList) {
                int current = task.getTaskNumber();
                if(current > maxValue) {
                    maxValue = current;
                }
            }
            return maxValue;
        }

        @SuppressWarnings("Duplicates")
        public boolean modifyTask(String taskName, LocalDate taskDate, String taskPlace, String taskComment){
            Task taskToBeModified = findTask(taskName);
            if (taskToBeModified == null) {
                System.out.println("Task not found, couldn't modify the task.");
                return false;
            }
            taskToBeModified.setTaskDate(taskDate);
            taskToBeModified.setTaskPlace(taskPlace);
            taskToBeModified.setTaskComments(taskComment);
            System.out.println("Task has been successfully modified: ");
            System.out.println(taskToBeModified);
            return true;
        }

        @SuppressWarnings("Duplicates")
        public boolean modifyTask(int taskNumber, LocalDate taskDate, String taskPlace, String taskComment){
            Task taskToBeModified = findTask(taskNumber);
            if (taskToBeModified == null) {
                System.out.println("Task not found, couldn't modify the task.");
                return false;
            }
            taskToBeModified.setTaskDate(taskDate);
            taskToBeModified.setTaskPlace(taskPlace);
            taskToBeModified.setTaskComments(taskComment);
            System.out.println("Task has been successfully modified: ");
            System.out.println(taskToBeModified);
            return true;
        }

    }

}
