package com.github.fabriciolfj.practicedynamodb.dto;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class Music {

    private String artist;
    private String songTitle;
    private String albumTitle;

    public Music(String artist, String songTitle, String albumTitle) {
        this.artist = artist;
        this.songTitle = songTitle;
        this.albumTitle = albumTitle;
    }

    public Music() {

    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "Artist")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "SongTitle")
    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "AlbumTitle-index")
    public String getAlbumTitle() {
        return albumTitle;
    }

    @DynamoDbAttribute(value = "AlbumTitle")
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
}
