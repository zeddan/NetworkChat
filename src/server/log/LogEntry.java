package server.log;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zeddan
 *
 */
public abstract class LogEntry {
    File file = new File(".networkchat.log");

    public LogEntry() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public void write(String text) {
        try (PrintWriter out =
                     new PrintWriter(
                     new BufferedWriter(
                     new FileWriter(file.getAbsolutePath(), true)))) {
            out.println(text);
            out.flush();
        } catch(IOException e) {
            System.err.println(e);
        }
    }

}
