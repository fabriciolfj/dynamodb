package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Music;

public class LerItemComBaseNoItem {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Music keySchema = new Music();
        keySchema.setArtist("Beto");
        keySchema.setSongTitle("media ");

        try {
            Music result = mapper.load(keySchema);
            if (result != null) {
                System.out.println(
                        "The song was released in "+ result.getArtist());
            } else {
                System.out.println("No matching song was found");
            }
        } catch (Exception e) {
            System.err.println("Unable to retrieve data: ");
            System.err.println(e.getMessage());
        }
    }
}
