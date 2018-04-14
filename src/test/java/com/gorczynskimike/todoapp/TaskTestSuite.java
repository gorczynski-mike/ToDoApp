package com.gorczynskimike.todoapp;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TaskTestSuite {

    @Test
    public void testNewTaskHasStatusTodo() {
        //Given
        //When
        Task task = new Task(1, "1", LocalDate.now(), "1", "1");
        //Then
        Assert.assertEquals(task.getTaskStatus(), TaskStatus.TODO);
    }

    @Test
    public void testCompleteTask() {
        //Given
        Task task = new Task(1, "1", LocalDate.now(), "1", "1");
        //When
        task.completeTask();
        //Then
        Assert.assertEquals(task.getTaskStatus(), TaskStatus.DONE);
    }

    @Test
    public void testEncodeTask() {
        //Given
        Task task = new Task(1,"name", LocalDate.of(2012,2,4),
                "place", "comment");
        //When
        String encodedTask = task.encodeTask();
        //Then
        Assert.assertEquals("[TODO] - [1] - [name] - [2012-02-04] - [place] - [comment]", encodedTask);
    }

    @Test
    public void testDecodeTask() {
        //Given
        Task task1 = new Task(10, "taskName1", LocalDate.of(1999,4,4),
                "TaskPlace1", "TaskComment1");
        Task task2 = new Task(31, "Nametask2", LocalDate.of(2012,12,9),
                "place", "");
        task2.completeTask();
        Task task3 = new Task(2019, "name name name", LocalDate.of(2020,1,1),
                "", "comment");
        String encodedTask1 = task1.toString();
        String encodedTask2 = task2.toString();
        String encodedTask3 = task3.toString();
        //When
        Task decodedTask1 = Task.decodeTask(encodedTask1);
        Task decodedTask2 = Task.decodeTask(encodedTask2);
        Task decodedTask3 = Task.decodeTask(encodedTask3);
        //Then
        Assert.assertEquals(task1, decodedTask1);
        Assert.assertEquals(task2, decodedTask2);
        Assert.assertEquals(task3, decodedTask3);

    }

}
