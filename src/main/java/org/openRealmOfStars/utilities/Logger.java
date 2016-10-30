package org.openRealmOfStars.utilities;

public class Logger {

    private static final int DEFAULT_LOG_SIZE = 11;

    String[] textLog;

    public Logger() {
        this(DEFAULT_LOG_SIZE);
    }

    public Logger(int logSize) {
        textLog = new String[logSize];
        for (int i = 0; i < logSize; i++) {
            textLog[i] = "";
        }
    }

    public void addLog(String message) {
        System.arraycopy(textLog, 0, textLog, 1, textLog.length - 1);
        textLog[0] = message;
    }

    public String[] getLogMessages() {
        return textLog;
    }
}
