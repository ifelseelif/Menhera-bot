package app;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;


/**
 * @author Arthur Kupriyanov
 */
@AllArgsConstructor
public class AppUncaughtErrorHandler implements Thread.UncaughtExceptionHandler {
    private final Class app;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getLogger(app);
        logger.error(e.getMessage()==null?"No exception message found":e.getMessage());
        logger.error(e);
    }
}
