package common.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static common.Logger.LoggingLevel.*;

public class Logger {

    // Constants for coloring the log
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Level at witch events should be triggered
     */
    public static int loggingLevel = WARNING;

    private static LinkedList<Log> log = new LinkedList<>();

    public static void warning(String s) {
        Log l = generateLog(s, WARNING);
        if (loggingLevel >= WARNING) {
            printLog(l);
        }
    }

    public static void error(String s) {
        Log l = generateLog(ANSI_RED + s + ANSI_RESET, LoggingLevel.ERROR);
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

    public static void log(Object s) {
        Log l = generateLog(s.toString(), LoggingLevel.LOG);
        if (loggingLevel >= LOG) {
            printLog(l);
        }
    }

    public static void debug(Object s) {
        Log l = generateLog(s.toString(), LoggingLevel.DEBUG);
        if (loggingLevel >= DEBUG) {
            printLog(l);
        }
    }

    private static void printLog(Log l) {
        System.out.format("%-64s%-32s%1s",
                "[" + ANSI_GREEN + l.getCallee() + ANSI_RESET + ":" +
                        ANSI_BLUE + l.getLine() + ANSI_RESET +
                        "]:", l.getLog(), "\n");
    }

    private static Log generateLog(String info, int level) {
        Log l = new Log(getCallee(), getCalleeLine(), info, level);
        log.add(l);
        return l;
    }

    private static String getCallee() {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        return trace.getClassName();
    }

    private static int getCalleeLine() {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        return trace.getLineNumber();
    }

    /**
     * Writes the complete log into a logfile in case of a crash
     * @param exception Last catched exception
     * @return success or failure (true/false)
     */
    public static boolean outputLogToFile(Exception exception) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm_ss_SSS");
        exception.printStackTrace();
        return outputLogToFile("log/" + dateFormat.format(new Date()) + ".log", exception);
    }

    /**
     * Writes the complete log into a logfile in case of a crash
     * @return uccess or failure (true/false)
     */
    public static boolean outputLogToFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm_ss_SSS");
        return outputLogToFile("log/" + dateFormat.format(new Date()) + ".log", null);
    }

    /**
     * Writes the complete log into a logfile in case of a crash
     * @param path Location of the logfile
     * @param exception Last catched exception
     * @return success or failure (true/false)
     */
    public static boolean outputLogToFile(String path, Exception exception) {
        File file = new File(path);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
            PrintWriter printWriter = new PrintWriter(file);
            for (Log l : log) {
                printWriter.print("[" + dateFormat.format(l.getTime()) + " - " + l.getCallee() + "]: \t\t\t" + l.getLog() + "\n");
            }
            if (exception != null) {
                exception.printStackTrace(printWriter);
            }
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
