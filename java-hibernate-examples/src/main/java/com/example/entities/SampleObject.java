package com.example.entities;

import com.google.cloud.spanner.hibernate.types.SpannerArrayListType;
import com.google.cloud.spanner.hibernate.types.SpannerJsonType;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * Hibernate entity demonstrating SmapleObject entity.
 */
@TypeDefs({
    @TypeDef(
      name = "spanner-array",
      typeClass = SpannerArrayListType.class
    ),
    @TypeDef(
      name = "spanner-json",
      typeClass = SpannerJsonType.class
    )
})
@Entity
public class SampleObject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  private UUID sampleObjectId;

  private String stringField;

  private int intField;

  @Type(type = "spanner-array")
  private List<String> stringArrayField;

  @Type(type = "spanner-json")
  private Employee jsonField;

  /**
   * SmapleObject entity constructor.
   */
  public SampleObject(
      String stringField, int intField,
      List<String> stringArrayField,
      Employee jsonField) {
    this.stringField = stringField;
    this.intField = intField;
    this.stringArrayField = stringArrayField;
    this.jsonField = jsonField;
  }

  // Default constructor used by JPA
  public SampleObject() {

  }

  public UUID getSampleObjectId() {
    return this.sampleObjectId;
  }

  public void setSampleObjectId(UUID sampleObjectId) {
    this.sampleObjectId = sampleObjectId;
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

  public List<String> getStringArrayField() {
    return stringArrayField;
  }

  public void setStringArrayField(List<String> stringArrayField) {
    this.stringArrayField = stringArrayField;
  }

  public Employee getJsonField() {
    return jsonField;
  }

  public void setJsonField(Employee jsonField) {
    this.jsonField = jsonField;
  }

  @Override
  public String toString() {
    return "SampleObject{"
        + "sampleObjectId=" + sampleObjectId
        + ", stringField='" + stringField + '\''
        + ", intField='" + intField + '\''
        + ", stringArrayField=" + stringArrayField
        + ", jsonField=" + jsonField
        + '}';
  }

  public static class Employee {
    public String name;
    public Address address;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Address getAddress() {
      return address;
    }

    public void setAddress(Address address) {
      this.address = address;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Employee)) {
        return false;
      }
      Employee employee = (Employee) o;
      return Objects.equals(name, employee.name) && Objects
          .equals(address, employee.address);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, address);
    }
  }

  public static class Address {
    public String address;
    public int zipCode;

    public Address(String address, int zipCode) {
      this.address = address;
      this.zipCode = zipCode;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public int getZipCode() {
      return zipCode;
    }

    public void setZipCode(int zipCode) {
      this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Address)) {
        return false;
      }
      Address address1 = (Address) o;
      return zipCode == address1.zipCode && Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
      return Objects.hash(address, zipCode);
    }
  }
}
