package com.gorczynskimike.todoapp;

import java.util.List;

public class ConsolePrinter {

    private String tableHeader = String.format("| %s| %5s| %30s | %10s | %30s | %30s|",
            "status","number","name","date","place","comments");
    @SuppressWarnings("ReplaceAllDot")
    private String tableBorder = tableHeader.replaceAll(".","=");

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
     * prints given Tasks list in a table, header and footer included
     * @param tasksList list of Tasks to be printed
     */
    public void printTasks(List<Task> tasksList){
        printTableHead();
        for(Task task: tasksList) {
            System.out.println(getConsoleStringRepresentation(task));
        }
        printTableFoot();
    }

    /**
     * String that can be used to print task to console in a table
     * @return table formatted String representation of the task
     */
    private String getConsoleStringRepresentation(Task task) {
        return String.format("|%6s | %05d | %30s | %10s | %30s | %30s|",
                task.getTaskStatus(),
                task.getTaskNumber(),
                task.getTaskName(),
                task.getTaskDate(),
                task.getTaskPlace(),
                task.getTaskComments());
    }

}
