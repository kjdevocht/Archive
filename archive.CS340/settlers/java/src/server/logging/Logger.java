package server.logging;

import static server.logging.LogLevel.*;

/**
 * Created by mitch10e on 11/4/14.
 */
public class Logger implements ILogger{

    private static Logger singleton;
    private LogLevel level;

    public Logger() {
        level = DEBUG;
    }

    public Logger(LogLevel level) {
        setLoggingLevel(level);
    }

    public static Logger getSingleton() {
        if(singleton == null) {
            singleton = new Logger();
        }
        return singleton;
    }

    @Override
    public void setLoggingLevel(LogLevel level) {
        this.level = level;
        System.out.println("[" + level + "]\t\tLogging changed to: " + getLevel(level));
    }

    @Override
    public void setLoggingLevel(String level) throws IllegalArgumentException {
        try {
            LogLevel tmp = LogLevel.fromString(level);;

            if(tmp== null) {
                System.out.println("[" + this.level.toString() + "]\t\tInvalid Logging Level: " + level);
                return;
            }
            this.level = tmp;
            System.out.println("[" + level.toUpperCase() + "]\t\tLogging changed to: " + level);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Logging Level: " + level);
        }
    }

    @Override
    public void error(String message) {
        if(getLevel(this.level) >= getLevel(ERROR)) {
            System.out.println("[ERROR]\t\t" + message);
        }
    }

    @Override
    public void warn(String message) {
        if(getLevel(this.level) >= getLevel(WARN)) {
            System.out.println("[WARN]\t\t" + message);
        }
    }

    @Override
    public void info(String message) {
        if(getLevel(this.level) >= getLevel(INFO)) {
            System.out.println("[INFO]\t\t" + message);
        }
    }

    @Override
    public void debug(String message) {
        if(getLevel(this.level) >= getLevel(DEBUG)) {
            System.out.println("[DEBUG]\t\t" + message);
        }
    }
}
