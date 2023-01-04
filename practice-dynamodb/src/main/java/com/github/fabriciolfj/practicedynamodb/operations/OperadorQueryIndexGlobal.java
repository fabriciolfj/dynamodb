package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;
import com.github.fabriciolfj.practicedynamodb.util.DateFormatedUtil;
import com.github.fabriciolfj.practicedynamodb.util.PopularBaseUtil;

import java.util.HashMap;
import java.util.Map;

public class OperadorQueryIndexGlobal {

    public static void main(String[] args) throws InterruptedException {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);
        var date = DateFormatedUtil.toDateIso();
        Thread.sleep(1000);
        var date2 = DateFormatedUtil.toDateIso();
        Thread.sleep(1000);
        var date3 = DateFormatedUtil.toDateIso();

        PopularBaseUtil.execute(mapper, date, date2, date3);

        Map<String, AttributeValue> params = new HashMap<>();
        params.put(":v1", new AttributeValue().withS("mercearia"));
        params.put(":v2", new AttributeValue().withS(date));

        DynamoDBQueryExpression<ProdutoV2> queryExpression = new DynamoDBQueryExpression<ProdutoV2>()
                .withIndexName("dataCadastro-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("categoria = :v1 and dataCadastro >= :v2")
                .withExpressionAttributeValues(params);

        var result = mapper.query(ProdutoV2.class, queryExpression);
        result.forEach(System.out::println);
    }
}
