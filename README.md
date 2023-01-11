# dynamodb
- Para saber mais sobre overloading de index no dynamodb https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-gsi-overloading.html
- codigos de exemplo me java sobre dynamodb https://github.com/aws-samples/aws-dynamodb-examples/tree/master/DynamoDB-SDK-Examples/java e
- https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb

## Alguns conceitos do dynamodb
- temos alguns tipos de chaves primárias:
  - chave simples -> onde o chave fornecida é a partição aonde será armazenada (por traz o dynamodb gera um hash da chave, e o resultado é o local de armazenamento)
  - chave composta -> aonde possui a primary key (hash) e a chave de ordenação (range)
- alem das chaves, posso criar o index secundários, e projetar quais atributos serão retornados.
- existe 2 tipos de index secundários:
  -  global -> quando o index composto (chave + chave de ordenação), é diferente da chave principal
  - local -> quando o index composto, chave é iqual a chave principal mas o sort (ordenação) é diferente
- obs: os dados são armazenados nas partiçoes e, caso aja, index secundários.
- as solicitações para leitura ou gravação ao dynamodb, são feitas via requisição http e precisam ser autenticadas (sdks da aws fazem isso pra gente)
- um exemplo de adição de um indice na tabele Music
````
{
    TableName: "Music",
    AttributeDefinitions:[
        {AttributeName: "Genre", AttributeType: "S"},
        {AttributeName: "Price", AttributeType: "N"}
    ],
    GlobalSecondaryIndexUpdates: [
        {
            Create: {
                IndexName: "GenreAndPriceIndex",
                KeySchema: [
                    {AttributeName: "Genre", KeyType: "HASH"}, //Partition key
                    {AttributeName: "Price", KeyType: "RANGE"}, //Sort key
                ],
                Projection: {
                    "ProjectionType": "ALL"
                },
                ProvisionedThroughput: {                                // Only specified if using provisioned mode
                    "ReadCapacityUnits": 1,"WriteCapacityUnits": 1
                }
            }
        }
    ]
}
````
- na consulta, podemos utilizar o index via Query ou Scan, e tambem fazer uso da projeção, caso queiramos o retorno de apenas alguns atributos

## Partição uso chave simples
- quando usamos uma primary key simples, ela se torna a partição
- por traz o dynamodb usa uma função que gera um hash, com base nessa chave
- com base no hash o dynamodb determina aonde salvará a informação

## Partição uso chave composta (chave de particao e chave de classificacao)
- funciona igual a chave simples, mas os dados são salvos próximos ou agrupados na mesma partição (com base no hash retornado pela função, que aplicou na chave composta)
- exemplo de criação de uma tabela com chave composta
```
{
    TableName : "Music",
    KeySchema: [
        {
            AttributeName: "Artist",
            KeyType: "HASH", //Partition key
        },
        {
            AttributeName: "SongTitle",
            KeyType: "RANGE" //Sort key
        }
    ],
    AttributeDefinitions: [
        {
            AttributeName: "Artist",
            AttributeType: "S"
        },
        {
            AttributeName: "SongTitle",
            AttributeType: "S"
        }
    ],
    ProvisionedThroughput: {       // Only specified if using provisioned mode
        ReadCapacityUnits: 1,
        WriteCapacityUnits: 1
    }
}
```
- no exemplo acima, estamos precisando a capacidade de leitura / gravação
- isso impacta na nossa carga de trabalho e no tipo de leitura / gravação (leitura altamente consistente, final consistente e transacional)
- sob demanda, indicada quando não conhecemos nossa demanda, não tempos essa preocupação


## Tipos de tabela
- o dynamodb oferece 2 tipos de tabela:
  - standard -> modelo padrão
  - Standard-Infrequent Access -> pouca utilizada, ideal para logs ou histórico
- Obter informações sobre uma tabela

## TTL (tempo de vida)
- temos que adicionar um campo na tabela
- habilitar nele o ttl
- devemos salvar informação no formato timestamp, com a data/hora de expiração

## Consulta PartiQL
- uma api do dynamodb que aceita consulta sql
- dentro da aplicação deveremos utilizar o ExecuteStatement
```
SELECT AlbumTitle, Year, Price
FROM Music
WHERE Artist='No One You Know' AND SongTitle = 'Call Me Today' 
```
- ha um limite de 1 mb em dados retornados na consulta ao dynamodb (caso precise de mais dados, utiliza a estratégia de paginação)
  - tanto uso do query/scan
  - como PartiQL
  - 
# Interfaces para uso
- existem algumas interfaces de uso que podemos utilizar em operações ao dynamodb
- por exemplo, a interface de persistência, onde podemos criar objetos que representam os dados no dynamo e os utilizamos para consulta:
```
    public static void main(String[] args) {
        var client = ConfigureDynamoDb.getAmazonDynamodbClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Music keySchema = new Music();
        keySchema.setArtist("Beto");
        keySchema.setSongTitle("media ");

        try {
            Music result = mapper.load(keySchema);
            if (result != null) {
                System.out.println(
                        "The song was released in "+ result.getArtist());
            } else {
                System.out.println("No matching song was found");
            }
        } catch (Exception e) {
            System.err.println("Unable to retrieve data: ");
            System.err.println(e.getMessage());
        }
    }
```

