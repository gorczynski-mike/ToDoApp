package com.gorczynskimike.todoapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * The only purpose of this class is to validate user input of different types of information.
 *
 */
public class DataValidator {

    private static final Pattern namePattern = Pattern.compile("[a-zA-Z]+");
    private static final Pattern taskNumberPattern = Pattern.compile("\\d+");
    private static final Pattern miniUserChoicePattern = Pattern.compile("[12]");
    private static final Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern taskNamePattern = Pattern.compile(".*[\\S]+.*");
    private static final Pattern mainMenuChoicePattern = Pattern.compile("[0-7]|32");

    public static boolean validateName(String userName) {
        Matcher matcher = namePattern.matcher(userName);
        return matcher.matches();
    }

    public static boolean validateTaskNumber(String taskNumber) {
        Matcher matcher = taskNumberPattern.matcher(taskNumber);
        return matcher.matches();
    }

    public static boolean validateMiniUserChoice(String miniUserChoice) {
        Matcher matcher = miniUserChoicePattern.matcher(miniUserChoice);
        return matcher.matches();
    }

    public static boolean validateDate(String date) {
        Matcher matcher = datePattern.matcher(date);
        return matcher.matches();
    }

    public static boolean validateTaskName(String taskName) {
        Matcher matcher = taskNamePattern.matcher(taskName);
        return matcher.matches();
    }

    public static boolean validateMainMenuChoice(String mainMenuChoice) {
        Matcher matcher = mainMenuChoicePattern.matcher(mainMenuChoice);
        return matcher.matches();
    }

}
