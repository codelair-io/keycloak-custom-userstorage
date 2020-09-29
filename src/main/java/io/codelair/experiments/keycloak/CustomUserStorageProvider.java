package io.codelair.experiments.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

import java.util.*;

public class CustomUserStorageProvider implements
    UserStorageProvider,
    UserLookupProvider,
    CredentialInputValidator,
    CredentialInputUpdater {

  protected KeycloakSession session;
  protected Properties properties;
  protected ComponentModel model;

  protected Map<String, UserModel> loadedUsers = new HashMap<>();

  public CustomUserStorageProvider(KeycloakSession session, ComponentModel model, Properties properties) {
    this.session = session;
    this.model = model;
    this.properties = properties;
  }

  // UserLookupProvider methods

  @Override
  public UserModel getUserByUsername(String username, RealmModel realm) {
    UserModel userAdapter = loadedUsers.get(username);
    if (userAdapter == null) {
      String groupName = properties.getProperty(username);
      if (groupName != null) {
        Set<GroupModel> groups = new HashSet<>();
        groups.add(new CustomGroupModel(groupName));
        userAdapter = createAdapter(realm, username, groups);
        loadedUsers.put(username, userAdapter);
      }
    }
    return userAdapter;
  }

  @Override
  public UserModel getUserById(String id, RealmModel realm) {
    // the result of the below is that a username can be used as the id argument in this method
    StorageId storageId = new StorageId(id);
    String username = storageId.getExternalId();
    return getUserByUsername(username, realm);
  }

  @Override
  public UserModel getUserByEmail(String s, RealmModel realmModel) {
    return null; // not implemented
  }

  // internal util methods for UserLookupProvider methods

  protected UserModel createAdapter(RealmModel realm, String username, Set<GroupModel> groups) {
    return new AbstractUserAdapter(session, realm, model) {
      @Override
      public String getUsername() {
        return username;
      }

      @Override
      public Set<GroupModel> getGroups(int first, int max) {
        return groups;
      }

      @Override
      public Set<GroupModel> getGroups(String search, int first, int max) {
        return groups;
      }

      @Override
      public long getGroupsCount() {
        return groups.size();
      }

      @Override
      public long getGroupsCountByNameContaining(String search) {
        return groups.stream().filter(group -> group.getName().contains(search)).count();
      }
    };
  }

  // CredentialInputValidator methods

  @Override
  public boolean supportsCredentialType(String s) {
    return false;
  }

  @Override
  public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String s) {
    return false;
  }

  @Override
  public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
    return true;  // return true. we don't do any validation here
  }

  // CredentialInputUpdater methods

  @Override
  public boolean updateCredential(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
    return true; // N/A in this case so accept and do nothing
  }

  @Override
  public void disableCredentialType(RealmModel realmModel, UserModel userModel, String s) {
    // N/A in this case so accept and do nothing
  }

  @Override
  public Set<String> getDisableableCredentialTypes(RealmModel realmModel, UserModel userModel) {
    return null; // N/A in this case so return null
  }

  @Override
  public void close() {

  }

}
