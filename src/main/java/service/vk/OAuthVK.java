package service.vk;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Arthur Kupriyanov
 */
@Getter
@Component
public class OAuthVK {

    private static String VK_AUTHORIZE_URI = "https://oauth.vk.com/authorize";
    private static String VK_ACCESS_URI = "https://oauth.vk.com/access_token";
    private static String clientId = System.getenv("VK_CLIENT_ID");
    private static String clientSecret = System.getenv("VK_CLIENT_SECRET");
    private static String redirectUri = System.getenv("VK_REDIRECT_URI");

    static {
        Logger logger = LoggerFactory.getLogger(OAuthVK.class);
        Properties properties = new Properties();
        try {
            properties.load(OAuthVK.class.getClassLoader().getResourceAsStream("vk-app.properties"));
        } catch (IOException e) {
            logger.error("Couldn't find local configs properties file");
        }

        if (clientId == null) {
            clientId = properties.getProperty("VK_CLIENT_ID");
            logger.debug("Client Id set upped from conf file");
        }
        else logger.debug("Client Id set upped from env");

        if (clientSecret == null){
            clientSecret = properties.getProperty("VK_CLIENT_SECRET");
            logger.debug("Client Secret set upped from conf file");
        }
        else logger.debug("Client Secret set upped from env");

        if (redirectUri == null){
            redirectUri = properties.getProperty("VK_REDIRECT_URI");
            logger.debug("Redirect URI set upped from conf file");
        }
        else logger.debug("Redirect URI set upped from env");
    }


    /*
        page — форма авторизации в отдельном окне;
        popup — всплывающее окно;
     */
    private static String display = "page";

    /*
        notify - 1
        Добавление ссылки на приложение в меню слева - 256
        offline - 65536
        email - 4194304
     */
    private static String scope = String.valueOf(1 + 256 + 65536 + 4194304);

    private static String responseType = "code";
    private static String version = "5.101";

    public static String getCodeTokenPath(String state) {
        return String.format("%s?client_id=%s" +
                        "&redirect_uri=%s" +
                        "&display=%s" +
                        "&scope=%s" +
                        "&response_type=%s" +
                        "&v=%s" +
                        "&state=%s",
                VK_AUTHORIZE_URI, clientId, redirectUri, display, scope, responseType, version, state);
    }

    public static String getAccessTokenPath(String code) {
        return String.format("%s?client_id=%s" +
                        "&client_secret=%s" +
                        "&redirect_uri=%s" +
                        "&code=%s",
                VK_ACCESS_URI, clientId, clientSecret, redirectUri, code);
    }
}