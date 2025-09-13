# 06 - Casos de Uso

## 🎯 Objetivo
Descrever os **principais cenários de uso** do sistema **Smartstock**, detalhando os atores, fluxos e exceções.

---

## 👤 Atores
- **Usuário Comum:** pessoa que deseja realizar gerenciamento no estoque.  


---

## 📌 Caso de Uso 01: Realizar Cadastro de Categoria
- **Ator Principal:** Usuário Comum  
- **Pré-condições:**  
  - O sistema deve estar em funcionamento.  
  - O usuário deve acessar a API ou interface do sistema.  

- **Fluxo Principal:**  
  1. O usuário informa o nome da categoria.  
  2. O sistema salva no banco de dados a nova categoria. 

- **Fluxos Alternativos:**    
  - 2a. Caso o nome esteja null, o sistema retorna que o campo não foi preenchido.  

---

## 📌 Caso de Uso 02: Realizar Cadastro de Produto
- **Ator Principal:** Usuário Comum  
- **Pré-condições:**  
  - O sistema deve estar em funcionamento.  
  - O usuário deve acessar a API ou interface do sistema.  

- **Fluxo Principal:**  
  1. O usuário informa o nome do produto.  
  2. O usuário informa a quantidade minima.
  3. O usuário informa o preço do produto.
  4. O usuário informa a categoria do produto.
  5. O sistema salva no banco de dados um novo Produto.

- **Fluxos Alternativos:**    
  - 1a. Caso o nome esteja null, o sistema retorna que o campo não foi preenchido.
  - 3a. Caso o preço esteja null, o sistema retorna que o campo não foi preenchido.
  - 4a. Caso o categoria esteja null, o sistema retorna que o campo não foi preenchido.  

---

## 📌 Caso de Uso 03: Gerar Estoque no banco de dados
- **Ator Principal:** Usuário Comum  
- **Pré-condições:**  
  - O sistema deve estar em funcionamento.
  - A tabela de enderços deve estar vazia.  
  - O usuário deve acessar a API ou interface do sistema.  

- **Fluxo Principal:**  
  1. O usuário informa a quantidade de corredores.  
  2. O usuário informa a quantidade de posições/endereços.
  3. O usuário informa a quantidade de andares/altura.
  4. O sistema valida se não existe estoque cadastrado.
  5. O sistema gera todos os endereços.
  6. O sistema gera todos os IDs dos endereços.
  7. O sistema salva no banco de dados todos os endereços.

- **Fluxos Alternativos:**    
  - 1a. Caso a quantidade de corredores esteja null, o sistema retorna que o campo não foi preenchido.
  - 2a. Caso a quantidade de posições/endereço esteja null, o sistema retorna que o campo não foi preenchido.
  - 3a. Caso a quantidade de andares/altura esteja null, o sistema retorna que o campo não foi preenchido.
  - 4a. Caso exista estoque cadastrado, o sistema retorna que já existe estoque cadastrado.

---

## 📌 Caso de Uso 04: Entrada de Produtos e Registro de movimentação
- **Ator Principal:** Usuário Comum  
- **Pré-condições:**  
  - O sistema deve estar em funcionamento.
  - Deve existir posições/endereços no banco de dados.  
  - O usuário deve acessar a API ou interface do sistema.  

- **Fluxo Principal:**  
  1. O usuário informa a quantide de produtos.  
  2. O usuário informa o produto que será armazenado.
  3. O sistema busca um endereço vazio.
  4. O sistema armazena o produto nesse endereço.
  5. O sistema registra uma movimentação no estoque e salva no banco de dados.
  6. O sistema atualiza a quantidade total de produtos na tabela de produtos.

- **Fluxos Alternativos:**    
  - 1a. Caso a quantidade de produtos esteja null, o sistema retorna que o campo não foi preenchido.
  - 1a. Caso a quantidade de produtos esteja menor ou igual a 0, o sistema retornar que a quantidade está incorreta.
  - 2a. Caso o produto não seja encontrado no sistema, o sistema retorna que o produto não foi encontrado.
  - 3a. Caso não exista endereços vazios, o sistema retorna que não existe espaço para armazenar esse produto.

---

## 📌 Caso de Uso 05: Saída de Produtos e Registro de movimentação
- **Ator Principal:** Usuário Comum  
- **Pré-condições:**  
  - O sistema deve estar em funcionamento.
  - Deve existir posições/endereços no banco de dados.  
  - O usuário deve acessar a API ou interface do sistema.  

- **Fluxo Principal:**  
  1. O usuário informa a quantide de produtos.  
  2. O usuário informa o produto que será retirado do estoque.
  3. O sistema valida se tem produto suficiente no estoque.
  3. O sistema busca os endereços com o produto solicitado.
  4. O sistema retira o produto do endereço.
  5. O sistema registra uma movimentação no estoque e salva no banco de dados.
  6. O sistema atualiza a quantidade total de produtos na tabela de produtos.

- **Fluxos Alternativos:**    
  - 1a. Caso a quantidade de produtos esteja null, o sistema retorna que o campo não foi preenchido.
  - 1a. Caso a quantidade de produtos esteja menor ou igual a 0, o sistema retornar que a quantidade está incorreta.
  - 2a. Caso o produto não seja encontrado no sistema, o sistema retorna que o produto não foi encontrado.
  - 3a. Caso não tenha produto suficiente no estoque, o sistema retorna que o saldo de produtos é insuficiente.

---

## 📌 Caso de Uso 05: Deve realizar busca de produtos com estoque baixo
- **Ator Principal:** Usuário Comum  
- **Pré-condições:**  
  - O sistema deve estar em funcionamento.
  - O usuário deve acessar a API ou interface do sistema.  

- **Fluxo Principal:**  
  1. O sistema compara a quantidade atual produto com a quantidade minima do produto.
  2. O sistema valida se o produto esta com o estoque baixo.
  3. O sistema lista todos os produtos que estão com o estoque baixo.


---

## 📂 Links Relacionados
- [01 - Visão Geral](01-visão-geral.md)  
- [02 - Requisitos](02-requisitos.md)
- [03 - Arquitetura](03-arquitetura.md)
- [04 - Modelo de Dados](04-modelo-dados.md)
- [05 - Decisões de Arquitetura](05-decisoes-arquitetura.md)
- **06 - Casos de Uso** - Você está aqui!
- [07 - Roadmap](07-roadmap.md)
