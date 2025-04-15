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
    public void addProduct(String name, double price, int quantity, String category,
                           String description, String imageUrl, String supplierId) {
        var query = entityManager.createNativeQuery("{ call add_product(?, ?, ?, ?, ?, ?, ?) }");
        query.setParameter(1, name); // no need to pass 'id', it will be auto-generated
        query.setParameter(2, price);
        query.setParameter(3, quantity);
        query.setParameter(4, category);
        query.setParameter(5, description);
        query.setParameter(6, imageUrl);
        query.setParameter(7, supplierId);
        query.executeUpdate();
    }


    @Transactional
    public void updateProduct(String id, String name, double price, int quantity, String category,
                              String description, String imageUrl, String supplierId) {
        var query = entityManager.createNativeQuery("{ call update_product(?, ?, ?, ?, ?, ?, ?, ?) }");
        query.setParameter(1, id);
        query.setParameter(2, name);
        query.setParameter(3, price);
        query.setParameter(4, quantity);
        query.setParameter(5, category);
        query.setParameter(6, description);
        query.setParameter(7, imageUrl);
        query.setParameter(8, supplierId);
        query.executeUpdate();
    }

    @Transactional
    public void deleteProduct(String id) {
        var query = entityManager.createNativeQuery("{ call delete_product(?) }");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Transactional
    public List<ProductRequest> getProductsBySupplier(String supplierId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("get_products_by_supplier")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);

        query.setParameter(1, supplierId);
        query.execute();

        List<Object[]> resultList = query.getResultList();
        List<ProductRequest> products = new ArrayList<>();

        for (Object[] row : resultList) {
            ProductRequest product = new ProductRequest();
            product.setProductId((String) row[0]);
            product.setname((String) row[1]);
            product.setPrice(((Number) row[2]).doubleValue());
            product.setQuantity(((Number) row[3]).intValue());
            product.setCategory((String) row[4]);
            product.setDescription((String) row[5]);
            product.setImageUrl((String) row[6]);
            product.setSupplierId((String) row[7]);
            products.add(product);
        }

        return products;
    }

}
