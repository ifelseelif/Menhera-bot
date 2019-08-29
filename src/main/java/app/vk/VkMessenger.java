package app.vk;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Getter;
import lombok.Setter;
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
        return vkApiClient.messages()
                .send(groupActor)
                .peerId(Integer.parseInt(message.getTo()))
                .message(message.getMessage())
                .randomId((int) (Math.random() * 2048))
                .executeAsRaw();
    }
}
