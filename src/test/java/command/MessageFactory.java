package command;

import model.Message;

/**
 * @author Arthur Kupriyanov
 */
public class MessageFactory {
    public static Message generateBlankMessage(String context){
        return new Message(context, null, null, null);
    }
}
