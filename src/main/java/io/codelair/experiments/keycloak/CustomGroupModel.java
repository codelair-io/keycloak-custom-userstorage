package io.codelair.experiments.keycloak;

import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleMapperModel;
import org.keycloak.models.RoleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CustomGroupModel implements GroupModel {

  private final String id;
  private String name;

  public CustomGroupModel(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }

  // GroupModel methods

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setSingleAttribute(String s, String s1) {

  }

  @Override
  public void setAttribute(String s, List<String> list) {

  }

  @Override
  public void removeAttribute(String s) {

  }

  @Override
  public String getFirstAttribute(String s) {
    return null;
  }

  @Override
  public List<String> getAttribute(String s) {
    return null;
  }

  @Override
  public Map<String, List<String>> getAttributes() {
    return null;
  }

  @Override
  public GroupModel getParent() {
    return null;
  }

  @Override
  public String getParentId() {
    return null;
  }

  @Override
  public Set<GroupModel> getSubGroups() {
    return null;
  }

  @Override
  public void setParent(GroupModel groupModel) {

  }

  @Override
  public void addChild(GroupModel groupModel) {

  }

  @Override
  public void removeChild(GroupModel groupModel) {

  }

  // RoleMapperModel methods

  @Override
  public Set<RoleModel> getRealmRoleMappings() {
    return null;
  }

  @Override
  public Set<RoleModel> getClientRoleMappings(ClientModel clientModel) {
    return null;
  }

  @Override
  public boolean hasRole(RoleModel roleModel) {
    return false;
  }

  @Override
  public void grantRole(RoleModel roleModel) {

  }

  @Override
  public Set<RoleModel> getRoleMappings() {
    return null;
  }

  @Override
  public void deleteRoleMapping(RoleModel roleModel) {

  }
}
