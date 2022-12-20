package com.github.fabriciolfj.practicedynamodb.operations;

import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Music;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class GravarItem {

    public static void main(String[] args) {
        try {
            var client = ConfigureDynamoDb.getDynamodbEnhancedClient();
            var table = client.table("Music", TableSchema.fromBean(Music.class));
            var music = new Music("Beto", "Da boa", "nao sei");

            table.putItem(music);
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }

    }
}
