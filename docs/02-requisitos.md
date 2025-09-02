# 02 - Requisitos do Sistema

## 🎯 Objetivo
Esta documentação lista os **requisitos funcionais e não funcionais** do projeto **SmartStock**, garantindo clareza sobre o que o sistema deve fazer e os critérios de qualidade que devem ser atendidos.

---

## 📝 Requisitos Funcionais (RF)
| Código | Requisito | Descrição |
|--------|-----------|-----------|
| RF01 | Criação de Estoque | O sistema deve permitir que o usuário crie um novo estoque informando a quantidade de ruas, endereços e andares. |
| RF02 | Gerar Endereços | O sistema deve gerar todos os endereços seguindo esse padrão no ID "A.1.2", onde "A" é a rua "1" é o endereço e "2" é o andar. |
| RF03 | Armazenamento de Estoque | O sistema deve armazenar todas os endereços no banco de dados para consulta futuras. |
| RF04 | CRUD de Categorias | O sistema deve permitir cadastrar, consultar, atualizar e excluir categorias. |
| RF05 | CRUD de Produtos | O sistema deve permitir cadastrar, consultar, atualizar e excluir produtos. |
| RF06 | Vizualização de produtos com baixo estoque | O sistema deve permitir que o usuário vizualize todos os produtos que estejam com o estoque baixo. |
| RF07 | Entrada de Produtos | O sistema deve permitir que o usuário realize a entrada de produtos no estoque criando um historico de movimentação e armazenando no banco de dados. |
| RF07 | Saída de Produtos | O sistema deve permitir que o usuário realize a saída de produtos no estoque criando um historico de movimentação e armazenando no banco de dados. |
| RF08 | Atualização de Produtos | O sistema deve atualizar a quantidade atual de produtos quando ouver alguma entrada ou saída do estoque. |

---

## ⚙️ Requisitos Não Funcionais (RNF)
| Código | Requisito | Descrição |
|--------|-----------|-----------|
| RNF01 | Linguagem | O sistema deve ser desenvolvido em **Java 21**. |
| RNF02 | Framework | O sistema deve utilizar **Spring Boot** para desenvolvimento e gerenciamento de dependências. |
| RNF03 | Banco de Dados | O sistema deve utilizar **MySQL** para persistência de dados. |
| RNF04 | Banco de Dados | O sistema deve utilizar **H2 Console** para persistência de dados durantes os testes. |
| RNF05 | Performance | a criação de estoque não deve ultrapassar **3 segundos** de processamento. |
| RNF06 | Manutenibilidade | O código deve seguir boas práticas, padrão MVC e ser modular para facilitar manutenção futura. |
| RNF07 | Documentação | O sistema deve utilizar **Swagger/OpenAPI** para a documentação interativa e testes das requisições API. |

---

## 💡 Observações
- Todos os requisitos funcionais estão diretamente relacionados às **principais funcionalidades do projeto**.  
- Os requisitos não funcionais garantem **qualidade, confiabilidade e escalabilidade** do sistema.  
- Esta lista será **atualizada sempre que novas funcionalidades forem adicionadas** ou decisões técnicas forem alteradas.

---

## 📂 Links Relacionados


- [01 - Visão Geral](01-visão-geral.md)  
- **02 - Requisitos** - Você está aqui
- [03 - Arquitetura](03-arquitetura.md)
- [04 - Modelo de Dados](04-modelo-dados.md)
- [05 - Decisões de Arquitetura](05-decisoes-arquitetura.md)
- [06 - Casos de Uso](06-casos-uso.md)
- [07 - Roadmap](07-roadmap.md)

    