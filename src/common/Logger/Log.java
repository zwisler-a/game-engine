package common.Logger;

import java.util.Date;

public class Log {
    private final int line;
    private String callee;
    private String log;
    private int level;
    private Date time;

    public Log(String callee, int line, String log, int level) {
        this.callee = callee;
        this.log = log;
        this.level = level;
        this.line = line;
        this.time = new Date();
    }

    public String getCallee() {
        return callee;
    }

    public int getLine(){
        return this.line;
    }

    public String getLog() {
        return log;
    }

    public Date getTime() {
        return time;
    }
}
