package me.david.messageserver.logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/*
 * For all Loging actions
 * with Files
 */
public class GLogger {

    private Logger logger;
    private Level level;

    public GLogger(Level level) {
        this.level = level;
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        this.logger = root;
        FileHandler txt;
        try {
            String filename = new SimpleDateFormat("dd MM yyyy").format(new Date()) + "-1";
            File logdir = new File("./Logs/");
            if(!logdir.exists()) {
                logdir.mkdirs();
            }
            while(new File("./Logs/" + filename + ".log").exists()) {
                int next = Integer.valueOf(filename.split("-")[1]);
                next++;
                filename = filename.substring(0, filename.split("-")[0].length()) + "-" + next;
            }
            txt = new FileHandler("./Logs/" + filename + ".log");
            root.setLevel(level);
            txt.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String ret = "";
                    if(record.getLevel().intValue() >= Level.WARNING.intValue()) ret = "WARN ";
                    ret += record.getLevel() + ":";
                    ret += new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(record.getMillis());
                    ret += " " + record.getMessage();
                    ret += "\r\n";
                    return ret;
                }
            });
            root.addHandler(txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GLogger info(String info) {
        logger.info(info);
        System.out.println(info);
        return this;
    }

    public GLogger infoConsole(String info, boolean in) {
        logger.info("Console " + (in ? "<-" : "->") + " " + info);
        System.out.println(info);
        return this;
    }

    public GLogger infoLog(String message) {
        logger.info(message);
        return this;
    }

    public Logger getLogger() {
        return logger;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
