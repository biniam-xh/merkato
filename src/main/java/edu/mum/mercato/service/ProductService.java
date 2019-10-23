package edu.mum.mercato.service;


import edu.mum.mercato.domain.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product) throws IOException;
    Product getProductById(long id);
    void deleteProductById(long id);
    void deleteProduct(Product product);


    int getProductsInCartCount(Long id);


}
