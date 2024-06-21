package io.sandeep.framework.core.driver.remote;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.sandeep.framework.core.exception.NoSuchServerRoleException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.grid.selenium.GridLauncherV3;

import java.io.IOException;
import java.net.ServerSocket;

import static io.github.bonigarcia.wdm.config.DriverManagerType.SELENIUM_SERVER_STANDALONE;
import static java.lang.String.format;

@Slf4j
public class Server {
    private final int server_port;
    private final String server_address;
    private final Role role;

    public Server (String server, int port, Role role) {
        server_port = port;
        server_address = server;
        if (role.equals(Role.HUB) || role.equals(Role.NODE)) this.role = role;
        else
            throw new NoSuchServerRoleException(format("Selenium stand alone server cannot be initialized with the supplied role of %s", role));
    }

    public int start () {
        WebDriverManager.getInstance(SELENIUM_SERVER_STANDALONE).setup();
        String[] hub = {"-port", Integer.toString(server_port),
                "-host", server_address,
                "-role", role.toString()};
        GridLauncherV3.main(hub);
        return server_port;
    }

    public static int get_free_port () throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    public static enum Role {
        HUB, NODE;

        public String toString () {
            return this.name().toLowerCase();
        }
    }
}
