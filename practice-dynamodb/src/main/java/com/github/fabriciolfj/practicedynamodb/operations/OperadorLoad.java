package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Produto;
import com.github.fabriciolfj.practicedynamodb.util.DateFormatedUtil;

import java.lang.module.Configuration;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class OperadorLoad {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);
        var date = DateFormatedUtil.toDateIso();

        var produto = new Produto();
        produto.setId("001");
        produto.setDataCadastro(date);
        produto.setCategoria("mercearia");
        produto.setDescricao("arroz");

        mapper.save(produto);

        var item = mapper.load(Produto.class, "001", date);

        System.out.println(item);
    }
}
