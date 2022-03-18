package com.example.entities;

import com.example.entities.MapChild.MapChildId;
import com.google.cloud.spanner.hibernate.Interleaved;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

@Entity
@Interleaved(parentEntity = Parent.class, cascadeDelete = true)
@IdClass(MapChildId.class)
public class MapChild {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  private UUID mapChildId;

  @Id
  @ManyToOne
  @JoinColumn(name = "parentId")
  @Type(type = "uuid-char")
  private Parent parent;

  private String stringField;

  public MapChild(Parent parent, String stringField) {
    this.parent = parent;
    this.stringField = stringField;
  }

  public MapChild() {
  }

  public String getStringField() {
    return stringField;
  }

  public void setStringField(String stringField) {
    this.stringField = stringField;
  }

  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    return "MapChild{"
        + "mapChildId=" + mapChildId
        + ", parent=" + parent.getStringField()
        + ", stringField='" + stringField + '\''
        + '}';
  }

  /**
   * The embedded key for an entity using the {@link Interleaved} annotation.
   */
  public static class MapChildId implements Serializable {

    // The embedded key of an interleaved table contain all of the Primary Key fields
    // of the parent interleaved entity and be named identically. In this case: parentId.
    Parent parent;

    @Type(type = "uuid-char")
    UUID mapChildId;

    public MapChildId(Parent parent, UUID mapChildId) {
      this.parent = parent;
      this.mapChildId = mapChildId;
    }

    public MapChildId() {
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      MapChildId childId1 = (MapChildId) o;
      return Objects.equals(parent, childId1.parent)
          && Objects.equals(mapChildId, childId1.mapChildId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(parent, mapChildId);
    }
  }
}
