package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.*;
import edu.mum.mercato.repository.ProductItemRepository;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }


    @Override
    public Product saveProduct(Product product, int copies) {
        for(int i=0; i<copies; i++){
            product.getProductItems().add(new ProductItem(product));
        }
        return productRepository.save(product);


    }



}
