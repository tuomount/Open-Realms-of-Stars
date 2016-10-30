package org.openRealmOfStars.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoggerTest {

    private static final int LOG_SIZE = 5;
    private Logger logger = new Logger(LOG_SIZE);

    @Test
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
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddLogShouldNotThrowExceptionWhenGetMoreLogMessages() {
        for (int i = 1; i <= LOG_SIZE; i++) {
            logger.addLog("Message " + i);
        }

        logger.addLog("Message " + LOG_SIZE + 1);
    }

}