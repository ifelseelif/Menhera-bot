package app.handler.impl;

import app.commands.CommandManager;
import app.handler.AppHandler;
import model.Message;

/**
 * @author Arthur Kupriyanov
 */
public class VkAppHandler implements AppHandler {
    @Override
    public void receive(Message message) {
        System.out.println(CommandManager.getCommamd(message.getMessage()).execute(message));
    }
}
