package com.github.fabriciolfj.practicedynamodb.operations;

import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;

import java.util.HashMap;

public class ExcluirItem {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getClient();
        HashMap<String, AttributeValue> keyGet = new HashMap<>();
        keyGet.put("Artist", AttributeValue.builder().s("Beto").build());
        keyGet.put("SongTitle", AttributeValue.builder().s("Da boa").build());

        DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                .tableName("Music")
                .key(keyGet)
                .build();

        try {
            client.deleteItem(deleteReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