### Anotações
- podemos fazer uso de anotações para mapear os atributos da classe ao atributos da tabela (similar ao jpa)
- obs: utilizamos nos getters dos atributos da classe
- alguns exemplos:
  DynamoDBAttribute
  DynamoDBAutoGeneratedKey
  DynamoDBDocument
  DynamoDBHashKey
  DynamoDBIgnore
  DynamoDBIndexHashKey
  DynamoDBIndexRangeKey
  DynamoDBRangeKey
  DynamoDBTable
  DynamoDBTypeConverted
  DynamoDBTyped
  DynamoDBVersionAttribute
- um exemplo de mapeamento de chave composta, onde id a a partição e replyDateTime a classificação
```
@DynamoDBTable(tableName="Reply")
public class Reply {
    private Integer id;
    private String replyDateTime;

    @DynamoDBHashKey(attributeName="Id")
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @DynamoDBRangeKey(attributeName="ReplyDateTime")
    public String getReplyDateTime() { return replyDateTime; }
    public void setReplyDateTime(String replyDateTime) { this.replyDateTime = replyDateTime; }

   // Additional properties go here.
}
```
- para o mapeamento de um index secundario global, utilizamos o formato abaixo:
````
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "dataCadastro-index", attributeName = "dataCadastro")
    public String getDataCadastro() {
        return dataCadastro;
    }
    
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "dataCadastro-index", attributeName = "categoria")
    public String getCategoria() {
        return categoria;
    }
````
- um ponto importante e a respeito da data, que vemos utilizar o formato da iso 8601
- segue um exemplo abaixo:
```
 var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
 var produto = new Produto();
 produto.setId("001");
 produto.setDataCadastro(sdf.format(data));
 produto.setCategoria("carros");

{
  "id": {
    "S": "001"
  },
  "dataCadastro": {
    "S": "2023-01-02T23:14:06.883-03:00"
  },
  "categoria": {
    "S": "carros"
  }
}
```

# Tratamento de erros
- quando se utiliza aws sdk para comunicação com o dyanamodb, esta é transparente ao cliente
- como token de autorização nas requisições por exemplo
- em caso de falha, seja token expirado por exemplo, aconselha-se utilizar uma mecanismo de retry exponencial

# Configuração personalizada para O DynamoDBMapper
- existe alguns enuns que podemos configurar, como o a forma de carregamento paginada, qualidade dos dados na consulta e salvamento.
```
AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
        .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
        .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
        .withTableNameOverride(null)
        .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
    .build();

DynamoDBMapper mapper = new DynamoDBMapper(client, mapperConfig);
```
## Algumas opcoes

### Paginação

#### LAZY_LOADING
- carrega os demais dados quando possível e mantém todos os resultados na memória

#### EAGER_LOADING
- carrega todos dados quando a lista é inicializada

#### ITERATION_ONLY
- so pode haver um iterador na lista, e mantem na memoria apenas dados de uma pagina. Ideal para grande volume de dados

### Consistencia
- eventual -> default
- consistente -> garante que a informação foi persistida na tabela, mas aumenta o custo

### SaveBehavior
- update -> default, atualiza os itens mapeados e insere dados default nos não mapeados  ou passados na requisição
- clobber -> recria os itens somente com os dados passados.
 
- continuar https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/DynamoDBMapper.OptimisticLocking.html

## Bloqueio otimista
- o dynamodb versiona o registro, afim de garantir que o dado que você está atualizando, é o mesmo que encontra-se no dynamodb
- evitando que outro atualize o registro que você esta atualizando
- o incremente da versão é feita pela sdk aws java automaticamente, abaixo um exemplo no uso da anotação de versionamento.
```
@DynamoDBTable(tableName="ProductCatalog")
public class CatalogItem {

    private Integer id;
    private String title;
    private String ISBN;
    private Set<String> bookAuthors;
    private String someProp;
    private Long version;

    @DynamoDBHashKey(attributeName="Id")
    public Integer getId() { return id; }
    public void setId(Integer Id) { this.id = Id; }

    @DynamoDBAttribute(attributeName="Title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @DynamoDBAttribute(attributeName="ISBN")
    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN;}

    @DynamoDBAttribute(attributeName = "Authors")
    public Set<String> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(Set<String> bookAuthors) { this.bookAuthors = bookAuthors; }

    @DynamoDBIgnore
    public String getSomeProp() { return someProp;}
    public void setSomeProp(String someProp) {this.someProp = someProp;}

    @DynamoDBVersionAttribute
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version;}
}
```
- observações:
  -  transações não dão suporte ao bloqueio otiminista
  - para desabilitar, pode-se configurar um mapperconfig com CLOBBER.
```
DynamoDBMapper mapper = new DynamoDBMapper(client);

// Load a catalog item.
CatalogItem item = mapper.load(CatalogItem.class, 101);
item.setTitle("This is a new title for the item");
...
// Save the item.
mapper.save(item,
    new DynamoDBMapperConfig(
        DynamoDBMapperConfig.SaveBehavior.CLOBBER));
```

