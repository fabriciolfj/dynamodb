package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;
import com.github.fabriciolfj.practicedynamodb.dto.Recado;

public class TransactionLoad {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);

        var produto = new ProdutoV2();
        produto.setId("001");
        produto.setCategoria("mercearia");

        var recado = new Recado();
        recado.setId("55e0de4c-aa2f-4dbb-a88d-052a66126b24");

        var load = new TransactionLoadRequest();
        load.addLoad(produto);
        load.addLoad(recado);

        var result = mapper.transactionLoad(load);

        result.forEach(System.out::println);
    }
}
