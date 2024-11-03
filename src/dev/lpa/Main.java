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
      Artist artist = entityManager.find(Artist.class, 199);  // 203 is ID
//      Artist artist = new Artist(202, "Muddy Water");
//      artist.removeDuplicates();
      artist.addAlbum("The Best of Muddy Waters");
      System.out.println(artist);
//      artist.setArtistName("Muddy Waters");
//      entityManager.remove(artist);
//      entityManager.persist(new Artist("Muddy Water"));
//      entityManager.merge(artist);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
}
