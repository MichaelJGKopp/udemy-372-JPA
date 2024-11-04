package dev.lpa;

import dev.lpa.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.stream.Stream;

public class MainQuery {
  
  public static void main(String[] args) {
    
    List<Artist> artists = null;
    
    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(
      "dev.lpa.music");
         EntityManager em = emf.createEntityManager()) {
      var transaction = em.getTransaction();
      transaction.begin();
      artists = getArtistKPQL(em, "%Greatest Hits%");
      artists.forEach(System.out::println);
      
//      var names = getArtistNames(em, "%Stev%");
//      names
//        .map(a -> new Artist(
//          a.get("id", Integer.class),
//          (String) a.get("name")))
//          .forEach(System.out::println);
      transaction.commit();
    } catch (Exception e) { // temporary
      e.printStackTrace();
    }
  }
  
  private static List<Artist> getArtistKPQL(EntityManager em, String matchedValue) {
    
//    String jpql = "SELECT a FROM Artist a";
//    String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";  // random param. name
    String jpql = "SELECT a FROM Artist a JOIN albums album " +
                    "WHERE album.albumName LIKE ?1 OR album.albumName LIKE ?2";
    var query = em.createQuery(jpql, Artist.class);
//    query.setParameter("partialName", matchedValue);
    query.setParameter(1, matchedValue);
    query.setParameter(2, "%Best of%"); // hardcoded for simplicity
    return query.getResultList();
  }
  
  private static Stream<Tuple> getArtistNames(EntityManager em, String matchedValue) {

    String jpql = "SELECT a.artistId id, a.artistName as name FROM Artist a " +
                    "WHERE a.artistName LIKE ?1";  // random param. name
    var query = em.createQuery(jpql, Tuple.class);
    query.setParameter(1, matchedValue);
    return query.getResultStream();
  }
  
}
