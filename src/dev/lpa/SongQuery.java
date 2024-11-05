package dev.lpa;

import dev.lpa.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class SongQuery {
  
  public static void main(String[] args) {
  
    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(
      "dev.lpa.music");
         EntityManager em = emf.createEntityManager()) {
      
      var transaction = em.getTransaction();
      transaction.begin();
      
      Artist artist = em.find(Artist.class, 199);
      System.out.println(artist);
      System.out.println("-------------------------");
      
//      List<Artist> artistList = queryMusicData(em, "%Storm%");
      List<Tuple> artistList = queryMusicTuple(em, "%Storm%");
      artistList.forEach(System.out::println);
      System.out.println("size: " + artistList.size());
      
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static List<Artist> queryMusicData(EntityManager em, String matchSongName) {
    
    String jpql = "SELECT a FROM Artist a JOIN albums album JOIN songs song " +
                    "WHERE songTitle LIKE ?1";
    var query = em.createQuery(jpql, Artist.class);
    query.setParameter(1, matchSongName);
    return query.getResultList();
  }
  
  private static List<Tuple> queryMusicTuple(EntityManager em, String matchSongName) {
    
    String jpql = "SELECT a.artistName, album.albumName, s.songTitle FROM Artist a " +
                    "JOIN albums album JOIN songs s WHERE s.songTitle LIKE ?1";
    var query = em.createQuery(jpql, Tuple.class);
    query.setParameter(1, matchSongName);
    return query.getResultList();
  }
  
  private static List<Tuple> criteriaBuilderMusicTuple(EntityManager em, String matchSongName) {
    
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
    Root<Artist> rootArtist = criteriaQuery.from(Artist.class);
    var joinAlbum = rootArtist.join("albums");
    var joinSong = joinAlbum.join("songs");
    criteriaQuery
      .multiselect(
        rootArtist.get("artistName"),
        joinAlbum.get("albumName"),
        joinSong.get("songTitle"))
      .where(builder.like(joinSong.get("songTitle"), matchSongName))
      .orderBy(builder.asc(rootArtist.get("artistName")));
    return em.createQuery(criteriaQuery).getResultList();
  }
}
