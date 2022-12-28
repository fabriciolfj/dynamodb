package com.github.fabriciolfj.practicedynamodb.operations;

import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import java.util.HashMap;

public class LerItem {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getClient();
        var keyName = "Artist";
        var alias = "#nome";
        var value = "Beto";

        HashMap<String, String> nameAlias = new HashMap<>();
        nameAlias.put(alias, keyName);

        HashMap<String, AttributeValue> atrValues = new HashMap<>();
        atrValues.put(":test", AttributeValue.builder().s(value).build());

        QueryRequest request = QueryRequest.builder()
                .tableName("Music")
                .expressionAttributeNames(nameAlias)
                .keyConditionExpression(alias + " = :test" )
                .expressionAttributeValues(atrValues)
                .build();
        try {
            var response = client.query(request);
            System.out.println(response.items());
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }
}
