package dev.lpa.music;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "song_id")
  private int songId;
  
  @Column(name = "track_number")
  private int trackNumber;
  
  @Column(name = "song_title")
  private String songTitle;
  
  public Song() {
  }
  
  public int getSongId() {
    return songId;
  }
  
  public int getTrackNumber() {
    return trackNumber;
  }
  
  public String getSongTitle() {
    return songTitle;
  }
}
