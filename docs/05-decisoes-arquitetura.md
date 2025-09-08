# 05 - Decisões de Arquitetura

## 🎯 Objetivo
Este documento registra as **decisões arquiteturais** tomadas durante o desenvolvimento do projeto **Smartstock**.  
Cada decisão é registrada como um **ADR (Architecture Decision Record)**, explicando o contexto, a decisão tomada e suas consequências.

---

## 📌 ADR-01: Linguagem de Programação
- **Decisão:** Utilizar **Java 21** como linguagem principal.  
- **Contexto:**  
  - O projeto é voltado para portfólio profissional na área de backend.  
  - Java é a base de muitos sistemas corporativos complexos. 
  - Empresas usam porque precisam de escalabilidade, segurança e integração.  
- **Consequências:**  
  - Facilidade de manutenção e escalabilidade.  
  - Maior curva de aprendizado para iniciantes, mas ganho de credibilidade profissional.  

---

## 📌 ADR-02: Framework Principal
- **Decisão:** Utilizar **Spring Boot** como framework backend.  
- **Contexto:**  
  - Framework consolidado no mercado para APIs REST.  
  - Facilita integração com JPA, segurança, validação e documentação.  
  - Grande comunidade e documentação abundante.  
- **Consequências:**  
  - Acelera desenvolvimento e padroniza arquitetura.  
  - Pode adicionar certa complexidade inicial no aprendizado.  

---

## 📌 ADR-03: Banco de Dados
- **Decisão:** Utilizar **MySQL** como banco relacional.  
- **Contexto:**    
  - Open source, robusto e amplamente adotado.
  - O uso dessa linguagem é gratuito e super popular.
  - Leve, rápido e confiável.
- **Consequências:**  
  - Padronização no uso de banco relacional.  
  - Dependência de infraestrutura para rodar localmente (resolvido via Docker).  

---

## 📌 ADR-04: Swagger/OpenAPI
- **Decisão:**  
  Adotar **Swagger/OpenAPI** para documentação interativa e exploração dos endpoints da API.

- **Contexto:**  
  - Permite ao desenvolvedor visualizar todas as requisições disponíveis na aplicação.  
  - Facilita a leitura e manutenção de cada endpoint por novos desenvolvedores.  
  - Documentação integrada ao código, garantindo que esteja sempre atualizada.  

- **Consequências:**  
  - documentação padronizada, facilidade de testes, onboarding mais rápido de novos devs.  
  - pode tornar o código mais verboso e exigir configuração adicional em ambientes de produção.

---

## 📌 ADR-05: Lombok
- **Decisão:**  
  Adotar a biblioteca **Lombok** para reduzir a verbosidade do código Java, principalmente em classes de modelo e DTOs..

- **Contexto:**  
  - Em projetos Java, é comum a necessidade de gerar muitos métodos repetitivos (getters, setters, equals, hashCode, toString).
  - O Lombok fornece anotações (@Getter, @Setter, @Data, @Builder, etc.) que reduzem significativamente o boilerplate.  
  - Avaliadas alternativas como geração automática via IDE ou records (Java 14+), porém o Lombok foi escolhido por sua maturidade, ampla adoção na comunidade e compatibilidade com Spring Boot.
  - Facilita a manutenção e a legibilidade, permitindo que o time foque mais na regra de negócio do sistema.

- **Consequências:**  
  - Código mais limpo e legível.  
  - Redução de duplicação e erros manuais em métodos padrão.
  - Maior produtividade no desenvolvimento.
  - Requer configuração adequada em IDEs para evitar problemas de compilação.
  - Pode dificultar a compreensão do código por novos desenvolvedores que não conheçam a biblioteca.


  --- 

## 📂 Links Relacionados
- [01 - Visão Geral](01-visão-geral.md)  
- [02 - Requisitos](02-requisitos.md)
- [03 - Arquitetura](03-arquitetura.md)
- [04 - Modelo de Dados](04-modelo-dados.md)
- **05 - Decisões de Arquitetura** - - Você está aqui!
- [06 - Casos de Uso](06-casos-uso.md)
- [07 - Roadmap](07-roadmap.md)

