package common.Logger;

public class Log {
    private String callee;
    private String log;
    private int level;

    public Log(String callee, String log, int level) {
        this.callee = callee;
        this.log = log;
        this.level = level;
    }

    public String getCallee() {
        return callee;
    }

    public String getLog() {
        return log;
    }
}
