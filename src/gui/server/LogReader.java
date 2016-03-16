package gui.server;

import java.io.*;

/**
 * @author zeddan
 *
 */
public class LogReader {
    File file = new File(".networkchat.log");

    public LogReader() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public String read() {
        String str = "";
        try (BufferedReader br =
                     new BufferedReader(
                     new InputStreamReader(
                     new FileInputStream(file)))) {
            String line = br.readLine();
            while (line != null) {
                str += line + "\n";
                line = br.readLine();
            }
        } catch(IOException e) {
            System.err.println(e);
        }
        return str;
    }
}
