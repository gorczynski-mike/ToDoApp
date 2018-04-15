package com.gorczynskimike.todoapp;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskStatusTestSuite {

    @Test
    public void testTaskStatusOrdering() {
        //Given
        List<TaskStatus> taskStatusList = new ArrayList<>();
        taskStatusList.add(TaskStatus.DONE);
        taskStatusList.add(TaskStatus.TODO);
        taskStatusList.add(TaskStatus.DONE);
        taskStatusList.add(TaskStatus.TODO);

        //When
        Collections.sort(taskStatusList);

        //Then
        Assert.assertEquals(TaskStatus.TODO, taskStatusList.get(0));
        Assert.assertEquals(TaskStatus.TODO, taskStatusList.get(1));
        Assert.assertEquals(TaskStatus.DONE, taskStatusList.get(2));
        Assert.assertEquals(TaskStatus.DONE, taskStatusList.get(3));
    }

}
