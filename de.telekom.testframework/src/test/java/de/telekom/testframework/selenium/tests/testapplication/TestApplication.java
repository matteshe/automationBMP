package de.telekom.testframework.selenium.tests.testapplication;

import com.google.inject.Inject;
import de.telekom.testframework.selenium.Application;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Daniel
 */
public class TestApplication extends Application {

    public static final Server server = new Server(12345);

    static {
        if (!server.isRunning()) {
            try {
                ResourceHandler resourceHandler = new ResourceHandler();

                String rb = TestApplication.class.getResource("pages").toString();
                System.out.println(rb);
                        
                resourceHandler.setResourceBase(rb);
                resourceHandler.setDirectoriesListed(true);
                resourceHandler.setWelcomeFiles(new String[]{"index.html"});

                HandlerList handlers = new HandlerList();
                handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()});

                server.setHandler(resourceHandler);

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
    public TestApplication(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getURLString() {
        return "http://localhost:12345";
    }

}
