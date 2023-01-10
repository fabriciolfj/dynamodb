package com.github.fabriciolfj.practicedynamodb.operations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.github.fabriciolfj.practicedynamodb.config.ConfigureDynamoDb;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;
import com.github.fabriciolfj.practicedynamodb.dto.Recado;
import com.github.fabriciolfj.practicedynamodb.util.DateFormatedUtil;

import javax.sql.rowset.spi.TransactionalWriter;
import java.util.UUID;

public class TransacionalSave {

    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        var mapper = new DynamoDBMapper(client);

        var produto1 = new ProdutoV2();
        produto1.setCategoria("mercearia");
        produto1.setDescricao("test1");
        produto1.setDataCadastro(DateFormatedUtil.toDateIso());
        produto1.setId("001");

        var recado = new Recado();
        recado.setCategoria("phone");
        recado.setDataCadastro(DateFormatedUtil.toDateIso());
        recado.setId(UUID.randomUUID().toString());

        var transaction = new TransactionWriteRequest();
        transaction.addPut(produto1);
        transaction.addUpdate(recado);
        mapper.transactionWrite(transaction);
    }
}
