package io.sandeep.framework.core.driver.remote;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class Server {
    private final int server_port;

    public Server () throws IOException {
        this(get_free_port());
    }

    public Server (int port) {
        server_port = port;
    }

    public int start () {
        //WebDriverManager.getInstance(DriverManagerType.SELENIUM_SERVER_STANDALONE).setup();
        /*
         *TODO: use GridLauncherV3 to launch the server instead of using WebDriverManager main
         * as this launches the WDM server and not Remote Server
         */
        WebDriverManager.main(new String[]{"server", Integer.toString(server_port)});

        return server_port;
    }

    public static int get_free_port () throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
}
