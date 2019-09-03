package app.social.vk;

import app.social.SocialApp;
import app.handler.AppHandler;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Arthur Kupriyanov
 */
public class VkApp extends SocialApp {
    private static final Logger log = Logger.getLogger(VkApp.class);
    private static VkApiClient vkApiClient;
    private static GroupActor groupActor;
    private final TransportClient transportClient = new HttpTransportClient();
    private static final String ACCESS_TOKEN;
    private static final int GROUP_ID;
    private static VkMessenger vkMessenger;

    static {
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
            groupActor = init();
            vkMessenger = new VkMessenger(groupActor, vkApiClient);
            log.debug("Setting groups longpoll settings...");

            if (!vkApiClient.groups().getLongPollSettings(groupActor).execute().isEnabled()) {
                log.info("Setting the groups longpoll");
                vkApiClient.groups().setLongPollSettings(groupActor).enabled(true).messageNew(true).execute();
            }

            CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(vkApiClient, groupActor, this);
            handler.run();

        } catch (ClientException | ApiException e) {
            log.error("Could not start vk application",e);
        }

    }

    static VkApiClient getVkApiClient(){ return vkApiClient;}
    public static VkMessenger getVkMessenger(){
        return vkMessenger;
    }
    private GroupActor init()  {
        vkApiClient = new VkApiClient(this.transportClient);

        return new GroupActor(GROUP_ID, ACCESS_TOKEN);
    }
}
