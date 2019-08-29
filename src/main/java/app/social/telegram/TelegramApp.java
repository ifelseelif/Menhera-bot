package app.social.telegram;

import app.handler.AppHandler;
import app.handler.impl.TelegramAppHandler;
import app.social.SocialApp;
import app.social.telegram.bot.BasicTelegramBot;
import model.Message;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Arthur Kupriyanov
 */
public class TelegramApp extends SocialApp {
    static {
        System.getProperties().put("proxySet", true);
        System.getProperties().put("socksProxyHost", "127.0.0.1");
        System.getProperties().put("socksProxyPort", "9150");

        ApiContextInitializer.init();
    }
    private final Logger log = Logger.getLogger(TelegramApp.class);

    public TelegramApp(AppHandler handler) {
        super(handler);
    }

    public void launch() {

        TelegramBotsApi botsApi = new TelegramBotsApi();
        BasicTelegramBot basicTelegramBot = new BasicTelegramBot();
        basicTelegramBot.setHandler((TelegramAppHandler) appHandler);

        try {
            botsApi.registerBot(basicTelegramBot);
        } catch (TelegramApiRequestException e) {
            log.error(e);
            e.printStackTrace();
        }

        log.info("Telegram launched in Thread : " + Thread.currentThread().getName());
    }
}
