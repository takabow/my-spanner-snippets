package com.example;

import com.example.entities.Child;
import com.example.entities.MapChild;
import com.example.entities.Parent;
import com.example.entities.SampleObject;
import com.example.entities.SampleObject.Address;
import com.example.entities.SampleObject.Employee;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class SampleApplication {

  /**
   * Main method that runs a simple console application
   * that saves a {@link SampleObject} entity and {@link Parent} entities
   * then retrieves it to print to the console.
   */
  public static void main(String[] args) {

    // Create Hibernate environment objects.
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .configure()
        .build();
    SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata()
        .buildSessionFactory();
    Session session = sessionFactory.openSession();

    try {
      saveSampleObject(session);
      saveParentChild(session);
    } finally {
      session.close();
      sessionFactory.close();
    }
  }


  /**
   * Saves a {@link SampleObject} entity into a Cloud Spanner table.
   */
  public static void saveSampleObject(Session session) {
    // BEGIN
    session.beginTransaction();

    // Employee and Address Object are stored as JSON column in the SmapleObject Table
    Employee employeeJson1 = new Employee();
    employeeJson1.setName("Taro");
    employeeJson1.setAddress(new Address("Roppongi 6-10-1", 1066126));

    // SampleObject entity is mapped to the SmapleObject Table
    SampleObject sample1 = new SampleObject(
        "Sample1", 1,
        Arrays.asList("foo", "bar"),
        employeeJson1);
    
    // INSERT
    session.save(sample1);

    // COMMIT
    session.getTransaction().commit();

    // SELECT
    List<SampleObject> samples =
        session.createQuery("from SampleObject", SampleObject.class).list();
    System.out.println(
        String.format("There are %d samples saved in the table:", samples.size()));
    for (SampleObject sampleInTable : samples) {
        System.out.println(sampleInTable);
    }
  }


  /**
   * Saves a {@link Parent} entity into a Spanner table.
   *
   * <p>Demonstrates saving entities using {@link com.google.cloud.spanner.hibernate.Interleaved}.
   */
  public static void saveParentChild(Session session) {
    session.beginTransaction();

    Parent parent1 = new Parent(
        "Parent1", 1,
        new ArrayList<>(), new HashMap<>(),
        Arrays.asList("hoge", "fuga"));
    Child child11 = new Child(parent1, "Child1-1");
    Child child12 = new Child(parent1, "Child1-2");
    MapChild mapChild11 = new MapChild(parent1, "MapChild1-1");
    parent1.addChild(child11);
    parent1.addChild(child12);
    parent1.addMapChild("key1", mapChild11);

    Parent parent2 = new Parent(
        "Parent2", 2,
        new ArrayList<>(), new HashMap<>(),
        Arrays.asList("foo", "bar"));
    Child child21 = new Child(parent2, "Child2-1");
    Child child22 = new Child(parent2, "Child2-2");
    Child child23 = new Child(parent2, "Child2-2");
    MapChild mapChild21 = new MapChild(parent2, "MapChild2-1");
    MapChild mapChild22 = new MapChild(parent2, "MapChild2-2");
    parent2.addChild(child21);
    parent2.addChild(child22);
    parent2.addChild(child23);
    parent2.addMapChild("key21", mapChild21);
    parent2.addMapChild("key22", mapChild22);

    session.save(parent1);
    session.save(child11);
    session.save(child12);
    session.save(mapChild11);
    session.save(parent2);
    session.save(child21);
    session.save(child22);
    session.save(child23);
    session.save(mapChild21);
    session.save(mapChild22);

    session.getTransaction().commit();

    List<Parent> parents =
        session.createQuery("from Parent", Parent.class).list();
    System.out.println(
        String.format("There are %d parents saved in the table:", parents.size()));

    for (Parent parentInTable : parents) {
      System.out.println(parentInTable);
    }
  }
}
