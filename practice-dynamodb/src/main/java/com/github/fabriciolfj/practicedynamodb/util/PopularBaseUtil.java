package com.github.fabriciolfj.practicedynamodb.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.fabriciolfj.practicedynamodb.dto.ProdutoV2;

import java.util.stream.IntStream;

public class PopularBaseUtil {

    public static void execute(DynamoDBMapper mapper, String date, String date2, String date3) {

        IntStream.of(1, 2, 3)
                .mapToObj(c -> {
                    var produto = new ProdutoV2();
                    produto.setCategoria("mercearia");
                    produto.setDescricao("test" + c);
                    produto.setDataCadastro(date);
                    produto.setId("001"+c);
                    return produto;
                }).forEach(mapper::save);

        IntStream.of(1, 2, 3)
                .mapToObj(c -> {
                    var produto = new ProdutoV2();
                    produto.setCategoria("frios");
                    produto.setDescricao("test" + c);
                    produto.setDataCadastro(date2);
                    produto.setId("002"+c);
                    return produto;
                }).forEach(mapper::save);

        IntStream.of(1, 2, 3)
                .mapToObj(c -> {
                    var produto = new ProdutoV2();
                    produto.setCategoria("eletronicos");
                    produto.setDescricao("test" + c);
                    produto.setDataCadastro(date3);
                    produto.setId("003"+c);
                    return produto;
                }).forEach(mapper::save);

        IntStream.of(1, 2, 3)
                .mapToObj(c -> {
                    var produto = new ProdutoV2();
                    produto.setCategoria("mercearia");
                    produto.setDescricao("test" + c);
                    produto.setDataCadastro(date3);
                    produto.setId("003"+c);
                    return produto;
                }).forEach(mapper::save);
    }
}
