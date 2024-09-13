package com.Product.testProduct.Service;

import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.Map;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
 
	public Product patchProduct(Long id, Map<String, Object> updates) {
    Product product = productRepository.findById(id).orElseThrow();
    
    updates.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(Product.class, key);
        if (field != null) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, product, value);
        }
    });
    
    return productRepository.save(product);
}

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}