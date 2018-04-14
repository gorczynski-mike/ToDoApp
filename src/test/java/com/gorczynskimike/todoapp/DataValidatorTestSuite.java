package com.gorczynskimike.todoapp;

import org.junit.Assert;
import org.junit.Test;

public class DataValidatorTestSuite {

        @Test
        public void testValidateUserName() {
            //Given
            String correct1 = "adam";
            String correct2 = "JOHN";
            String correct3 = "MiKe";
            String incorrect1 = "adam johns";
            String incorrect2 = "adam1";
            String incorrect3 = "";
            String incorrect4 = "123456";
            String incorrect5 = "!@#$%";
            String[] names = new String[]{correct1, correct2, correct3, incorrect1, incorrect2,
                    incorrect3, incorrect4, incorrect5};
            boolean[] results = new boolean[names.length];
            boolean[] correctResults = new boolean[]{true, true, true, false, false, false, false, false};
            //When
            for(int i=0; i<names.length; i++){
                results[i] = DataValidator.validateUserName(names[i]);
            }
            //Then
            Assert.assertArrayEquals(correctResults, results);
        }

        @Test
        public void testValidateTaskNumber() {
            //Given
            String correct1 = "1";
            String correct2 = "12";
            String correct3 = "1234567";
            String incorrect1 = "";
            String incorrect2 = "abcd";
            String incorrect3 = "123abc";
            String incorrect4 = "12 34 56";
            String incorrect5 = "!@#$%";
            String[] names = new String[]{correct1, correct2, correct3, incorrect1, incorrect2,
                    incorrect3, incorrect4, incorrect5};
            boolean[] results = new boolean[names.length];
            boolean[] correctResults = new boolean[]{true, true, true, false, false, false, false, false};
            //When
            for(int i=0; i<names.length; i++){
                results[i] = DataValidator.validateTaskNumber(names[i]);
            }
            //Then
            Assert.assertArrayEquals(correctResults, results);
        }

        @Test
        public void testValidateTaskDate() {
            //Given
            String correct1 = "2011-2-4";
            String correct2 = "1901-03-12";
            String correct3 = "2100-5-01";
            String incorrect1 = "abcd";
            String incorrect2 = "1911";
            String incorrect3 = "";
            String incorrect4 = "1-1-1970";
            String incorrect5 = "!@#$%";
            String[] dates = new String[]{correct1, correct2, correct3, incorrect1, incorrect2,
                    incorrect3, incorrect4, incorrect5};
            boolean[] results = new boolean[dates.length];
            boolean[] correctResults = new boolean[]{true, true, true, false, false, false, false, false};
            //When
            for(int i=0; i<dates.length; i++){
                results[i] = DataValidator.validateTaskDate(dates[i]);
            }
            //Then
            Assert.assertArrayEquals(correctResults, results);
        }

        @Test
        public void testValidateTaskName() {
            //Given
            String correct1 = "name";
            String correct2 = "na me na me";
            String correct3 = "name !@#$%% 98261 name";
            String incorrect1 = "";
            String[] names = new String[]{correct1, correct2, correct3, incorrect1};
            boolean[] results = new boolean[names.length];
            boolean[] correctResults = new boolean[]{true, true, true, false};
            //When
            for(int i=0; i<names.length; i++){
                results[i] = DataValidator.validateTaskName(names[i]);
            }
            //Then
            Assert.assertArrayEquals(correctResults, results);
        }

        @Test
        public void testValidateMainMenuChoice() {
            //Given
            String correct1 = "0";
            String correct2 = "1";
            String correct3 = "7";
            String incorrect1 = "abcd";
            String incorrect2 = "1911";
            String incorrect3 = "";
            String incorrect4 = "1-1-1970";
            String incorrect5 = "!@#$%";
            String[] choices = new String[]{correct1, correct2, correct3, incorrect1, incorrect2,
                    incorrect3, incorrect4, incorrect5};
            boolean[] results = new boolean[choices.length];
            boolean[] correctResults = new boolean[]{true, true, true, false, false, false, false, false};
            //When
            for(int i=0; i<choices.length; i++){
                results[i] = DataValidator.validateMainMenuChoice(choices[i]);
            }
            //Then
            Assert.assertArrayEquals(correctResults, results);
        }

        @Test
        public void testValidateBinaryUserChoice() {
            //Given
            String correct1 = "1";
            String correct2 = "2";
            String incorrect1 = "0";
            String incorrect2 = "3";
            String incorrect3 = "1910";
            String incorrect4 = "a";
            String incorrect5 = "!@#$%";
            String[] choices = new String[]{correct1, correct2, incorrect1, incorrect2,
                    incorrect3, incorrect4, incorrect5};
            boolean[] results = new boolean[choices.length];
            boolean[] correctResults = new boolean[]{true, true, false, false, false, false, false};
            //When
            for(int i=0; i<choices.length; i++){
                results[i] = DataValidator.validateBinaryUserChoice(choices[i]);
            }
            //Then
            Assert.assertArrayEquals(correctResults, results);
        }

}
