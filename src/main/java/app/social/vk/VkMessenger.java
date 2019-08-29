package app.social.vk;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ClientException;
import model.Message;

/**
 * @author Arthur Kupriyanov
 */

public class VkMessenger {
    private GroupActor groupActor;
    private VkApiClient vkApiClient;
    public VkMessenger(GroupActor groupActor, VkApiClient vkApiClient){
        this.groupActor = groupActor;
        this.vkApiClient = vkApiClient;
    }

    public ClientResponse sendMessage(Message message) throws ClientException {
        if (message == null) return null;
        return vkApiClient.messages()
                .send(groupActor)
                .peerId(Integer.parseInt(message.getTo()))
                .message(message.getMessage())
                .randomId((int) (Math.random() * 2048))
                .executeAsRaw();
    }
}
