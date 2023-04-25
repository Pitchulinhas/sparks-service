# Sparks Service

## Descrição

Microsserviço para processamento dos tópicos criados no Kafka pela API.

## Configuração

**Obs:** Caso você esteja utilizando uma IDE que tenha suporte ao Maven ou ao Spring como o _SpringToolSuite4_ o processo todo vai ser quase que automático bastando somente você definir as variáveis de ambiente nas configurações de execução do projeto, tirando isso, um simples botão de Play vai colocar o projeto em execução.

### Instalação

Para que o projeto seja executado com sucesso é necessário instalar as dependências dele e para isso é preciso que você tenha o _Maven_ instalado em sua máquina. Com isso em mãos, para instalar as dependências, basta executar o comando abaixo na raiz do projeto:

```bash
mvn -Dmaven.test.skip=true clean install
```

## Build

Antes de executar o projeto é necessário buildá-lo, para isso basta executar o comando abaixo na raiz do projeto:

```bash
mvn -Dmaven.test.skip=true clean package
```

## Testes

Para executar os testes do projeto basta executar o comando abaixo na raiz do projeto:

**Modelo:**

```bash
mvn \
    -DPORT=<Porta em que a aplicação irá rodar (ex: 8082)> \
    -DKAFKA_BROKERS=<Endereços dos servidores Kafka separados por vírgula (ex: localhost:9092)> \
    -DDB_URL=<URL de conexão com a base de dados MongoDB (ex: mongodb://admin:P%40ssw0rd@localhost:27017/Sparks?authSource=admin)> \
test
```

**Exemplo:**

```bash
mvn \
    -DPORT=8082 \
    -DKAFKA_BROKERS=localhost:9092 \
    -DDB_URL=mongodb://admin:sparks20%40MG@localhost:27017/Sparks?authSource=admin \
test
```

## Execução

Para executar o projeto basta executar o comando abaixo dentro da pasta _target_:

**Modelo:**

```bash
java \
    -DPORT=<Porta em que a aplicação irá rodar (ex: 8082)> \
    -DKAFKA_BROKERS=<Endereços dos servidores Kafka separados por vírgula (ex: localhost:9092)> \
    -DDB_URL=<URL de conexão com a base de dados MongoDB (ex: mongodb://admin:P%40ssw0rd@localhost:27017/Sparks?authSource=admin)> \
    -jar service-<versao>.jar
```

**Exemplo:**

```bash
java \
    -DPORT=8082 \
    -DKAFKA_BROKERS=localhost:9092 \
    -DDB_URL=mongodb://admin:sparks20%40MG@localhost:27017/Sparks?authSource=admin \
    -jar service-1.0.0.jar
```
