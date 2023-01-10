package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;
import com.github.fabriciolfj.practicedynamodb.util.DateFormatedUtil;

import java.util.Arrays;

public class BatchSave {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);

        var produto1 = new ProdutoV2();
        produto1.setCategoria("mercearia");
        produto1.setDescricao("test1");
        produto1.setDataCadastro(DateFormatedUtil.toDateIso());
        produto1.setId("001");

        var produto2 = new ProdutoV2();
        produto2.setCategoria("mercearia");
        produto2.setDescricao("test2");
        produto2.setDataCadastro(DateFormatedUtil.toDateIso());
        produto2.setId("002");

        mapper.batchLoad(Arrays.asList(produto2, produto1));

    }
}
