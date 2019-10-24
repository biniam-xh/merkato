package edu.mum.mercato.service;


import edu.mum.mercato.config.productEvents.ProductAddEvent;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product, int copies);
    Product findById(Long id);
    ProductItem saveItem(ProductItem p);
    List<ProductItem> findProductItems(Long id);

    Product saveProduct(Product product) throws IOException;
    Product getProductById(long id);
    void deleteProductById(long id);
    void deleteProduct(Product product);

    int getProductsInCartCount(Long id);
    List<Product> getUnApprovedProducts();


    List<ProductItem> getSellerProductItems(Long id);

    Product getByProductByTitleAndCategory(String title, String categoryName);

    void notify(ProductAddEvent addEvent, String username);


}
