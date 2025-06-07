package com.example.WebsiteTraining.services.impl;

import com.example.WebsiteTraining.models.Image;
import com.example.WebsiteTraining.models.Product;
import com.example.WebsiteTraining.repositories.ProductRepository;
import com.example.WebsiteTraining.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> listProducts(String title) {
        if (title != null) {
            return productRepository.findByTitle(title);
        }
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        // Проверка на null продукта
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Добавление изображений
        if (file1 != null && !file1.isEmpty()) {
            Image image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2 != null && !file2.isEmpty()) {
            product.addImageToProduct(toImageEntity(file2));
        }
        if (file3 != null && !file3.isEmpty()) {
            product.addImageToProduct(toImageEntity(file3));
        }

        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getAuthor());

        // Первое сохранение продукта
        Product savedProduct = productRepository.save(product);

        // Если есть изображения, устанавливаем previewImageId и сохраняем снова
        if (!savedProduct.getImages().isEmpty()) {
            savedProduct.setPreviewImageId(savedProduct.getImages().get(0).getId());
            productRepository.save(savedProduct);
        }
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
