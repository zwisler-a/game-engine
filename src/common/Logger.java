package common;

public class Logger {

    public static final int WARNING = 1;
    public static final int ERROR = 0;
    public static final int INFO = 2;
    public static final int LOG = 3;
    public static final int DEBUG = 4;

    public static int loggingLevel = Logger.WARNING;

    public static void warning(String s) {
        if (loggingLevel >= WARNING) {
            System.out.println(s);
        }
    }

    public static void error(String s) {
        if (loggingLevel >= ERROR) {
            System.err.println(s);
        }
    }

    public static void info(String s) {
        if (loggingLevel >= INFO) {
            System.out.println(s);
        }
    }

    public static void debug(String s) {
        if (loggingLevel >= DEBUG) {
            System.out.println(s);
        }
    }

    public static void log(String logging) {
        if (loggingLevel >= LOG) {
            System.out.println(logging);
        }
    }
}
