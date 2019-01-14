package io.sandeep.framework.core.config;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Properties;

@Slf4j
public class FrameworkConfig implements Cloneable, Serializable {
    private static volatile FrameworkConfig instance = new FrameworkConfig();
    private Properties frameworkProperties;

    private FrameworkConfig () {
        try {
            frameworkProperties = new PropertyFileReader(new File(String.format("%s/src/main/resources/frameworkConfig.properties",
                    System.getProperty("user.dir")))).getPropertyFile();
        } catch (FileNotFoundException e) {
            log.error("Unable to locate the file as the path was not properly configured!!");
            log.error(e.getStackTrace().toString());
        }
    }

    public static FrameworkConfig getInstance () {
        return instance;
    }

    public Properties getConfigProperties () {
        return frameworkProperties;
    }

    public Object readResolve () {
        return instance;
    }

    @Override
    public Object clone () throws CloneNotSupportedException {
        log.info("Not allowed to clone the current class");
        log.info("throwing CloneNotSupportedException for your pains ...");
        throw new CloneNotSupportedException(String.format("Cloning not allowed for %s class", this.getClass()));
    }
}
