# Mini-Autorizador

Este projeto demonstra uma aplicação Spring Boot desenvolvida utilizando as seguintes tecnologias:

* **JDK 21:** A mais recente versão LTS do Java, oferecendo novas funcionalidades e melhorias de desempenho.
* **Spring Boot:** Um framework para simplificar o desenvolvimento de aplicações Java, proporcionando automação de configuração e convenções.
* **Testcontainers:** Uma biblioteca para testes de software com containers
* **JUnit 5**: Um framework de testes para Java.
* **Mockito:** Um framework de mocking para testar código Java.
* **Maven:** Ferramenta de gerenciamento de build para projetos Java.
* **MySQL:** Um sistema gerenciador de banco de dados relacional popular e robusto.
* **Flyway:** Uma ferramenta para gerenciamento de migrações de banco de dados, garantindo a consistência do esquema ao longo do tempo.
* **Docker:** Uma plataforma de containerização que permite empacotar a aplicação e suas dependências em um único container, facilitando o desenvolvimento, teste e implantação.
* **Docker Compose:** Uma ferramenta para definir e executar aplicações multi-container Docker, como por exemplo, nossa aplicação Spring Boot e o banco de dados MySQL.

### **Pré-requisitos**

* **Java Development Kit (JDK) 21:** Instale o JDK 21 a partir do site oficial da Oracle ou OpenJDK.
* **Docker:** Instale o Docker Desktop ou o Docker Engine em seu sistema operacional.
* **Docker Compose:** Instale o Docker Compose seguindo as instruções oficiais.
* **Git:** Para clonar o repositório do projeto.
* **Maven:** Instale o Apache Maven seguindo as instruções do site oficial.

### **Clonando o Projeto**

```bash
  git clone https://github.com/fabianolc/mini-autorizador.git
```

### **Estrutura do Projeto**

* **src/main/java:** Contém o código fonte da aplicação Spring Boot.
* **src/main/resources:** Contém os recursos da aplicação, como arquivos de configuração e templates.
* **src/main/resources/db/migration:** Contém os scripts de migração do Flyway.
* **docker:** Contém os arquivos de configuração do Docker (Dockerfile e docker-compose.yml).

### **Executando a Aplicação**

Deve-se entrar na pasta `docker`.

1. **Compilando o projeto:**
```bash
  mvn clean package
```

2. **Iniciando os containers com Docker Compose:**

```bash
  docker compose up -d
```
> Este comando já irá efetuar o build da aplicação e a criação da imagem. 
2. **Parando os containers com Docker Compose:**
```bash
  docker compose down
```

### **Configurando o Banco de Dados**

* **Informações de conexão:** As informações de conexão com o banco de dados (URL, usuário e senha) estão configuradas no arquivo `application.properties` (ou `application.yml`).
* **Migrações:** O Flyway irá executar automaticamente as migrações definidas nos scripts SQL localizados na pasta `db/migration`.

### **Testando a Aplicação**

* **Acessando a aplicação:** A aplicação estará disponível na porta especificada no arquivo `docker-compose.yml`. Por exemplo, se a porta for 8080, você pode acessar a aplicação em `http://localhost:8080`.

**Para obter mais informações sobre as tecnologias utilizadas, consulte a documentação oficial:**

* Spring Boot: [https://spring.io/guides/gs/spring-boot/](https://spring.io/guides/gs/spring-boot)
* Maven: [https://maven.apache.org/](https://maven.apache.org/)
* Docker: [https://docs.docker.com/](https://docs.docker.com/)
* Docker Compose: [https://docs.docker.com/compose/](https://docs.docker.com/compose/)
* Flyway: [https://flywaydb.org/](https://flywaydb.org/)
