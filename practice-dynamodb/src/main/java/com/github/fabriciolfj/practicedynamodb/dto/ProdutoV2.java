package com.github.fabriciolfj.practicedynamodb.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "Product")
public class ProdutoV2 {

    private String id;
    private String dataCadastro;
    private String categoria;
    private String descricao;

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "dataCadastro-index", attributeName = "dataCadastro")
    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @DynamoDBRangeKey(attributeName = "categoria")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "dataCadastro-index", attributeName = "categoria")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @DynamoDBAttribute(attributeName = "descricao")
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", dataCadastro='" + dataCadastro + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
