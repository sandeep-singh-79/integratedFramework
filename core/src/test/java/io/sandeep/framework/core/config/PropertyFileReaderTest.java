package io.sandeep.framework.core.config;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import static org.testng.Assert.assertTrue;

@Slf4j
public class PropertyFileReaderTest {
    @BeforeMethod
    public void setUp () {
    }

    @AfterMethod
    public void tearDown () {
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testGetNullPropertyFile () throws FileNotFoundException {
        new PropertyFileReader(null);
    }

    @Test (expectedExceptions = FileNotFoundException.class)
    public void testPropertyFileExists () throws FileNotFoundException {
        new PropertyFileReader(new File(System.getProperty("user.dir") + "frameworkConfig.properties"));
    }

    @Test
    public void testPropertyFilereturnsProperties() throws FileNotFoundException {
        String filePath = String.format("%s/../testng/src/main/resources/frameworkConfig.properties", System.getProperty("user.dir"));
        Properties props = new PropertyFileReader(new File(filePath)).getPropertyFile();
        assertTrue(props instanceof Properties);
    }
}