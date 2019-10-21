package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductImage;
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


    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

//    @Override
//    public Product save(Product product) {
//        for(ProductImage image : product.getImages());
//    }
}
