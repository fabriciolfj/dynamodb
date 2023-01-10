package com.github.fabriciolfj.practicedynamodb.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "Recados")
public class Recado {

    private String id;
    private String dataCadastro;
    private String categoria;

    @DynamoDBAttribute(attributeName = "id")
    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "dataCadastro")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "dataCadastro-rec-index", attributeName = "dataCadastro")
    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @DynamoDBAttribute(attributeName = "categoria")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "dataCadastro-rec-index", attributeName = "categoria")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Recado{" +
                "id='" + id + '\'' +
                ", dataCadastro='" + dataCadastro + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
