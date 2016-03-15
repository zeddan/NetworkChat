package server.log;

/**
 * @author zeddan
 *
 */
public class UserEntry extends LogEntry {

    // [2016/05/16 23:05:02] (groupname) <simon> hej alla
    private String message = "[%s] (%s) <%s> %s";
    private String text;
    private String sender;
    private String group;
    private String date;

    public UserEntry(String text, String sender, String group) {
        this.text = text;
        this.sender = sender;
        this.group = group;
        this.date = getCurrentDate();
        super.write(this.toString());
    }

    public String toString() {
        return String.format(
                message,
                date,
                group,
                sender,
                text);
    }


}
