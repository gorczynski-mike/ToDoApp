package com.gorczynskimike.todoapp;

import org.junit.Assert;
import org.junit.Test;

public class UserTestSuite {

    @Test
    public void testUserConstructorSuccessfullyCreatesTasksManager() {
        //Given
        //When
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();

        //Then
        Assert.assertNotNull(tasksManager);
    }

}
