package app.handler.impl;

import app.handler.AppHandler;
import app.service.CommandManager;
import lombok.Setter;
import model.Message;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Arthur Kupriyanov
 */
@Setter
public class TelegramAppHandler implements AppHandler {

    private final Logger logger = Logger.getLogger(TelegramAppHandler.class);
    private TelegramLongPollingBot telegramLongPollingBot;

    @Override
    public void receive(Message message) {
        Message msg = CommandManager.getCommand(message.getMessage()).execute(message);
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getFrom());
        sm.setText(msg.getMessage());

        try {
            telegramLongPollingBot.execute(sm);
        } catch (TelegramApiException e) {
            logger.error(e);
        }
    }
}
