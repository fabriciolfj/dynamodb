package com.github.fabriciolfj.practicedynamodb.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
@DynamoDBTable(tableName="Music") //usado apenas para consulta
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
    @DynamoDBHashKey(attributeName="Artist")//usado apenas para consulta
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "SongTitle")
    @DynamoDBRangeKey(attributeName="SongTitle")//usado apenas para consulta
    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "AlbumTitle-index")
    @DynamoDBAttribute(attributeName = "AlbumTitle")//usado apenas para consulta
    public String getAlbumTitle() {
        return albumTitle;
    }

    @DynamoDbAttribute(value = "AlbumTitle")
    @DynamoDBAttribute(attributeName = "Year")//usado apenas para consulta
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
}
