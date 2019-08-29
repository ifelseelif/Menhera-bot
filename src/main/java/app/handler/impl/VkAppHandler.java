package app.handler.impl;

import app.service.CommandManager;
import app.handler.AppHandler;
import app.vk.VkApp;
import com.vk.api.sdk.exceptions.ClientException;
import model.Message;
import org.apache.log4j.Logger;


/**
 * @author Arthur Kupriyanov
 */
public class VkAppHandler implements AppHandler {
    private final Logger logger = Logger.getLogger(VkAppHandler.class);
    @Override
    public void receive(Message message) {

        Message msg = CommandManager.getCommand(message.getMessage()).execute(message);
        try {
            VkApp.getVkMessenger().sendMessage(msg);
        } catch (ClientException e) {
            logger.error(e);
        }
    }
}
