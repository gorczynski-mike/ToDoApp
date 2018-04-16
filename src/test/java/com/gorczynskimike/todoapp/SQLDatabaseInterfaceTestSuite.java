package com.gorczynskimike.todoapp;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SQLDatabaseInterfaceTestSuite {

    @Test
    public void testDatabaseConnection(){
        //Given
        SQLDatabaseInterface sqlDatabaseInterface = new SQLDatabaseInterface();
        //When
        sqlDatabaseInterface.initialize();
        //Then
        Assert.assertTrue(Files.exists(Paths.get(sqlDatabaseInterface.getDatabaseName() + ".mv.db")));
    }

}
