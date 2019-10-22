package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.*;
import edu.mum.mercato.repository.ProductItemRepository;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        //temp filter
        return products.stream().filter(product -> product.getCopiesCount() != 0 && product.getProductItems().stream()
                .anyMatch(item->item.getOrder()== null))
                .collect(Collectors.toList());
    }


    @Override
    public Product saveProduct(Product product, int copies) {
        for(int i=0; i<copies; i++){
            product.getProductItems().add(new ProductItem(product));
        }
        return productRepository.save(product);


    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public ProductItem saveItem(ProductItem p) {
        return productItemRepository.save(p);
    }

    @Override
    public List<ProductItem> findProductItems(Long id) {
        return productItemRepository.findByProductIdAndOrderIsNull(id);
    }

    @Override
    public int getProductsInCartCount(Long id) {
        return productItemRepository.findAllByOrderId(id).size();
    }


}
