# 04 - Modelo de Dados

## 🎯 Objetivo
Documentar o **modelo de dados** do projeto **SmartStock**, descrevendo as entidades, seus atributos, tipos de dados e relacionamentos.  
O objetivo é fornecer clareza sobre **como os dados são estruturados e armazenados no banco**.

---

## 🧩 Entidades 

### 1. tb_category
Representa a categoria de produtos.

| Campo           | Tipo       | Descrição                              | Observações |
|-----------------|-----------|----------------------------------------|-------------|
| category_id | BIGINT | Identificador único da categoria | Chave primária |
| name | VARCHAR(255) | Nome da categoria |  |


### 2. tb_product
Representa o produto.

| Campo           | Tipo       | Descrição                              | Observações |
|-----------------|-----------|----------------------------------------|-------------|
| product_id | BIGINT| Identificador único do produto | Chave Primaria|
| name | VARCHAR(255) | Nome da categoria| |
| current_quantity | INT | Quantidade atual em estoque| Calculado pelo service |
| minimum_quantity | INT | Quantidade miníma de um produto deve ter ||
| price | DOUBLE | Preço do únitario do produto | |
| category_id | BIGINT | Identificador único da categoria | Chave estrangeira |

### 3. tb_location
Representa as localizações/endereços no estoque.
| Campo           | Tipo       | Descrição                              | Observações |
|-----------------|-----------|----------------------------------------|-------------|
| location_id | VARCHAR(255) | Identificador único da localização | Chave Primaria |
| aisle | CHAR(1) | Corredor em que o produto está armazenado| |
| position | INT | Posição/endereço no corredor em que o produto está armazenado | |
| floor | INT | Andar do armazém em que o produto está armazenado | |
| has_product | BIT(1) | Validação se o endereço está ocupado | |
| product_id | BIGINT | Identificador único do produto | Chave estrangeira |
| quantity | INT | Quantidade de produtos armazenados no endereço | |


### 4. tb_stock_movement
Representa uma movimentação no estoque.
| Campo           | Tipo       | Descrição                              | Observações |
|-----------------|-----------|----------------------------------------|-------------|
| stock_movement_id | BIGINT | Identificador único da movimentação de estoque | Chave primaria|
| type | VARCHAR(255) | Tipo de Movimentação realizada | |
| quantity | INT | Quantidade de produtos movidos | |
| movimentation_date | DATETIME(6) | Data/hora da movimentação | Padrão: Instant.now()|
| location_id | VARCHAR(255) | Identificador único da localização | Chave estrangeira |
| product_id | BIGINT | Identificador único do produto | Chave estrangeira |




**Relacionamento:**  
- Categoria → Produto (1:N)
    - Uma Categoria pode ter muitos Produtos.
    - A chave **category_id** em **tb_product** referencia **tb_category.category_id**.

- Produto → Localização (1:N)
    - Um Produto pode estar em muitas Localizações.
    - A chave **product_id** em **tb_location** referencia **tb_product.product_id**.

- Produto → Movimentação de Estoque (1:N)
    - Um Produto pode ter muitas Movimentações de Estoque.
    - A chave **product_id** em  **tb_stock_movement** referencia **tb_product.product_id**.

- Localização → Movimentação de Estoque (1:N)
    - Uma Localização pode ter muitas Movimentações de Estoque.
    - A chave **location_id** em **tb_stock_movement** referencia **tb_location.location_id**.


---

## 📊 Considerações Técnicas
- Todos as movimentações obrigatoriamente deve ter um produto e uma localização/endereço
- A coluna current_quantity no tb_roduct é atualizada pelo service toda vez que é realizada uma entrada/saída.

---

## 📂 Links Relacionados
- [01 - Visão Geral](01-visão-geral.md)  
- [02 - Requisitos](02-requisitos.md)
- [03 - Arquitetura](03-arquitetura.md)
- **04 - Modelo de Dados** - Você está aqui!
- [05 - Decisões de Arquitetura](05-decisoes-arquitetura.md)
- [06 - Casos de Uso](06-casos-uso.md)
- [07 - Roadmap](07-roadmap.md)
