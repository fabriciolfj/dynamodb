package com.github.fabriciolfj.practicedynamodb.operations;

import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Music;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class AtualizarItem {

    public static void main(String[] args) {
        var enhance = ConfigureDynamoDb.getDynamodbEnhancedClient();

        try {
            var table = enhance.table("Music", TableSchema.fromBean(Music.class));
            final Key key = Key.builder().partitionValue(AttributeValue.builder().s("Beto").build()).sortValue("Da boa").build();
            var music = table.getItem(r -> r.key(key));

            music.setAlbumTitle("tudo ruim");
            table.updateItem(music);
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }
}
