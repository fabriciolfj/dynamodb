package com.github.fabriciolfj.practicedynamodb.operations;

import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Music;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

public class ConsultaTabela {

    public static void main(String[] args) {
        try {
            var client = ConfigureDynamoDb.getDynamodbEnhancedClient();
            var table = client.table("Music", TableSchema.fromBean(Music.class));
            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue("Beto").build());

            var results = table.query(queryConditional).items().iterator();

            while (results.hasNext()) {
                var music = results.next();
                System.out.println(music);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
