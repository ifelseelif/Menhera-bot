package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
@Data
@AllArgsConstructor
@ToString
public class Message {
    public Message(String message, String from, String to, MessageSourceType type){
        this.message = message;
        this.from = from;
        this.to = to;
        this.sourceType = type;
    }
    private MessageSourceType sourceType;
    private String from;
    private String to;
    private String message;
    private List<Object> attachments;

    public static Message reverseMessage(Message message){
        return new Message(message.message, message.to, message.from, message.sourceType);
    }
    public void toLowerCase(){
        if (this.message != null) this.message = this.message.toLowerCase();
    }

    public static Message getInstance(com.vk.api.sdk.objects.messages.Message vkMessage) {
        try {
            return new Message(vkMessage.getBody(), vkMessage.getUserId().toString(), null, MessageSourceType.VK);

        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Message getInstance(org.telegram.telegrambots.meta.api.objects.Message tlgrmMessage){
        return new Message(tlgrmMessage.getText(), tlgrmMessage.getChatId().toString(), null, MessageSourceType.TELEGRAM);
    }
}
