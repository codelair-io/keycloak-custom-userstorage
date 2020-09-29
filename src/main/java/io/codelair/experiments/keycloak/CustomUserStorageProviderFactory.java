package io.codelair.experiments.keycloak;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {

  private static final Logger logger = Logger.getLogger(CustomUserStorageProviderFactory.class.getName());

  public static final String PROVIDER_NAME = "custom-user-storage-provider";

  protected Properties properties = new Properties();

  @Override
  public String getId() {
    return PROVIDER_NAME;
  }

  @Override
  public void init(Config.Scope config) {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("/user_groups.properties")) {
      properties.load(is);
    } catch (IOException ex) {
      logger.warning("Failed to load user_groups.properties file: " + ex.getMessage());
    }
  }

  @Override
  public CustomUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
    return new CustomUserStorageProvider(keycloakSession, componentModel, properties);
  }
}
