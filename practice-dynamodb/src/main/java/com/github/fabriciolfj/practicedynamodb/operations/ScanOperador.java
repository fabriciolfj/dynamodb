package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;
import com.github.fabriciolfj.practicedynamodb.util.DateFormatedUtil;
import com.github.fabriciolfj.practicedynamodb.util.PopularBaseUtil;

import javax.sound.midi.SysexMessage;
import java.util.HashMap;

public class ScanOperador {

    public static void main(String[] args) throws InterruptedException {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);

        var date1 = DateFormatedUtil.toDateIso();
        Thread.sleep(1000);
        var date2 = DateFormatedUtil.toDateIso();
        Thread.sleep(1000);
        var date3 = DateFormatedUtil.toDateIso();
        Thread.sleep(1000);

        PopularBaseUtil.execute(mapper, date1, date2, date3);

        var filter = new HashMap<String, AttributeValue>();
        filter.put(":v1", new AttributeValue().withS("mercearia"));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withIndexName("dataCadastro-index")
                .withFilterExpression("categoria = :v1")
                .withExpressionAttributeValues(filter)
                .withConsistentRead(false);

        //var products = mapper.scan(ProdutoV2.class, scanExpression);
        var products = mapper.parallelScan(ProdutoV2.class, scanExpression, 4);

        System.out.println("Total itens: " + products.size());
        products.stream().forEach(System.out::println);
    }
}
