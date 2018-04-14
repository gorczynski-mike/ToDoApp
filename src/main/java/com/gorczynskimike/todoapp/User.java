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

@SuppressWarnings("WeakerAccess")
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

    @SuppressWarnings("WeakerAccess")
    public class TasksManager {

        private final Path userTasksFile = Paths.get("Users", name + ".txt");
        private String tableHeader = String.format("| %s| %5s| %30s | %10s | %30s | %30s|",
                "status","number","name","date","place","comments");
        @SuppressWarnings("ReplaceAllDot")
        private String tableBorder = tableHeader.replaceAll(".","=");

        /**
         * Add task with given attributes to user's tasks list
         * @param taskName new task name
         * @param taskDate new task date
         * @param taskPlace new task place
         * @param taskComment new task comment
         */
        public void addTask(String taskName, LocalDate taskDate, String taskPlace, String taskComment) {
            int taskNumber = findMaxTaskNumber() + 1;
            Task task = new Task(taskNumber, taskName, taskDate, taskPlace, taskComment);
            addTask(task);
        }

        /**
         * Adds given task to user's tasks list
         * @param task task to be added
         */
        public void addTask(Task task) {
            String taskName = task.getTaskName();
            if (findTask(taskName) != null) {
                System.out.println("Task with name " + taskName + " already exists. Cannot add task.");
            } else {
                User.this.tasksList.add(task);
                System.out.println("Task added successfully. Task details:");
                System.out.println(task);
                saveUserTasks();
            }
        }

        /**
         * Mark task with given number as completed.
         * @param taskNumber number of task to be marked as completed
         */
        public void completeTask(int taskNumber) {
            Task task = findTask(taskNumber);
            if(task != null){
                System.out.printf("I have found task with number %d. Marking task as complete.%n", taskNumber);
                task.completeTask();
                saveUserTasks();
            } else {
                System.out.printf("I haven't found task with number %d. No changes made.%n", taskNumber);
            }
        }

        /**
         * Mark task with given name as completed
         * @param taskName name of the task to be marked as completed
         */
        public void completeTask(String taskName) {
            Task task = findTask(taskName);
            if(task != null) {
                System.out.printf("I have found task with name %s. Marking task as complete.%n", taskName);
                task.completeTask();
                saveUserTasks();
            } else {
                System.out.printf("I haven't found task with number %s. No changes made.%n", taskName);
            }
        }

        /**
         * Deletes the task with given number
         * @param taskNumber number of the task to be deleted
         */
        public void deleteTask(int taskNumber) {
            Iterator<Task> taskIterator = User.this.tasksList.iterator();
            while(taskIterator.hasNext()) {
                Task current = taskIterator.next();
                if(current.getTaskNumber() == taskNumber) {
                    System.out.printf("I have found task with number %d. Deleting task.%n", taskNumber);
                    taskIterator.remove();
                    saveUserTasks();
                    return;
                }
            }
            System.out.printf("I haven't found task with number %d. No tasks deleted.%n", taskNumber);
        }

        /**
         * Deletes task with given name
         * @param taskName name of the task to be deleted
         */
        public void deleteTask(String taskName) {
            Iterator<Task> taskIterator = User.this.tasksList.iterator();
            while(taskIterator.hasNext()) {
                Task current = taskIterator.next();
                if(current.getTaskName().equals(taskName)) {
                    System.out.printf("I have found task with name %s. Deleting task.%n", taskName);
                    taskIterator.remove();
                    saveUserTasks();
                    return;
                }
            }
            System.out.printf("I haven't found task with name %s. No tasks deleted.%n", taskName);
        }

        /**
         * Finds max task number
         * @return the maximum value of task number in user's task list
         */
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

        /**
         * Find task with given number in user's tasks list
         * @param taskNumber number of the task we want to find
         * @return task if task exists in database, null if task is not found
         */
        private Task findTask(int taskNumber) {
            for(Task task : User.this.tasksList) {
                if(task.getTaskNumber() == taskNumber) {
                    return task;
                }
            }
            return null;
        }

        /**
         * Find task with given name in user's tasks list
         * @param taskName name of the task we want to find
         * @return task if task exists in database, null if task is not found
         */
        private Task findTask(String taskName) {
            for(Task task : User.this.tasksList) {
                if(task.getTaskName().equals(taskName)) {
                    return task;
                }
            }
            return null;
        }

        /**
         *  Loads user tasks from user tasks file to memory.
         */
        public void loadUserTasks() {
            try (
                    BufferedReader br = Files.newBufferedReader(userTasksFile)
            ) {
                String line;
                while((line = br.readLine()) != null) {
                    User.this.tasksList.add(Task.decodeTask(line));
                }
            } catch (IOException e) {e.printStackTrace();}
        }

        /**
         * Modifies task with given name. Parameters to be modified: date, place, comment.
         * @param taskName name of the task we want to modify
         * @param taskDate new task date
         * @param taskPlace new task place
         * @param taskComment new task comment
         * @return true if task was successfully modified, false otherwise
         */
        public boolean modifyTask(String taskName, LocalDate taskDate, String taskPlace, String taskComment){
            Task taskToBeModified = findTask(taskName);
            if (taskToBeModified == null) {
                System.out.println("Task not found, couldn't modify the task.");
                return false;
            }
            return modifyTask(taskToBeModified, taskDate, taskPlace, taskComment);
        }

        /**
         * Modifies task with given number. Parameters to be modified: date, place, comment.
         * @param taskNumber number of the task we want to modify
         * @param taskDate new task date
         * @param taskPlace new task place
         * @param taskComment new task comment
         * @return true if task was successfully modified, false otherwise
         */
        public boolean modifyTask(int taskNumber, LocalDate taskDate, String taskPlace, String taskComment){
            Task taskToBeModified = findTask(taskNumber);
            if (taskToBeModified == null) {
                System.out.println("Task not found, couldn't modify the task.");
                return false;
            }
            return modifyTask(taskToBeModified, taskDate, taskPlace, taskComment);

        }

        public boolean modifyTask(Task taskToBeModified, LocalDate taskDate, String taskPlace, String taskComment) {
            taskToBeModified.setTaskDate(taskDate);
            taskToBeModified.setTaskPlace(taskPlace);
            taskToBeModified.setTaskComments(taskComment);
            System.out.println("Task has been successfully modified: ");
            System.out.println(taskToBeModified);
            saveUserTasks();
            return true;
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

        /**
         *  Print the header of the tasks table to console
         */
        private void printTableHead(){
            System.out.println(tableBorder);
            System.out.println(tableHeader);
            System.out.println(tableBorder);
        }

        /**
         *  Print the footer of the tasks table to console
         */
        private void printTableFoot() {
            System.out.println(tableBorder);
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
         * Method used to print all tasks numbers and names, especially useful when user wants to modify or delete one
         * of them.
         */
        public void printAllTasksNumbersAndNames() {
            System.out.print("All tasks [number:name]: ");
            User.this.tasksList.stream()
                    .forEach(task -> System.out.printf("[%s:%s], ",task.getTaskNumber(), task.getTaskName()));
            System.out.println();
        }

        /**
         * Method used to print all 'to do' tasks numbers and names, especially useful when user wants to mark
         * one of them as finished
         */
        public void printAllTodoTasksNumbersAndNames() {
            System.out.print("All pending todo tasks [number:name]: ");
            User.this.tasksList.stream()
                    .filter(task -> task.getTaskStatus().equals(TaskStatus.TODO))
                    .forEach(task -> System.out.printf("[%s:%s], ",task.getTaskNumber(), task.getTaskName()));
            System.out.println();
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

        /**
         * Prints task with given number.
         * @param taskNumber number of the task to be printed
         */
        public void printTask(int taskNumber) {
            Task task = findTask(taskNumber);
            if(task != null) {
                System.out.println(task);
            } else {
                System.out.println("Task #" + taskNumber + " not found in database.");
            }
        }

        /**
         * Prints task with given name.
         * @param taskName name of the task to be printed
         */
        public void printTask(String taskName) {
            Task task = findTask(taskName);
            if(task != null) {
                System.out.println(task);
            } else {
                System.out.println("Task with name " + taskName + " not found in database");
            }
        }

        /**
         *  Saves user's tasks list to user task file.
         */
        public void saveUserTasks() {
            try (
                    BufferedWriter bw = Files.newBufferedWriter(userTasksFile);
                    PrintWriter pw = new PrintWriter(bw)
            ) {
                for(Task task : User.this.tasksList) {
                    pw.println(task);
                }
            } catch (IOException e) {e.printStackTrace();}
        }

        /**
         * Check if task with given number exists.
         * @param taskNumber search for the task using this task number.
         * @return true if the task already exists, false otherwise.
         */
        public boolean taskExists(int taskNumber) {
            return findTask(taskNumber) != null;
        }

        /**
         * Check if task with given name exists.
         * @param taskName search for the task using this task name.
         * @return true if the task already exists, false otherwise.
         */
        public boolean taskExists(String taskName) {
            return findTask(taskName) != null;
        }

    }

}
