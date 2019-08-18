package app.handler;

import model.Message;

/**
 * @author Arthur Kupriyanov
 */
public interface AppHandler {
    void receive(Message message);
}
