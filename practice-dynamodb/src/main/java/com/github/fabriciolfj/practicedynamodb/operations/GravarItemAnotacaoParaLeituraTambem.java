package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Music;

public class GravarItemAnotacaoParaLeituraTambem {

    public static void main(String[] args) {
        var cient = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(cient);

        var music = new Music();
        music.setAlbumTitle("teste");
        music.setArtist("teste");
        music.setSongTitle("teste");

        mapper.save(music);

        var getMusic = new Music();
        getMusic.setArtist("teste");

        var expression = new DynamoDBQueryExpression<Music>()
                .withHashKeyValues(getMusic);

        var result = mapper.query(Music.class, expression);

        result.forEach(System.out::println);

    }
}
