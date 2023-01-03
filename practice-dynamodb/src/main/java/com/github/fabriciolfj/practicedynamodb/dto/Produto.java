package com.github.fabriciolfj.practicedynamodb.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "Product")
public class Produto {

    private String id;
    private String dataCadastro;
    private String categoria;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "categoria-index")
    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "dataCadastro")
    @DynamoDBRangeKey(attributeName = "dataCadastro")
    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "categoria-index")
    @DynamoDBAttribute(attributeName = "categoria")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", dataCadastro='" + dataCadastro + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
