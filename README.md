# Sparks Service

## Descrição

Microsserviço para processamento dos tópicos criados no Kafka pela API.

## Configuração

**Obs:** Caso você esteja utilizando uma IDE que tenha suporte ao Maven ou ao Spring como o _SpringToolSuite4_ o processo todo vai ser quase que automático bastando somente você definir as variáveis de ambiente nas configurações de execução do projeto, tirando isso, um simples botão de Play vai colocar o projeto em execução.

### Propriedades

**Obs:** Caso não esteja executando o MongoDB localmente, descomente os campos _username_ e _password_ do arquivo _application.yaml_ para que a aplicação consiga fazer o login no banco de dados.

### Instalação

Para que o projeto seja executado com sucesso é necessário instalar as dependências dele e para isso é preciso que você tenha o _Maven_ instalado em sua máquina. Com isso em mãos, para instalar as dependências, basta executar o comando abaixo na raiz do projeto:

```bash
mvn clean install
```

## Build

Antes de executar o projeto é necessário buildá-lo, para isso basta executar o comando abaixo na raiz do projeto:

```bash
mvn package
```

## Testes

Para executar os testes do projeto basta executar o comando abaixo na raiz do projeto:

```bash
mvn test
```

## Execução

Para executar o projeto basta executar o comando abaixo dentro da pasta _target_:

**Modelo:**

```bash
java \
    -Dspring.profiles.active=<Profile a ser executado (ex: dev, test, prod)> \
    -DPORT=<Porta em que a aplicação irá rodar (ex: 8082)> \
    -DKAFKA_BROKERS=<Endereços IPs dos brokers do Kafka (ex: localhost:9092)> \
    -DDB_HOST=<Host do banco de dados (ex: localhost)> \
    -DDB_PORT=<Porta do banco de dados (ex: 27017)> \
    -DDB_USER=<Usuário do banco de dados (ex: admin)> \
    -DDB_PASS=<Senha do banco de dados (ex: admin@123)> \
    -DDB_NAME=<Nome da base de dados (ex: Sparks)> \
    -jar service-<versao>.jar
```

**Exemplo:**

```bash
java \
    -Dspring.profiles.active=dev \
    -DPORT=8082 \
    -DKAFKA_BROKERS=localhost:9092 \
    -DDB_HOST=localhost \
    -DDB_PORT=27017 \
    -DDB_USER=admin \
    -DDB_PASS=admin@123 \
    -DDB_NAME=Sparks \
    -jar service-1.0.0.jar
```
