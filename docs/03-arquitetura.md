# 03 - Arquitetura do Sistema

## 🎯 Objetivo
Esta documentação descreve a **arquitetura do projeto SmartSock**, explicando como os módulos se comunicam, a estrutura de camadas e as tecnologias utilizadas.  
O objetivo é fornecer clareza sobre **a construção do sistema e facilitar manutenção ou evolução futura**.

---

## 🧩 Diagrama de Arquitetura

    [Cliente / Front-end] --> [Controller (REST API)] --> [Service (Regras de Negócio)] --> [Repository (JPA)] --> [MySQL]

---

## Explicação do fluxo:

1. O cliente envia requisições HTTP (ex.: criar estoque).

2. O Controller recebe a requisição, valida dados básicos e encaminha para o Service.

3. O Service aplica as regras de negócio, como o gerar endereços e criar estoque.

4. O Repository persiste ou consulta dados no banco MySQL usando JPA.

5. O resultado é retornado para o cliente.

---

## ⚙️ Camadas do Sistema

| Camada | Responsabilidade |
|--------|------------------|
| Controller | Receber e responder requisições REST, realizar validações iniciais de entrada. |
| Service | Centralizar toda a lógica de negócio, incluindo criação de estoque e entrada ou saída de produtos. |
| Repository | Abstrair o acesso ao banco de dados, realizando operações CRUD através do Spring Data JPA. |
| Entity | Representar os objetos do domínio (Categoria, Produto, Localização) no banco de dados. |

---

## 🛠️ Tecnologias Utilizadas

- **Java** -  linguagem principal do projeto.
- **Spring Boot** - framework para construção da API REST.
- **Spring Data JPA** - persistência de dados e integração com o banco.
- **Lombok** - biblioteca para reduzir boilerplate no Java.
- **Swagger/OpenAPI** - documentação e teste interativo da API.
- **Junit 5** - criação de testes unitários.
- **Mockito** - mock de dependências nos testes.
- **H2 Database** - banco em memória utilizado nos testes.
- **MySQL** - banco de dados relacional utilizado.
- **Maven** - gerenciamento de dependências e build do projeto.
- **Postman** - teste e documentação dos endpoints da API.
- **MapStruct** – conversão automática entre DTOs e entidades, reduzindo código boilerplate

---

## 💡 Boas Práticas Arquiteturais

- **Separação de camadas (Controller, Service, Repository)** para manter código limpo e modular.

- **DTOs (Data Transfer Objects)** para comunicação entre camadas e evitar exposição direta das entidades.

- **Validação de dados** nas camadas apropriadas (Controller para validações básicas e Service para regras de negócio).

- **Registro de logs** e tratamento de exceções centralizado para facilitar manutenção.

---

## 📂 Links Relacionados
- [01 - Visão Geral](01-visão-geral.md)  
- [02 - Requisitos](02-requisitos.md)
- **03 - Arquitetura** - Você está aqui!
- [04 - Modelo de Dados](04-modelo-dados.md)
- [05 - Decisões de Arquitetura](05-decisoes-arquitetura.md)
- [06 - Casos de Uso](06-casos-uso.md)
- [07 - Roadmap](07-roadmap.md)
