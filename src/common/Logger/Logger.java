package common.Logger;

import sun.misc.Queue;

import static common.Logger.LoggingLevel.*;

public class Logger {

    public static int loggingLevel = WARNING;

    private static Queue<Log> log = new Queue<>();

    public static void warning(String s) {
        Log l = generateLog(s, WARNING);
        if (loggingLevel >= WARNING) {
            printLog(l);
        }
    }

    public static void error(String s) {
        Log l = generateLog(s, LoggingLevel.ERROR);
        if (loggingLevel >= ERROR) {
            printLog(l);
        }
    }

    public static void info(String s) {
        Log l = generateLog(s, LoggingLevel.INFO);
        if (loggingLevel >= INFO) {
            printLog(l);
        }
    }

    public static void debug(String s) {
        Log l = generateLog(s, LoggingLevel.DEBUG);
        if (loggingLevel >= DEBUG) {
            printLog(l);
        }
    }

    public static void log(String s) {
        Log l = generateLog(s, LoggingLevel.LOG);
        if (loggingLevel >= LOG) {
            printLog(l);
        }
    }

    private static void printLog(Log l) {
        System.out.format("%-50s%-32s%1s","[" + l.getCallee() + "]:" ,l.getLog(),"\n");
    }

    private static Log generateLog(String info, int level) {
        Log l = new Log(getCallee(), info, level);
        log.enqueue(l);
        return l;
    }

    private static String getCallee() {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        return trace.getClassName()+":"+trace.getLineNumber();
    }
}
