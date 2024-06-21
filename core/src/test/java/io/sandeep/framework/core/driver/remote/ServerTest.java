package io.sandeep.framework.core.driver.remote;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class ServerTest {
    @Test
    public void testStart () throws IOException {
        int server_port = new Server("localhost", Server.get_free_port(), Server.Role.HUB).start();
        assertThat(server_port, greaterThan(0));
    }
}