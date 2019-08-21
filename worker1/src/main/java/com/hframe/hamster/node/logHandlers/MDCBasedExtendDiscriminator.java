package com.hframe.hamster.node.logHandlers;

import ch.qos.logback.classic.sift.MDCBasedDiscriminator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.util.OptionHelper;
import scala.reflect.io.File;

import java.util.Map;

public class MDCBasedExtendDiscriminator extends MDCBasedDiscriminator{
    private String key;
    private String defaultValue;
    private String key2;
    private String defaultValue2;


    /**
     * Return the value associated with an MDC entry designated by the Key
     * property. If that value is null, then return the value assigned to the
     * DefaultValue property.
     */
    public String getDiscriminatingValue(ILoggingEvent event) {
        // http://jira.qos.ch/browse/LBCLASSIC-213
        Map<String, String> mdcMap = event.getMDCPropertyMap();
        String value1 = mdcMap == null || mdcMap.get(key) == null ? defaultValue : mdcMap.get(key);
        String value2 = mdcMap == null || mdcMap.get(key2) == null ? defaultValue2 : mdcMap.get(key2);

        return value1 + File.pathSeparator() + value2;
    }

    @Override
    public void start() {
        int errors = 0;
        if (OptionHelper.isEmpty(key)) {
            errors++;
            addError("The \"Key\" property must be set");
        }
        if (OptionHelper.isEmpty(defaultValue)) {
            errors++;
            addError("The \"DefaultValue\" property must be set");
        }
        if (OptionHelper.isEmpty(key2)) {
            errors++;
            addError("The \"Key2\" property must be set");
        }
        if (OptionHelper.isEmpty(defaultValue2)) {
            errors++;
            addError("The \"DefaultValue2\" property must be set");
        }
        if (errors == 0) {
            started = true;
        }
    }

    public String getKey() {
        return key + "|" + key2;
    }

    public void setKey(String key) {
        this.key = key.split("|")[0].trim();
        this.key2 = key.split("|")[1].trim();
    }

    /**
     * @return
     * @see #setDefaultValue(String)
     */
    public String getDefaultValue() {
        return defaultValue + "|" + defaultValue2;
    }

    /**
     * The default MDC value in case the MDC is not set for
     * {@link #setKey(String) mdcKey}.
     * <p/>
     * <p> For example, if {@link #setKey(String) Key} is set to the value
     * "someKey", and the MDC is not set for "someKey", then this appender will
     * use the default value, which you can set with the help of this method.
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue.split("|")[0].trim();
        this.defaultValue2 = defaultValue.split("|")[1].trim();
    }
}
