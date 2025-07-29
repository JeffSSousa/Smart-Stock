package com.jeffersonsousa.smartstock.dto;

public record ProductRequestDTO(String name, Integer minimumQuantity, Double price, Long categoryId) {

}
