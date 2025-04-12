package com.sampleurban.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SupplierService {

    @Autowired //dependacy injection
    private EntityManager entityManager;

//    Register - working
    @Transactional
    public void addSupplier(String supplierName, String email, String phoneNumber, String address, String pass, String supplierType) {
        // Call the stored procedure without the supplierId
        Query query = entityManager.createNativeQuery("{ call add_new_sup( ?, ?, ?, ?, ?, ?) }");
        query.setParameter(1, supplierName);
        query.setParameter(2, email);
        query.setParameter(3, phoneNumber);
        query.setParameter(4, address);
        query.setParameter(5, pass);
        query.setParameter(6, supplierType);  // Pass the supplierType here

        query.executeUpdate();
    }

//    Login - In Progress
    @Transactional
    public boolean loginSupplier(String email, String password) {
        Query query = entityManager.createNativeQuery("SELECT login_supp(?, ?) FROM dual");
        query.setParameter(1, email);
        query.setParameter(2, password);

        Object result = query.getSingleResult();

        // Convert the result to boolean (Oracle returns 1 for TRUE, 0 for FALSE)
        return result != null && (result.equals(BigDecimal.ONE) || result.equals(1));
    }

//    Update - Completed
    @Transactional
    public void updateSupplier(String supplierId, String supplierName, String email, String phoneNumber,
                               String address, String pass, String supplierType) {
        Query query = entityManager.createNativeQuery("{ call update_supplier(?, ?, ?, ?, ?, ?, ?) }");
        query.setParameter(1, supplierId);
        query.setParameter(2, supplierName);
        query.setParameter(3, email);
        query.setParameter(4, phoneNumber);
        query.setParameter(5, address);
        query.setParameter(6, pass);
        query.setParameter(7, supplierType);

        query.executeUpdate();
    }

}
