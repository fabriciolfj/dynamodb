package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.Produto;
import org.joda.time.format.ISODateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GravarELerProduto {

    public static void main(String[] args) {

        var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);
        var data = new Date();

        var produto = new Produto();
        produto.setId("001");
        produto.setDataCadastro(sdf.format(data));
        produto.setCategoria("carros");

        var produto2 = new Produto();
        produto2.setId("002");
        produto2.setDataCadastro(sdf.format(new Date()));
        produto2.setCategoria("roupas");

        var produto3 = new Produto();
        produto3.setId("003");
        produto3.setDataCadastro(sdf.format(new Date()));
        produto3.setCategoria("carros");

        var produto4 = new Produto();
        produto4.setId("004");
        produto4.setDataCadastro(sdf.format(new Date()));
        produto4.setCategoria("calcados");

        var produto5 = new Produto();
        produto5.setId("005");
        produto5.setDataCadastro(sdf.format(new Date()));
        produto5.setCategoria("roupas");


        mapper.save(produto);
        mapper.save(produto2);
        mapper.save(produto3);
        mapper.save(produto4);
        mapper.save(produto5);

        var consulta = new Produto();
        consulta.setId("001");
        consulta.setDataCadastro(sdf.format(data));

        var expressions = new DynamoDBQueryExpression<Produto>().withHashKeyValues(consulta);
        var result = mapper.query(Produto.class, expressions);

        result.forEach(System.out::println);
        var consulta2 = new Produto();
        consulta2.setId("001");
        consulta2.setCategoria("carros");

        var exp = new DynamoDBQueryExpression<Produto>()
                .withIndexName("categoria-index")
                .withHashKeyValues(consulta2)
                .withConsistentRead(false);

        result = mapper.query(Produto.class, exp);
        System.out.println("========usando index========");

        result.forEach(System.out::println);
    }
}
