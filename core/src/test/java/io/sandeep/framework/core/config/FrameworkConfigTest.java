package io.sandeep.framework.core.config;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

import static org.testng.Assert.*;

public class FrameworkConfigTest {
    FrameworkConfig config;

    @BeforeMethod
    public void setUp () {
        config = FrameworkConfig.getInstance();
    }

    @Test
    public void testGetInstance () {
        assertTrue(config instanceof FrameworkConfig);
    }

    @Test
    public void testGetConfigProperties () {
        assertTrue(config.getConfigProperties() instanceof Properties);
    }

    @Test(expectedExceptions = CloneNotSupportedException.class)
    public void test_cannot_clone() throws CloneNotSupportedException {
        config.clone();
    }
}