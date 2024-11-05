package dev.lpa.music;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "albums")
public class Album implements Comparable<Album> {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="album_id")
  private int albumId;
  
  @Column(name="album_name")
  private String albumName;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "album_id")
  private List<Song> songs = new ArrayList<>();
  
  public Album() {
  }
  
  public Album(String albumName) {
    this.albumName = albumName;
  }
  
  public Album(int albumId, String albumName) {    this.albumId = albumId;
    this.albumName = albumName;
  }
  
  public String getAlbumName() {
    return albumName;
  }
  
  public List<Song> getSongs() {
    return songs;
  }
  
  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }
  
  @Override
  public String toString() {
    
    List<Song> songsOrdered = new ArrayList<>(songs);
    songsOrdered.sort(Comparator.comparing(Song::getTrackNumber));
    
    return "Album{" +
             "albumId=" + albumId +
             ", albumName='" + albumName + '\'' + ", songs=\n" +
             songs.stream()
               .sorted(Comparator.comparing(Song::getTrackNumber))
               .map(Song::toString)
               .collect(Collectors.joining("\n\t", "\t", "\n}"));
  }
  
  @Override
  public int compareTo(Album o) {
    return this.getAlbumName().compareTo(o.getAlbumName());
  }
}
