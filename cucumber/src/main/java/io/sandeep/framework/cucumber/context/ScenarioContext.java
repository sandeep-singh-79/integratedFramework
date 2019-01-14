package io.sandeep.framework.cucumber.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ScenarioContext implements Cloneable, Serializable {
    private static volatile ScenarioContext context_instance;
    private final Map <String, Object> scenarioContext = new HashMap <>();

    private ScenarioContext () {
    }

    static ScenarioContext getInstance () {
        if (context_instance == null) {
            synchronized (ScenarioContext.class) {
                if (context_instance == null) {
                    context_instance = new ScenarioContext();
                }
            }
        }

        return context_instance;
    }

    public void setContext (String key, Object value) {
        scenarioContext.put(key, value);
    }

    public Object getContext (String key) {
        return scenarioContext.get(key);
    }

    public Boolean contains (String key) {
        return scenarioContext.containsKey(key);
    }

    protected Object readResolve () {
        return context_instance;
    }

    @Override
    protected Object clone () throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}