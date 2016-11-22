package org.openRealmOfStars.utilities;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.io.PrintStream;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 *
 * Test for ErrorLogger
 *
 */

public class ErrorLoggerTest {

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testErrorLoggerShouldWriteToDefaultErrorOutput() {
        String errorMessage = "Error message";
        PrintStream err =  System.err;
        System.setErr(Mockito.mock(System.err.getClass()));

        ErrorLogger.log(errorMessage);

        Mockito.verify(System.err, Mockito.times(1)).println(errorMessage);
        System.setErr(err);
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testErrorLoggerWhenLogGetException() {
        Exception exception = new Exception("Message");
        PrintStream err =  System.err;
        System.setErr(Mockito.mock(System.err.getClass()));

        ErrorLogger.log(exception);

        Mockito.verify(System.err, Mockito.times(1)).println(exception.getMessage());
        System.setErr(err);
    }

}
