package org.failsafe.failsafe.common;

import lombok.SneakyThrows;

import java.util.Properties;

public class Property {
  private static Properties properties;
  private static final String PROPERTIES_FILE = "application.properties";

  private Property() {
    throw new RuntimeException("Properties object should not be instantiated!");
  }

  @SneakyThrows
  private static Properties get() {
    if (properties == null) {
      properties = new Properties();
      properties.load(Property.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
    }
    return properties;
  }

  public static String getProperty(String propertyName) {
    return get().getProperty(propertyName);
  }

}
