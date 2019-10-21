package edu.mum.mercato.service;


import edu.mum.mercato.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product, int copies);


}
