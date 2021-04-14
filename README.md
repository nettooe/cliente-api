# Cliente-API

Este projeto usa Quarkus, o "Supersonic Subatomic Java Framework".

Se você deseja saber mais sobre Quarkus, visite este website: https://quarkus.io/ .

## Executando a aplicação em modo desenvolvedor

Você pode rodar esta aplicação em modo dev, isto permite codificar ao mesmo tempo em que a aplicação está executando. Para isto, utilize o comando:


```
./mvnw quarkus:dev
```

## Empacotando e executando a aplicação

A aplicação pode ser empacotada com:

```
./mvnw package
```

Isto gera o arquivo `cliente-api-1.0.0-SNAPSHOT-runner.jar` no diretório `/target` .
Saiba que o arquivo gerado não é um _über-jar_ mas as dependencias são copiadas para dentro do diretório `target/lib` .

A aplicação pode ser executada agora usando:

```
java -jar target/cliente-api-1.0.0-SNAPSHOT-runner.jar
```


## Documentação da API

OpenAPI

`http://localhost:8080/openapi`


Swagger-UI

`http://localhost:8080/swagger-ui/`


## Gerando um executável nativo

Você pode criar um executável nativo usando:

```
./mvnw package -Pnative
```

Ou, se você não possuir uma GraalVM instalada, você pode rodar o compilador nativo em um container usando:

```
./mvnw package -Pnative -Dquarkus.native.container-build=true
```


Você pode executar seu executável nativo com:

```
./target/cliente-api-1.0.0-SNAPSHOT-runner
```

Se você quiser saber mais sobre como construir executáveis nativos, consulte:  `https://quarkus.io/guides/building-native-image` .


## Usando Docker

Para inicializar a aplicação utilizando containers docker, preparei um script de docker-compose que, além de um container com a aplicação, também inicializa um container com o banco de dados PostgreSQL.

Para inicializar, execute os dois comandos a seguir:

```
./mvnw clean package
```
```
docker-compose up -d --build
```

## Checando a saúde da aplicação

Para checar se a aplicação está em execução, acesse:

```
http://localhost:8080/health
```