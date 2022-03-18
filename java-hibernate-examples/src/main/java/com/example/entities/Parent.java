package com.example.entities;

import com.google.cloud.spanner.hibernate.types.SpannerArrayListType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * Hibernate entity demonstrating a one-to-many relationship with {@link Child} entity.
 */
@TypeDefs({
    @TypeDef(
      name = "spanner-array",
      typeClass = SpannerArrayListType.class
    )
})
@Entity
public class Parent {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  private UUID parentId;

  private String stringField;

  private int intField;

  @OneToMany(mappedBy = "parent")
  List<Child> children;

  @OneToMany(mappedBy = "parent")
  @MapKeyColumn(name = "map_key")
  Map<String, MapChild> mapChildren;

  @Type(type = "spanner-array")
  private List<String> stringArrayField;

  public Parent(
      String stringField, int intField,
      List<Child> children, Map<String, MapChild> mapChildren,
      List<String> stringArrayField) {
    this.stringField = stringField;
    this.intField = intField;
    this.stringArrayField = stringArrayField;
    this.children = children;
    this.mapChildren = mapChildren;
  }

  public Parent() {
  }

  public UUID getParentId() {
    return this.parentId;
  }

  public void setParentId(UUID parentId) {
    this.parentId = parentId;
  }

  public String getStringField() {
    return stringField;
  }

  public void setStringField(String stringField) {
    this.stringField = stringField;
  }

  public int getIntField() {
    return intField;
  }

  public void setIntField(int intField) {
    this.intField = intField;
  }

  public void addChild(Child child) {
    this.children.add(child);
  }

  public List<Child> getChildren() {
    return children;
  }

  public void setChildren(List<Child> children) {
    this.children = children;
  }

  public void addMapChild(String key, MapChild child) {
    this.mapChildren.put(key, child);
  }

  public Map<String, MapChild> getMapChildren() {
    return mapChildren;
  }

  public void setMapChildren(Map<String, MapChild> mapChildren) {
    this.mapChildren = mapChildren;
  }

  public List<String> getStringArrayField() {
    return stringArrayField;
  }

  public void setStringArrayField(List<String> stringArrayField) {
    this.stringArrayField = stringArrayField;
  }

  @Override
  public String toString() {
    return "Parent{"
        + "parentId=" + parentId
        + ", stringField='" + stringField + '\''
        + ", intField='" + intField + '\''
        + ", Child=" + children
        + ", stringArrayField=" + stringArrayField
        + '}';
  }
}
