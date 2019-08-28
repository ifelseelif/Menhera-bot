package app;

import app.handler.AppHandler;
import model.Message;
import org.apache.log4j.Logger;

/**
 * @author Arthur Kupriyanov
 */
public class TelegramApp extends SocialApp {
    private final Logger log = Logger.getLogger(TelegramApp.class);

    public TelegramApp(AppHandler handler) {
        super(handler);
    }

    public void launch() {
        appHandler.receive(new Message("hello", null, null, null));
        log.info("Telegram launched in Thread : " + Thread.currentThread().getName());
    }
}
