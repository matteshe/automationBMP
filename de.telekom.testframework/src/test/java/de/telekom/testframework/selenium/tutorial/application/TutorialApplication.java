package de.telekom.testframework.selenium.tutorial.application;

import com.google.inject.Inject;
import de.telekom.testframework.selenium.Application;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Daniel
 */
public class TutorialApplication extends Application {

    public static final Server server = new Server(12346);

    static {
        if (!server.isRunning()) {
            try {
                ResourceHandler resourceHandler = new ResourceHandler();

                String rb = TutorialApplication.class.getResource("pages").toString();

                resourceHandler.setResourceBase(rb);
                resourceHandler.setDirectoriesListed(true);
                resourceHandler.setWelcomeFiles(new String[]{"index.html"});

//                RequestLogHandler requestLogHandler = new RequestLogHandler();                
//                requestLogHandler.setRequestLog(new NCSARequestLog("TutorialApplication.log"));
                HandlerList handlers = new HandlerList();
                handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()/* , requestLogHandler */});

                server.setHandler(handlers);

                server.start();

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                if (server.isRunning()) {
                    try {
                        server.stop();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });
    }

    @Inject
    public TutorialApplication(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getURLString() {
        return "http://localhost:12346";
    }

}
