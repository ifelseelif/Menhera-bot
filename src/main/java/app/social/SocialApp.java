package app.social;

import app.App;
import app.handler.AppHandler;

/**
 * @author Arthur Kupriyanov
 */
public abstract class SocialApp implements App {
    public SocialApp(AppHandler handler){
        this.appHandler = handler;
    }
    protected AppHandler appHandler;

    public AppHandler getAppHandler() {
        return appHandler;
    }

    public void setAppHandler(AppHandler appHandler) {
        this.appHandler = appHandler;
    }

}
