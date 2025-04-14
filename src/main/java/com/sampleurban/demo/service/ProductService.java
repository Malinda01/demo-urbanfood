package com.sampleurban.demo.service;

import com.sampleurban.demo.DTO.ProductRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void addProduct(String id, String name, String category, String type, String description, String imageUrl) {
        var query = entityManager.createNativeQuery("{ call add_product(?, ?, ?, ?, ?, ?) }");
        query.setParameter(1, id);
        query.setParameter(2, name);
        query.setParameter(3, category);
        query.setParameter(4, type);
        query.setParameter(5, description);
        query.setParameter(6, imageUrl);
        query.executeUpdate();
    }

    @Transactional
    public void updateProduct(String id, String name, String category, String type, String description, String imageUrl) {
        var query = entityManager.createNativeQuery("{ call update_product(?, ?, ?, ?, ?, ?) }");
        query.setParameter(1, id);
        query.setParameter(2, name);
        query.setParameter(3, category);
        query.setParameter(4, type);
        query.setParameter(5, description);
        query.setParameter(6, imageUrl);
        query.executeUpdate();
    }

    @Transactional
    public void deleteProduct(String id) {
        var query = entityManager.createNativeQuery("{ call delete_product(?) }");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Transactional
    public List<ProductRequest> getAllProducts() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("get_products")
                .registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> resultList = query.getResultList();
        List<ProductRequest> products = new ArrayList<>();

        for (Object[] row : resultList) {
            ProductRequest product = new ProductRequest();
            product.setProductId((String) row[0]);
            product.setProductName((String) row[1]);
            product.setCategory((String) row[2]);
            product.setType((String) row[3]);
            product.setDescription((String) row[4]);
            product.setImageUrl((String) row[5]);
            products.add(product);
        }

        return products;
    }
}
