package app.vk;

import app.handler.AppHandler;
import com.vk.api.sdk.callback.longpoll.CallbackApiLongPoll;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.objects.messages.Message;

/**
 * @author Arthur Kupriyanov
 */
public class CallbackApiLongPollHandler extends CallbackApiLongPoll {
    private AppHandler handler;

    public CallbackApiLongPollHandler(VkApiClient client, GroupActor actor, VkApp app) {
        super(client, actor);
        this.handler = app.getAppHandler();
    }

    @Override
    public void messageNew(Integer groupId, Message message) {
        if (message != null && !message.isOut())
            handler.receive(model.Message.getInstance(message));
        //handler.receive(new model.Message(message.getText(), "" + message.getFromId(), "" + message.getPeerId(), MessageSourceType.VK));
    }

    @Override
    public void messageNew(Integer groupId, String secret, Message message) {
        if (message != null && !message.isOut())
        handler.receive(model.Message.getInstance(message));
    }
}
