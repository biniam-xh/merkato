package edu.mum.mercato.service;


import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product, int copies);
    Product findById(Long id);
    ProductItem saveItem(ProductItem p);
    List<ProductItem> findProductItems(Long id);


}
