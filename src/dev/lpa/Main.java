package dev.lpa;

import dev.lpa.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
  
  public static void main(String[] args) {
  
    try (var sessionFactory = Persistence.createEntityManagerFactory(
      "dev.lpa.music");
         EntityManager entityManager = sessionFactory.createEntityManager();
    ) {
    
      var transaction = entityManager.getTransaction();
      transaction.begin();
      entityManager.persist(new Artist("Muddy Water"));
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  
  }
}
