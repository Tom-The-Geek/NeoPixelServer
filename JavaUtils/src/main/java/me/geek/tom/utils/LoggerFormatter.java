package me.geek.tom.utils;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return "[" + new Date(record.getMillis()).toString() +
                "] [" + record.getLoggerName() + "/" + record.getLevel() + "] : " + record.getMessage() + "\n";
    }
}
