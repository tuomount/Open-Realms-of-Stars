package org.openRealmOfStars.utilities;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;
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
 *
 * Test for Logger class
 *
 */
public class LoggerTest {

    private static final int LOG_SIZE = 5;
    private static final int DEFAULT_LOG_SIZE = 11;
    private Logger logger = new Logger(LOG_SIZE);

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testCreateDefaultLogger(){
        logger = new Logger();
        assertEquals(DEFAULT_LOG_SIZE, logger.size());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testAddLogShouldRotateMessagesAndPutNewMessageToTheBeginningOfTheArray() {
        String[] actualResult;
        String[] expectedResult = new String[LOG_SIZE];
        String addMessage = "Message";
        expectedResult[0] = addMessage;
        for (int i = 1; i < LOG_SIZE; i++) {
            expectedResult[i] = "";
        }

        logger.addLog(addMessage);

        actualResult = logger.getLogMessages();
        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testAddLogShouldNotThrowExceptionWhenGetMoreLogMessages() {
        for (int i = 1; i <= LOG_SIZE; i++) {
            logger.addLog("Message " + i);
        }

        logger.addLog("Message " + LOG_SIZE + 1);
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testSizeShouldReturnTheLoggerSize(){
        assertEquals(LOG_SIZE, logger.size());
    }

    @Test(expected = RuntimeException.class)
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGetMessageShouldThrowExceptionWhereIndexLowerThanZero(){
        logger.getMessage(-1);
    }

    @Test(expected = RuntimeException.class)
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGetMessageShouldThrowExceptionWhereIndexGreaterThanLoggerSize(){
        logger.getMessage(10);
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGetMessageShouldReturnTheMessageByIndex(){
        logger.addLog("Message 1");
        logger.addLog("Message 2");
        logger.addLog("Message 3");
        logger.addLog("Message 4");

        assertEquals("Message 4", logger.getMessage(0));
        assertEquals("Message 3", logger.getMessage(1));
        assertEquals("Message 2", logger.getMessage(2));
        assertEquals("Message 1", logger.getMessage(3));
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testToString(){
        logger.addLog("Message 1");
        logger.addLog("Message 2");
        logger.addLog("Message 3");
        logger.addLog("Message 4");

        assertEquals("\"\"\n"
            + "\"Message 1\"\n"
            + "\"Message 2\"\n"
            + "\"Message 3\"\n"
            + "\"Message 4\"\n", logger.toString());
    }

}