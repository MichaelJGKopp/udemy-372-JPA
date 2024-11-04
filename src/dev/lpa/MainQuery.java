package dev.lpa;

import dev.lpa.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class MainQuery {
  
  public static void main(String[] args) {
    
    List<Artist> artists = null;
    
    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(
      "dev.lpa.music");
         EntityManager em = emf.createEntityManager()) {
      var transaction = em.getTransaction();
      transaction.begin();
      artists = getArtistKPQL(em, "%Stev%");
      artists.forEach(System.out::println);
      
      var names = getArtistNames(em, "%Stev%");
      names.forEach(System.out::println);
      transaction.commit();
    } catch (Exception e) { // temporary
      e.printStackTrace();
    }
  }
  
  private static List<Artist> getArtistKPQL(EntityManager em, String matchedValue) {
    
//    String jpql = "SELECT a FROM Artist a";
    String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";  // random param. name
    var query = em.createQuery(jpql, Artist.class);
//    query.setParameter("partialName", matchedValue);
    query.setParameter(1, matchedValue);
    return query.getResultList();
  }
  
  private static List<String> getArtistNames(EntityManager em, String matchedValue) {

    String jpql = "SELECT a.artistName FROM Artist a WHERE a.artistName LIKE ?1";  // random param. name
    var query = em.createQuery(jpql, String.class);
    query.setParameter(1, matchedValue);
    return query.getResultList();
  }
  
}
