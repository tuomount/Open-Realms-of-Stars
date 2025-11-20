package org.openRealmOfStars.utilities;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016 Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

/**
 * Test for ErrorLogger
 *
 */

public class ErrorLoggerTest {

    private PrintStream originalErr;
    private ByteArrayOutputStream capturedOutput;

    @Before
    public void setUp() {
        originalErr = System.err;
        capturedOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(capturedOutput));
    }

    @After
    public void tearDown() {
        System.setErr(originalErr);
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testErrorLoggerShouldWriteToDefaultErrorOutput() {
        String errorMessage = "Error message";
        ErrorLogger.log(errorMessage);
        String output = capturedOutput.toString();
        assertTrue("Expected error message to be logged", output.contains(errorMessage));
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testErrorLoggerWhenLogGetException() {
        Exception exception = new Exception("Message");
        ErrorLogger.log(exception);
        String output = capturedOutput.toString();
        StackTraceElement stackTraceElement = exception.getStackTrace()[0];
        String expectedOutput = stackTraceElement.getClassName()
                + " - "
                + "Line " + stackTraceElement.getLineNumber()
                + " - "
                + exception.getMessage();
        assertTrue("Expected exception information to be logged", output.contains(expectedOutput));
    }

}
