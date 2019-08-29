import app.AppUncaughtErrorHandler;
import app.handler.impl.TelegramAppHandler;
import app.handler.impl.VkAppHandler;
import app.social.telegram.TelegramApp;
import app.social.vk.VkApp;


/**
 * @author Arthur Kupriyanov
 */
public class Main {

    public static void main(String[] args) throws Exception{
        Thread vkAppThread = new Thread(() -> new VkApp(new VkAppHandler()).launch());
        Thread telegramAppThread = new Thread(() -> new TelegramApp(new TelegramAppHandler()).launch());

        vkAppThread.setName("VK APP THREAD");
        vkAppThread.setUncaughtExceptionHandler(new AppUncaughtErrorHandler(VkApp.class));

        telegramAppThread.setName("TELEGRAM APP THREAD");
        telegramAppThread.setUncaughtExceptionHandler(new AppUncaughtErrorHandler(TelegramApp.class));

        vkAppThread.start();
        telegramAppThread.start();
    }
}
