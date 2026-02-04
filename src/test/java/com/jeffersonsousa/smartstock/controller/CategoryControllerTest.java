package com.jeffersonsousa.smartstock.controller;

import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.mapper.CategoryMapper;
import com.jeffersonsousa.smartstock.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CategoryMapper mapper;

    @MockitoBean
    CategoryService service;


        @Test
        @DisplayName("Deve Cria uma categoria com sucesso")
        void shouldCreateACategory() throws Exception {

            //arrange
            Category category = new Category();
            category.setName("Celulares");


            when(service.createCategory(Mockito.any())).thenReturn(category);

            String json = """
                    {
                        "category": "Celulares"
                    }
                    """;

            //act
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
            );

            result
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().string(""));

        }



}