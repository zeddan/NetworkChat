package client.interfaces;

import message.Message;

import java.util.ArrayList;

/**
 * @author zeddan
 *
 */
public interface ClientGUIListener {
    void onMessage(Message message);
    void newPrivateMessage(String sender, String recipient);
    void newGroup(ArrayList<String> users, String groupName);
}
