package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;
import com.github.fabriciolfj.practicedynamodb.util.DateFormatedUtil;
import com.github.fabriciolfj.practicedynamodb.util.PopularBaseUtil;

import java.util.HashMap;
import java.util.Map;

public class OperadorQueryPageIndexGlobal {

    public static void main(String[] args) throws InterruptedException {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);
        var date = "2023-01-03T23:37:24.189Z";

        var index = 0;
        while(index < 2500) {
            var produto = new ProdutoV2();
            produto.setCategoria("mercearia");
            produto.setDescricao("test" + index);
            produto.setDataCadastro(date);
            produto.setId("004"+index);

            mapper.save(produto);
            index++;
        }

        Map<String, AttributeValue> params = new HashMap<>();
        params.put(":v1", new AttributeValue().withS("mercearia"));
        params.put(":v2", new AttributeValue().withS(date));

        DynamoDBQueryExpression<ProdutoV2> queryExpression = new DynamoDBQueryExpression<ProdutoV2>()
                .withIndexName("dataCadastro-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("categoria = :v1 and dataCadastro >= :v2")
                .withExpressionAttributeValues(params);

        var result = mapper.queryPage(ProdutoV2.class, queryExpression, DynamoDBMapperConfig.builder()
                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.ITERATION_ONLY)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL).build());

        result.getResults().forEach(System.out::println);

        while (result.getCount() < result.getScannedCount()) {
            queryExpression = new DynamoDBQueryExpression<ProdutoV2>()
                    .withIndexName("dataCadastro-index")
                    .withConsistentRead(false)
                    .withKeyConditionExpression("categoria = :v1 and dataCadastro >= :v2")
                    .withExpressionAttributeValues(params)
                    .withExclusiveStartKey(result.getLastEvaluatedKey());

            result = mapper.queryPage(ProdutoV2.class, queryExpression, DynamoDBMapperConfig.builder()
                    .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.ITERATION_ONLY)
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL).build());

            result.getResults().forEach(System.out::println);
        }

    }
}
