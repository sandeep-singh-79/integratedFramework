package io.sandeep.framework.core.config;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

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
        new PropertyFileReader(new java.io.File(System.getProperty("user.dir") + "frameworkConfig.properties"));
    }
}