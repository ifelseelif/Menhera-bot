package app.handler.impl;

import app.service.CommandManager;
import app.handler.AppHandler;
import model.Message;

/**
 * @author Arthur Kupriyanov
 */
public class VkAppHandler implements AppHandler {
    @Override
    public void receive(Message message) {
        System.out.println(CommandManager.getCommand(message.getMessage()).execute(message));
    }
}
