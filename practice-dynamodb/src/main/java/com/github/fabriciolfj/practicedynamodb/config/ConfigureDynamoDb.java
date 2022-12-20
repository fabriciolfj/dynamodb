package com.github.fabriciolfj.practicedynamodb.config;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class ConfigureDynamoDb {

    private static DynamoDbClient dynamoDbClient;

    public static DynamoDbEnhancedClient getDynamodbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getClient()).build();
    }

    public static DynamoDbClient getClient() {
        if (dynamoDbClient == null) {
            dynamoDbClient = getConfig();
        }

        return dynamoDbClient;
    }

    public static void close() {
        if (dynamoDbClient == null) {
            return;
        }

        dynamoDbClient.close();
    }

    private static DynamoDbClient getConfig() {
        System.out.println("Listing your Amazon DynamoDB tables:\n");
        final ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();

        return DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.SA_EAST_1)
                .build();
    }
}
