package com.gorczynskimike.todoapp;

import org.junit.*;
import org.junit.rules.TestName;

import java.time.LocalDate;

public class TasksManagerTestSuite {

    private static int testCount = 0;

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void beforeAllTests(){
        System.out.println("TasksManager Test Suite: start");
        System.out.println();
    }

    @AfterClass
    public static void afterAllTests(){
        System.out.println("TasksManager Test Suite: end");
    }

    @Before
    public void beforeTest(){
        testCount++;
        System.out.println("Test #" + testCount + " : " + testName.getMethodName() + " : start");
    }

    @After
    public void afterTest(){
        System.out.println("Test #" + testCount + " : " + testName.getMethodName() + " : end");
        System.out.println();
    }

    @Test
    public void testTaskListForNewUserIsEmpty() {
        //Given
        //When
        User user = new User("user");
        TasksManager tasksManager = user.getTasksManager();

        //Then
        Assert.assertEquals(0, tasksManager.getNumberOfTasks());
    }

    @Test
    public void testAddTask() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task = new Task(1,"1", LocalDate.now(),"1", "1");

        //When
        tasksManager.addTask(task);

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTasks());
    }

    @Test
    public void testAddNullTask() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task = null;

        //When
        tasksManager.addTask(task);

        //Then
        Assert.assertEquals(0, tasksManager.getNumberOfTasks());
    }

    @Test
    public void testAddTaskAlreadyExisting() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"1", LocalDate.now(),"1", "1");
        Task task2 = new Task(1,"1", LocalDate.now(),"1", "1");
        tasksManager.addTask(task1);

        //When
        tasksManager.addTask(task2);

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTasks());
    }

    @Test
    public void testNewTaskHasStatusToDo() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"1", LocalDate.now(),"1", "1");

        //When
        tasksManager.addTask(task1);

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTodoTasks());
        Assert.assertEquals(0, tasksManager.getNumberOfDoneTasks());
    }

    @Test
    public void testCompleteTaskByNumber() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"1", LocalDate.now(),"1", "1");
        tasksManager.addTask(task1);

        //When
        tasksManager.completeTask(1);

        //Then
        Assert.assertEquals(0, tasksManager.getNumberOfTodoTasks());
        Assert.assertEquals(1, tasksManager.getNumberOfDoneTasks());
    }

    @Test
    public void testCompleteTaskByName() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"name", LocalDate.now(),"1", "1");
        tasksManager.addTask(task1);

        //When
        tasksManager.completeTask("name");

        //Then
        Assert.assertEquals(0, tasksManager.getNumberOfTodoTasks());
        Assert.assertEquals(1, tasksManager.getNumberOfDoneTasks());
    }

    @Test
    public void testCompleteNullTask() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"1", LocalDate.now(),"1", "1");
        tasksManager.addTask(task1);

        //When
        tasksManager.completeTask(null);

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTodoTasks());
        Assert.assertEquals(0, tasksManager.getNumberOfDoneTasks());
    }

    @Test
    public void testCompleteTaskNotExisting() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"1", LocalDate.now(),"1", "1");
        Task task2 = new Task(2,"2", LocalDate.now(),"2", "2");
        tasksManager.addTask(task1);

        //When
        tasksManager.completeTask(2);

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTodoTasks());
        Assert.assertEquals(0, tasksManager.getNumberOfDoneTasks());
    }

    @Test
    public void testDeleteTaskByTaskNumber() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"1", LocalDate.now(),"1", "1");
        Task task2 = new Task(2,"2", LocalDate.now(),"2", "2");
        tasksManager.addTask(task1);
        tasksManager.addTask(task2);

        //When
        tasksManager.deleteTask(2);

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTasks());
        Assert.assertTrue(tasksManager.taskExists(1));
        Assert.assertFalse(tasksManager.taskExists(2));
    }

    @Test
    public void testDeleteTaskByTaskName() {
        //Given
        User user = new User("name");
        TasksManager tasksManager = user.getTasksManager();
        Task task1 = new Task(1,"name1", LocalDate.now(),"1", "1");
        Task task2 = new Task(2,"name2", LocalDate.now(),"2", "2");
        tasksManager.addTask(task1);
        tasksManager.addTask(task2);

        //When
        tasksManager.deleteTask("name2");

        //Then
        Assert.assertEquals(1, tasksManager.getNumberOfTasks());
        Assert.assertTrue(tasksManager.taskExists(1));
        Assert.assertFalse(tasksManager.taskExists(2));
    }

}
