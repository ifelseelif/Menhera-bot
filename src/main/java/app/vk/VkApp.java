package app.vk;

import app.SocialApp;
import app.handler.AppHandler;
import com.vk.api.sdk.callback.longpoll.CallbackApiLongPoll;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.GroupAuthResponse;
import model.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Arthur Kupriyanov
 */
public class VkApp extends SocialApp {
    private final Logger log = Logger.getLogger(VkApp.class);
    private VkApiClient vkApiClient;
    private final TransportClient transportClient = new HttpTransportClient();
    private final String ACCESS_TOKEN;
    private final int GROUP_ID;
    {
        String groupId = System.getenv("VK_GROUP_ID");
        String accessToken = System.getenv("VK_ACCESS_TOKEN");
        if (groupId == null || accessToken == null ){
            Properties properties = new Properties();
            try {
                properties.load(VkApp.class.getClassLoader().getResourceAsStream("vk-app.properties"));
            } catch (IOException e) {
                log.error(e);
            }
            groupId = properties.getProperty("VK_GROUP_ID");
            accessToken = properties.getProperty("VK_ACCESS_TOKEN");
            log.info("Vk App auth configs downloaded from properties file");
        } else {
            log.info("VK App auth configs set from env");
        }

        GROUP_ID = Integer.valueOf(groupId);
        ACCESS_TOKEN = accessToken;
    }



    public VkApp(AppHandler handler) {
        super(handler);
    }

    public void launch() {
        log.info("Vkontakte launched in Thread : " + Thread.currentThread().getName());

        try {
            GroupActor actor = init();
            log.debug("Setting groups longpoll settings...");
            this.vkApiClient.groupsLongPoll().setLongPollSettings(actor)
                    .enabled(true)
                    .messageAllow(true)
                    .messageNew(true)
                    .execute();
            log.debug("Groups longpoll settings setted");
            CallbackApiLongPoll callbackApiLongPoll = new CallbackApiLongPollHandler(this.vkApiClient, actor, this);
            log.debug("Running callbackApiLongPoll...");
            callbackApiLongPoll.run();
        } catch (ClientException | ApiException e) {
            log.error("Could not start vk application",e);
        }

    }

    private GroupActor init()  {
        this.vkApiClient = new VkApiClient(this.transportClient);
        return new GroupActor(GROUP_ID, ACCESS_TOKEN);
    }
}
