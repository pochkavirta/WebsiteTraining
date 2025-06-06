package com.example.WebsiteTraining.controllers;

import com.example.WebsiteTraining.models.Product;
import com.example.WebsiteTraining.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void products_ShouldReturnProductsView_WithProductsAttribute() throws Exception {
        // Arrange
        List<Product> products = Collections.singletonList(new Product());
        when(productService.listProducts(any())).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"));

        verify(productService).listProducts(null);
    }

    @Test
    void products_ShouldFilterByTitle_WhenTitleParamPresent() throws Exception {
        // Arrange
        String title = "test";
        when(productService.listProducts(title)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/").param("title", title))
                .andExpect(status().isOk());

        verify(productService).listProducts(title);
    }

    @Test
    void productInfo_ShouldReturnProductInfoView_WithProductAndImages() throws Exception {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("product-info"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("images"));

        verify(productService).getProductById(productId);
    }

    @Test
    void createProduct_ShouldSaveProductAndRedirect() throws Exception {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");

        // Создаем mock файлы
        MockMultipartFile file1 = new MockMultipartFile(
                "file1",
                "test1.jpg",
                "image/jpeg",
                "test image 1".getBytes());

        MockMultipartFile file2 = new MockMultipartFile(
                "file2",
                "test2.jpg",
                "image/jpeg",
                "test image 2".getBytes());

        MockMultipartFile file3 = new MockMultipartFile(
                "file3",
                "test3.jpg",
                "image/jpeg",
                "test image 3".getBytes());

        // Act & Assert
        mockMvc.perform(multipart("/product/create")
                        .file(file1)
                        .file(file2)
                        .file(file3)
                        .flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(productService).saveProduct(
                any(Product.class),
                any(MultipartFile.class),
                any(MultipartFile.class),
                any(MultipartFile.class));
    }

    @Test
    void deleteProduct_ShouldDeleteProductAndRedirect() throws Exception {
        // Arrange
        Long productId = 1L;

        // Act & Assert
        mockMvc.perform(post("/product/delete/{id}", productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(productService).deleteProduct(productId);
    }
}