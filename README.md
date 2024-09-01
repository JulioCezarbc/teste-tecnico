# teste-tecnico
é uma aplicação completa desenvolvida em Java utilizando o framework Spring Boot. Conforme pedido o enunciado todos os pontos principais estão no projeto.

## Funcionalidades
Gerenciamento de Clientes:
Cadastro, busca e exclusão de clientes.
Consulta de clientes por ID e CPF.

Gerenciamento de Empresas:
Cadastro, busca e exclusão de empresas.
Consulta de empresas por ID e CNPJ.

Transações Financeiras:
Depósitos e retiradas entre clientes e empresas.
Notificações automáticas via e-mail e webhook para clientes e empresas após uma transação bem-sucedida.

Tratamento de Exceções:
Exceções personalizadas para entidades não encontradas, conflitos de dados e violação de integridade.

## Tecnologias Utilizadas
Backend:
Java 17
Spring Boot 3.x
JPA (Java Persistence API)
Hibernate
MySQL
Jakarta Validation
JavaMailSender
RestTemplate

Ferramentas e Utilitários:
Maven
Postman (para testes de API)

## Configuração e Execução do Projeto
Pré-requisitos
Java 17+
Maven 3.8+

## Passos para Executar
git clone https://github.com/JulioCezarbc/tgid.git

## Acesse o diretório do projeto:
cd tgid

## Instale as dependências e compile o projeto:
mvn clean install

## Execute a aplicação:
mvn spring-boot:run

## Acessar a Documentação Swagger:
http://localhost:8080/swagger-ui.html
