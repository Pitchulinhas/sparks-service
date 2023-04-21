# Sparks Service

## Descrição

Microsserviço para processamento das requisições feitas à API para gerenciamento dos recursos do e-commerce "Sparks".

## Configuração

### Variáveis de ambiente

As variáveis de ambiente abaixo devem ser definidas para que a aplicação funcione corretamente:

- PORT: Porta em que a aplicação será executada. Por exemplo: 8082
- KAFKA_BROKERS: Endereço IP dos brokers Kafka separados por vírgula. Por exemplo: localhost:9092,localhost:9093
- DB_HOST: Host do banco de dados. Por exemplo: localhost
- DB_PORT: Host do banco de dados. Por exemplo: localhost
- DB_USER: Nome do usuário do banco de dados (essa variável não é necessária caso esteja rodando o banco em modo _standalone_). Por exemplo: admin
- DB_PASS: Senha do usuário do banco de dados (essa variável não é necessária caso esteja rodando o banco em modo _standalone_). Por exemplo: Mongo100%
- DB_NAME: Nome da base de dados criada no banco de dados. Por exemplo: Sparks

**Caso não esteja executando o MongoDB em modo standalone, descomentar os campos _username_ e _password_ do arquivo application.yaml**

## Instalação

Para instalar as dependências do projeto é necessário que tenha o Maven instalado em sua máquina ou utilize uma IDE que o tenha. Com isso em mãos, para instalar as dependências, basta executar o comando abaixo na raiz do projeto:

```bash
mvn clean install
```

## Build

Para buildar o projeto basta executar o comando abaixo na raiz do projeto:

```bash
mvn package
```

## Execução

Para executar o arquivo jar gerado basta executar o comando abaixo dentro da pasta **_target_** do projeto:

```bash
java -jar service-<versao>.jar
```
