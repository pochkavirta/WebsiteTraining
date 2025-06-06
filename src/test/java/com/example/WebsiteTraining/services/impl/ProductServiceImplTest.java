package com.example.WebsiteTraining.services.impl;

import com.example.WebsiteTraining.models.Image;
import com.example.WebsiteTraining.models.Product;
import com.example.WebsiteTraining.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;
    private MockMultipartFile testFile;
    private MockMultipartFile emptyFile;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setTitle("Test Product");

        testFile = new MockMultipartFile(
                "testFile",
                "test.jpg",
                "image/jpeg",
                "test image".getBytes());

        emptyFile = new MockMultipartFile("empty", new byte[0]);
    }

    @Test
    void listProducts_ShouldReturnAllProducts_WhenTitleIsNull() {
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        List<Product> result = productService.listProducts(null);

        assertEquals(1, result.size());
        verify(productRepository).findAll();
        verify(productRepository, never()).findByTitle(any());
    }

    @Test
    void listProducts_ShouldFilterByTitle_WhenTitleIsProvided() {
        String title = "Test";
        when(productRepository.findByTitle(title)).thenReturn(List.of(testProduct));

        List<Product> result = productService.listProducts(title);

        assertEquals(1, result.size());
        verify(productRepository).findByTitle(title);
        verify(productRepository, never()).findAll();
    }

    @Test
    void saveProduct_ShouldThrowException_WhenProductIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> productService.saveProduct(null, testFile, testFile, testFile));
    }

    @Test
    void saveProduct_ShouldSaveProductWithoutImages_WhenAllFilesAreEmpty() throws IOException {
        Product product = new Product();
        product.setTitle("Empty Files Test");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.saveProduct(product, emptyFile, emptyFile, emptyFile);

        verify(productRepository, times(1)).save(product);
        assertTrue(product.getImages().isEmpty());
    }

    @Test
    void saveProduct_ShouldSaveProductWithOneImage() throws IOException {
        Product product = new Product();
        product.setTitle("Single Image Test");

        when(productRepository.save(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        productService.saveProduct(product, testFile, emptyFile, emptyFile);

        verify(productRepository, times(2)).save(any(Product.class));
        assertEquals(1, product.getImages().size());
        assertTrue(product.getImages().get(0).isPreviewImage());
    }

    @Test
    void saveProduct_ShouldSaveProductWithMultipleImages() throws IOException {
        Product product = new Product();
        product.setTitle("Multiple Images Test");
        MockMultipartFile file2 = new MockMultipartFile(
                "file2", "test2.jpg", "image/jpeg", "test2".getBytes());

        when(productRepository.save(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        productService.saveProduct(product, testFile, file2, emptyFile);

        verify(productRepository, times(2)).save(any(Product.class));
        assertEquals(2, product.getImages().size());
        assertTrue(product.getImages().get(0).isPreviewImage());
        assertFalse(product.getImages().get(1).isPreviewImage());
    }

    @Test
    void saveProduct_ShouldHandleNullFiles() throws IOException {
        Product product = new Product();
        product.setTitle("Null Files Test");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.saveProduct(product, null, null, null);

        verify(productRepository, times(1)).save(product);
        assertTrue(product.getImages().isEmpty());
    }

    @Test
    void deleteProduct_ShouldCallRepositoryDelete() {
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Test Product", result.getTitle());
    }

    @Test
    void getProductById_ShouldReturnNull_WhenNotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(1L);

        assertNull(result);
    }

    @Test
    void toImageEntity_ShouldConvertFileToImage() throws Exception {
        Method method = ProductServiceImpl.class
                .getDeclaredMethod("toImageEntity", MultipartFile.class);
        method.setAccessible(true);

        Image result = (Image) method.invoke(productService, testFile);

        assertEquals("testFile", result.getName());
        assertEquals("test.jpg", result.getOriginalFileName());
        assertEquals("image/jpeg", result.getContentType());
        assertArrayEquals("test image".getBytes(), result.getBytes());
    }

    @Test
    void saveProduct_ShouldNotSetPreviewImageId_WhenNoImages() throws IOException {
        Product product = new Product();
        product.setTitle("No Images Test");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.saveProduct(product, emptyFile, emptyFile, emptyFile);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        assertNull(captor.getValue().getPreviewImageId());
    }
}