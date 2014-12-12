package server.logging;

/**
 * Created by mitch10e on 11/4/14.
 */
public enum LogLevel {
    OFF, ERROR, WARN, INFO, DEBUG;

    public static int getLevel(LogLevel level) {
        int result = 0;
        switch(level) {
            case OFF:
                result = 0;
                break;
            case ERROR:
                result = 1;
                break;
            case WARN:
                result = 2;
                break;
            case INFO:
                result = 3;
                break;
            case DEBUG:
                result = 4;
                break;
        }
        return result;
    }
    public static LogLevel fromString(String strLevel) {
        switch (strLevel.toLowerCase()){
            case "severe":
                return LogLevel.ERROR;
            case "warning":
                return LogLevel.WARN;
            case "info":
                return LogLevel.INFO;
            case "config":
                return LogLevel.INFO;
            case "fine":
                return LogLevel.DEBUG;
            case "finer":
                return LogLevel.DEBUG;
            case "finest":
                return LogLevel.OFF;
            default:
                return null;
        }
    }
}
