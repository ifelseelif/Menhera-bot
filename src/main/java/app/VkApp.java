package app;

import app.handler.AppHandler;
import model.Message;
import org.apache.log4j.Logger;

/**
 * @author Arthur Kupriyanov
 */
public class VkApp extends SocialApp {
    private final Logger log = Logger.getLogger(VkApp.class);

    public VkApp(AppHandler handler) {
        super(handler);
    }

    public void launch() {
        log.info("Vkontakte launched in Thread : " + Thread.currentThread().getName());
        appHandler.receive(new Message("hello", "", "", null));
        appHandler.receive(new Message("away", "", "", null));
    }
}
