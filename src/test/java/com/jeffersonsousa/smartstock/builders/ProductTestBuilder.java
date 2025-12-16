package com.jeffersonsousa.smartstock.builders;

import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Product;

public class ProductTestBuilder {

    private Long productId;
    private String name = "Apple iPhone 15 Pro Max";
    private Integer currentQuantity = 20;
    private Integer minimumQuantity = 15;
    private Double price = 12289.0;
    private Category category = new Category(1L,"Celulares",null);

    private  ProductTestBuilder(){}

    public static ProductTestBuilder aProduct() {
        return new ProductTestBuilder();
    }

    public ProductTestBuilder withProductId(Long id){
        this.productId = id;
        return this;
    }

    public ProductTestBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ProductTestBuilder withCurrentQuantity(Integer currentQuantity){
        this.currentQuantity = currentQuantity;
        return this;
    }

    public ProductTestBuilder withMinimumQuantity(Integer minimumQuantity){
        this.minimumQuantity = minimumQuantity;
        return this;
    }

    public ProductTestBuilder withPrice(Double price){
        this.price = price;
        return this;
    }

    public ProductTestBuilder withCategory(Category category){
        this.category = category;
        return this;
    }

    public Product build(){
        Product product = new Product();
        product.setProductId(productId);
        product.setName(name);
        product.setCurrentQuantity(currentQuantity);
        product.setMinimumQuantity(minimumQuantity);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }

}
