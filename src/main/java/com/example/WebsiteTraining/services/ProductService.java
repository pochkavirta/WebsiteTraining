package com.example.WebsiteTraining.services;

import com.example.WebsiteTraining.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    /**
     * Получает список продуктов, фильтруя по названию (если указано)
     * @param title название продукта для фильтрации (может быть null)
     * @return список продуктов
     */
    List<Product> listProducts(String title);

    /**
     * Сохраняет продукт с прикрепленными изображениями
     * @param product продукт для сохранения
     * @param file1   первое изображение
     * @param file2   второе изображение
     * @param file3   третье изображение
     * @throws IOException если возникает ошибка при работе с файлами
     */
    void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException;

    /**
     * Удаляет продукт по ID
     * @param id ID продукта для удаления
     */
    void deleteProduct(Long id);

    /**
     * Получает продукт по ID
     * @param id ID продукта
     * @return найденный продукт или null, если не найден
     */
    Product getProductById(Long id);
}
