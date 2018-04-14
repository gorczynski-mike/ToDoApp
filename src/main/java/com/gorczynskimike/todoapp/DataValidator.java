package com.gorczynskimike.todoapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The only purpose of this class is to validate user input for different types of information.
 */
@SuppressWarnings("WeakerAccess")
public class DataValidator {

    private static final Pattern userNamePattern = Pattern.compile("[a-zA-Z]+");

    private static final Pattern taskNumberPattern = Pattern.compile("\\d+");
    private static final Pattern taskDatePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern taskNamePattern = Pattern.compile(".*[\\S]+.*");

    private static final Pattern mainMenuChoicePattern = Pattern.compile("[0-7]|3[2-4]");
    private static final Pattern binaryUserChoicePattern = Pattern.compile("[12]");

    /**
     * Validate user name according to pattern: userNamePattern
     * @param userName name that will be checked
     * @return true if given name is valid, false otherwise
     */
    public static boolean validateUserName(String userName) {
        Matcher matcher = userNamePattern.matcher(userName);
        return matcher.matches();
    }

    /**
     * Validate task number according to pattern: taskNumberPattern
     * @param taskNumber number that will be checked
     * @return true if number is valid, false otherwise
     */
    public static boolean validateTaskNumber(String taskNumber) {
        Matcher matcher = taskNumberPattern.matcher(taskNumber);
        return matcher.matches();
    }

    /**
     * Validate task date according to pattern: taskDatePattern
     * @param date task date that will be checked
     * @return true if date is valid, false otherwise
     */
    public static boolean validateTaskDate(String date) {
        Matcher matcher = taskDatePattern.matcher(date);
        return matcher.matches();
    }

    /**
     * Validate task name according to pattern: taskNamePattern
     * @param taskName task name that will be checked
     * @return true if name is valid, false otherwise
     */
    public static boolean validateTaskName(String taskName) {
        Matcher matcher = taskNamePattern.matcher(taskName);
        return matcher.matches();
    }

    /**
     * Validates main menu choice. The point of this method is to prevent choosing menu items that do not exist.
     * @param mainMenuChoice The option typed by the user.
     * @return true if the option chosen by the user exists, false otherwise
     */
    public static boolean validateMainMenuChoice(String mainMenuChoice) {
        Matcher matcher = mainMenuChoicePattern.matcher(mainMenuChoice);
        return matcher.matches();
    }

    /**
     * Validates binary user choice - the only valid options for binary user choice is either 1 (selecting task by number)
     * or 2 (selecting task by name). The point of this method is to only allow these two choices to be made.
     * @param miniUserChoice The option typed by the user.
     * @return True if user typed either 1 or 2, false otherwise.
     */
    public static boolean validateBinaryUserChoice(String miniUserChoice) {
        Matcher matcher = binaryUserChoicePattern.matcher(miniUserChoice);
        return matcher.matches();
    }

}
