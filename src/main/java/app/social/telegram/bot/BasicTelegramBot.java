package app.social.telegram.bot;

import app.handler.AppHandler;
import app.handler.impl.TelegramAppHandler;
import lombok.Getter;
import lombok.Setter;
import model.Message;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Arthur Kupriyanov
 */
@Getter
@Setter
public class BasicTelegramBot extends TelegramLongPollingBot {
    private TelegramAppHandler handler;
    private Logger logger = Logger.getLogger(BasicTelegramBot.class);
    private String botUsername;
    private String botToken;

    {
        Properties properties = new Properties();
        botUsername = System.getenv("TELEGRAM_BOT_USERNAME");
        botToken = System.getenv("TELEGRAM_BOT_TOKEN");
        if( botUsername == null || botToken == null) {
            try {
                properties.load(BasicTelegramBot.class.getClassLoader().getResourceAsStream("telegram-app.properties"));
            } catch (IOException e) {
                logger.error("Failed to load bot properties", e);
            }

            botUsername = properties.getProperty("bot_name");
            botToken = properties.getProperty("bot_token");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handler.receive(Message.getInstance(update.getMessage()));
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setHandler(TelegramAppHandler handler){
        handler.setTelegramLongPollingBot(this);
        this.handler = handler;
    }
}
