package dev.lpa;

import dev.lpa.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.stream.Stream;

public class Main {
  
  public static void main(String[] args) {
    
    try (var sessionFactory = Persistence.createEntityManagerFactory(
      "dev.lpa.music");
         EntityManager entityManager = sessionFactory.createEntityManager();
    ) {
      
      var transaction = entityManager.getTransaction();
      transaction.begin();
      Artist artist = entityManager.find(Artist.class, 199);  // 203 is ID
      System.out.println(artist);
      artist.getAlbums().forEach(album -> {
        System.out.println(album.getAlbumName());
        album.getSongs().forEach(s -> System.out.printf("\t %-20s %-20s %n",
          s.getTrackNumber(), s.getSongTitle()));
      });
//      Artist artist = new Artist(202, "Muddy Water");
//      artist.removeDuplicates();
//      artist.addAlbum("The Best of Muddy Waters");
//      System.out.println(artist);
//      artist.setArtistName("Muddy Waters");
//      entityManager.remove(artist);
//      entityManager.persist(new Artist("Muddy Water"));
//      entityManager.merge(artist);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static Stream<Artist> getArtist(EntityManager em, int id) {
    
    String jpql = "SELECT a FROM Artist a WHERE a.artistId LIKE :myArtistId";
    var query = em.createQuery(jpql, Artist.class);
    query.setParameter("myArtistId", id);
    return query.getResultStream();
  }

//  private static Stream<Tuple> getArtistNames(EntityManager em, String matchedValue) {
//
//    String jpql = "SELECT a.artistId id, a.artistName as name FROM Artist a " +
//                    "WHERE a.artistName LIKE ?1";  // random param. name
//    var query = em.createQuery(jpql, Tuple.class);
//    query.setParameter(1, matchedValue);
//    return query.getResultStream();
//  }
}
