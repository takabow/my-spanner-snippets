package com.example.entities;

import com.example.entities.Child.ChildId;
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
@IdClass(ChildId.class)
public class Child {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  private UUID childId;

  @Id
  @ManyToOne
  @JoinColumn(name = "parentId")
  @Type(type = "uuid-char")
  private Parent parent;

  private String stringField;

  public Child(Parent parent, String stringField) {
    this.parent = parent;
    this.stringField = stringField;
  }

  public Child() {
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
    return "Child{"
        + "childId=" + childId
        + ", parent=" + parent.getStringField()
        + ", stringField='" + stringField + '\''
        + '}';
  }

  /**
   * The embedded key for an entity using the {@link Interleaved} annotation.
   */
  public static class ChildId implements Serializable {

    // The embedded key of an interleaved table contain all of the Primary Key fields
    // of the parent interleaved entity and be named identically. In this case: parentId.
    Parent parent;

    @Type(type = "uuid-char")
    UUID childId;

    public ChildId(Parent parent, UUID childId) {
      this.parent = parent;
      this.childId = childId;
    }

    public ChildId() {
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ChildId childId1 = (ChildId) o;
      return Objects.equals(parent, childId1.parent)
          && Objects.equals(childId, childId1.childId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(parent, childId);
    }
  }
}
