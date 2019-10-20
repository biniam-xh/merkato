package edu.mum.mercato.repository.seed;

import edu.mum.mercato.domain.Product;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedProducts();
    }

    public void seedProducts(){
        Product p1 = new Product("Ankle Power Line", "Lightning Cable", 10.00
                , "https://multimedia.bbycastatic.ca/multimedia/products/500x500/106/10653/10653762.jpg");
        Product p2 = new Product("Ankle Power Line", "Lightning Cable", 15.00
                , "https://multimedia.bbycastatic.ca/multimedia/products/500x500/106/10653/10653762.jpg");
        Product p3 = new Product("Ankle Power Line", "Lightning Cable", 20.00
                , "https://multimedia.bbycastatic.ca/multimedia/products/500x500/106/10653/10653762.jpg");

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);

    }
}
