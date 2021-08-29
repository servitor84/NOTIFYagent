package de.di.dokinform.util;

/**
 *
 * @author A. Sopicki
 */
public class Registry {

  private static Registry instance;

  private java.util.HashMap<String, Object> registry;

  private Registry() {
    registry = new java.util.HashMap<String,Object>();
  }

  public static synchronized Registry getInstance() {
    if ( instance == null ) {
      instance = new Registry();
    }

    return instance;
  }

  public void put(String key, Object value) {
    registry.put(key, value);
  }

  public Object get(String key) {
    return registry.get(key);
  }

  public void remove(String key) {
    registry.remove(key);
  }

  public void clear() {
    registry.clear();
  }
  
}
