package server.log;

/**
 * @author zeddan
 *
 */
public class SystemEntry extends LogEntry {

    // [2016/05/16 23:05:02] [INFO] simon connected
    private String message = "[%s] [%s] %s";
    private String text;
    private SystemEntryType systemEntryType;
    private String date;

    public SystemEntry(String text, SystemEntryType systemEntryType) {
        this.text = text;
        this.systemEntryType = systemEntryType;
        this.date = getCurrentDate();
        super.write(this.toString());
    }

    public String toString() {
        return String.format(
                message,
                date,
                systemEntryType.name(),
                text
        );
    }

}
