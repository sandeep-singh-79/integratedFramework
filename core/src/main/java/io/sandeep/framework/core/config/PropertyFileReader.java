package io.sandeep.framework.core.config;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertyFileReader {
    private Properties property;

    public PropertyFileReader (final File propertyFilePath) throws FileNotFoundException, NullPointerException {
        if(propertyFilePath == null) throw new NullPointerException("Property file path is null! Can not initialize!");
        if (propertyFilePath.exists() && propertyFilePath.isFile()) {
            try {
                property = new Properties();

                property.load(new FileInputStream(propertyFilePath));
            } catch (IOException e) {
                System.out.println("Unable to locate the file at the provided location " + propertyFilePath.getPath());
                e.printStackTrace();
            }
        } else {
            throw new FileNotFoundException("File Path supplied is either not valid or is not a file");
        }
    }

    public Properties getPropertyFile () {
        return property;
    }
}
