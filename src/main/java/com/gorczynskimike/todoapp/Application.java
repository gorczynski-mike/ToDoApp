package com.gorczynskimike.todoapp;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class Application {

    private static Scanner scanner = new Scanner(System.in);
    private static User user;

    public static void main(String[] args) {

        UserManager.initialize();

        greetUser();
        mainLoop();
        exitProgram();

    }

    private static void greetUser() {
        System.out.println("Hello user, what is your name?");
        String name = scanner.nextLine();
        while(!DataValidator.validateName(name)) {
            System.out.println("Chosen name is invalid, only [a-zA-Z] letters are allowed. Try again.");
            name = scanner.nextLine();
        }
        System.out.println("Your name is: " + name);
        if(!UserManager.userExists(name)) {
            System.out.printf("I haven't found you in the database %s, creating new user.%n", name);
            UserManager.createUser(name.toLowerCase());
            System.out.println("New user created.");
        } else {
            System.out.printf("I have found you in the database %s. Hello again.%n", name);
        }
        UserManager.validateUserTasksFile(name);

        user = new User(name);
        user.tasksManager.loadUserTasks();

        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    private static void mainLoop() {
        int menuChoice = -1;
        mainLoop:
        while(menuChoice != 0) {
            printMenu();
            menuChoice = getMenuChoice();
            switch(menuChoice) {
                case 1:
                    System.out.printf("%nYou have chosen: 1. Show me my tasks for today.%n%n");
                    user.tasksManager.printAllTasksForToday();
                    break;
                case 2:
                    System.out.printf("%nYou have chosen: 2. Show me all my todo tasks.%n%n");
                    user.tasksManager.printAllTodoTasks();
                    break;
                case 3:
                    System.out.printf("%nYou have chosen: 3. Show me all my finished tasks.%n%n");
                    user.tasksManager.printAllDoneTasks();
                    break;
                case 32:
                    System.out.printf("%nYou have chosen: 32. Show me my tasks.%n%n");
                    user.tasksManager.printAllTasks();
                    break;
                case 4:
                    markAsCompleted();
                    break;
                case 5:
                    addTask();
                    break;
                case 6:
                    modifyTask();
                    break;
                case 7:
                    deleteTask();
                    break;
                case 0:
                    System.out.println("Exiting.");
                    break mainLoop;
                default:
                    System.err.println("Option not recognized. This should never happen!");
                    break;
            }
            System.out.println("Press enter to continue");
            scanner.nextLine();
        }
    }

    @SuppressWarnings("Duplicates")
    private static void modifyTask() {
        System.out.printf("%nYou have chosen: 6. Modify task.%n%n");
        int userChoice = getMiniUserChoice();
        boolean taskExists = false;
        switch(userChoice) {
            case 1:
                int taskNumber = getTaskNumber();
                taskExists = user.tasksManager.taskExists(taskNumber);
                if(taskExists) {
                    System.out.println("Task found in database, the following task will be modified: ");
                    user.tasksManager.printTask(taskNumber);
                    System.out.println("Please type new date of the task: ");
                    LocalDate newTaskDate = getTaskDate();
                    System.out.println("Please type new place of the task (optional, hit enter if you don't"
                            + "wish to enter this info): ");
                    String newTaskPlace = scanner.nextLine().trim();
                    System.out.println("Please type new comment of the task (optional, hit enter if you don't"
                            + "wish to enter this info): ");
                    String newTaskComment = scanner.nextLine().trim();
                    user.tasksManager.modifyTask(taskNumber,newTaskDate, newTaskPlace, newTaskComment);
                } else {
                    System.out.println("Task with number " + taskNumber + " not found in database, nothing modified.");
                }
                break;
            case 2:
                String taskName = getTaskName();
                taskExists = user.tasksManager.taskExists(taskName);
                if(taskExists) {
                    System.out.println("Task found in database, the following task will be modified: ");
                    user.tasksManager.printTask(taskName);
                    System.out.println("Please type new date of the task: ");
                    LocalDate newTaskDate = getTaskDate();
                    System.out.println("Please type new place of the task (optional, hit enter if you don't"
                            + "wish to enter this info): ");
                    String newTaskPlace = scanner.nextLine().trim();
                    System.out.println("Please type new comment of the task (optional, hit enter if you don't"
                            + "wish to enter this info): ");
                    String newTaskComment = scanner.nextLine().trim();
                    user.tasksManager.modifyTask(taskName,newTaskDate, newTaskPlace, newTaskComment);
                } else {
                    System.out.println("Task with name " + taskName + " not found in database, nothing modified.");
                }
                break;
            default:
                System.err.println("Unrecognized user mini choice. This should never happen!");
                break;
        }
    }

    private static String getTaskName() {
        String taskName = scanner.nextLine();
        while(!(DataValidator.validateTaskName(taskName))) {
            System.out.println("Task name connot be empty, try again: ");
            taskName = scanner.nextLine();
        }
        taskName = taskName.trim();
        return taskName;
    }

    private static LocalDate getTaskDate() {
        while (true) {
            try {
                String taskDate = scanner.nextLine();
                while (!(DataValidator.validateDate(taskDate))) {
                    System.out.println("Incorrect format (YYYY-MM-DD), try again: ");
                    taskDate = scanner.nextLine();
                }
                String[] dateItems = taskDate.split("-");
                LocalDate taskLocalDate = LocalDate.of(Integer.parseInt(dateItems[0]), Integer.parseInt(dateItems[1]), Integer.parseInt(dateItems[2]));
                return taskLocalDate;
            } catch (Exception e) {
                System.out.println("Typed date is incorrect, try again.");
            }
        }
    }

    private static void addTask() {
        System.out.printf("%nYou have chosen: 5. Add task.%n%n");
        System.out.println("Please type the name of the task: ");
        String taskName = scanner.nextLine();
        while(!(DataValidator.validateTaskName(taskName))) {
            System.out.println("Task name connot be empty, try again: ");
            taskName = scanner.nextLine();
        }
        taskName = taskName.trim();
        System.out.println("Please type the date of the task (format: YYYY-MM-DD): ");
        LocalDate taskLocalDate = getTaskDate();
        System.out.println("Please type the place of the task (optional, hit enter if you don't"
                + "wish to enter this info): ");
        String taskPlace = scanner.nextLine().trim();
        System.out.println("Please type the comment of the task (optional, hit enter if you don't"
                + "wish to enter this info): ");
        String taskComment = scanner.nextLine().trim();
        user.tasksManager.addTask(taskName, taskLocalDate, taskPlace, taskComment);
    }

    private static void deleteTask() {
        System.out.println("You have chosen: 7. Delete task.%n%n");
        int userChoice = getMiniUserChoice();
        switch(userChoice) {
            case 1:
                int taskNumber = getTaskNumber();
                user.tasksManager.deleteTask(taskNumber);
                break;
            case 2:
                String taskName = getTaskName();
                user.tasksManager.deleteTask(taskName);
                break;
            default:
                System.err.println("Unrecognized user mini choice. This should never happen!");
                break;
        }
    }

    private static void markAsCompleted() {
        System.out.printf("%nYou have chosen: 4. Mark task as completed.%n ");
        int userChoice = getMiniUserChoice();
        switch(userChoice) {
            case 1:
                int taskNumber = getTaskNumber();
                user.tasksManager.completeTask(taskNumber);
                break;
            case 2:
                String taskName = getTaskName();
                user.tasksManager.completeTask(taskName);
                break;
            default:
                System.err.println("Unrecognized user mini choice. This should never happen!");
                break;
        }
    }

    private static int getMiniUserChoice() {
        System.out.printf("Please type 1 to select task by number or type 2 to select task by name.%n");
        String userChoice = scanner.nextLine();
        while(!DataValidator.validateMiniUserChoice(userChoice)) {
            System.out.println("Invalid selection. Please select either 1 or 2.");
            userChoice = scanner.nextLine();
        }
        return Integer.parseInt(userChoice);
    }

    private static int getTaskNumber() {
        System.out.println("Please type task number: ");
        String taskNumber = scanner.nextLine();
        while(!(DataValidator.validateTaskNumber(taskNumber))) {
            System.out.println("Invalid task number, only digits are allowed.");
            taskNumber = scanner.nextLine();
        }
        return Integer.parseInt(taskNumber);
    }

//    private static String getTaskName() {
//        System.out.println("Enter the task name: ");
//        return scanner.nextLine();
//    }

    private static int getMenuChoice() {

        String menuChoice = scanner.nextLine();
        while(!DataValidator.validateMainMenuChoice(menuChoice)) {
            System.out.println("You need to choose an int in range of 0-7 or 32. Try again.");
            menuChoice = scanner.nextLine();
        }
        return Integer.parseInt(menuChoice);

    }

    private static void printMenu() {
        System.out.println("1. Show me my tasks for today.");
        System.out.println("2. Show me all my todo tasks.");
        System.out.println("3. Show me all my finished tasks.");
        System.out.println("    32. Show me all my tasks.");
        System.out.println("4. Mark task as completed.");
        System.out.println("5. Add task.");
        System.out.println("6. Modify task.");
        System.out.println("7. Delete task.");
        System.out.println("0. Exit.");

        System.out.printf("%nPlease choose one of the options: ");
    }

    private static void exitProgram() {
        if(scanner != null) {
            scanner.close();
        }
        user.tasksManager.saveUserTasks();
    }


}
