package edu.mum.mercato.repository.seed;

import edu.mum.mercato.domain.*;
import edu.mum.mercato.repository.CategoryRepository;
import edu.mum.mercato.repository.ProductItemRepository;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.repository.UserRepository;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedProducts();
    }

    public void seedProducts(){
        List<String> images = Arrays.asList("http://www.momax.net/wp-content/uploads/2016/11/w_cable_1400px_flatten_05.jpg",
                "http://www.momax.net/wp-content/uploads/2018/03/products_img_DL12_cover-5.jpg");
        Product p1 = new Product("Ankle Power Line1", "Lightning Cable", 10.00, images);
        Product p2 = new Product("Ankle Power Line2", "Lightning Cable", 15.00, images);
        Product p3 = new Product("Ankle Power Line3", "Lightning Cable", 20.00, images);

        Category category = new Category();
        category.setCategoryName("Power Cable");
        category.setProductList(p1);
        category.setProductList(p2);
        category.setProductList(p3);
        p1.setCategory(category);
        p2.setCategory(category);
        p3.setCategory(category);
        category = categoryRepository.save(category);

        User seller = new User();
        seller.setId(999);
        seller.setFirstName("BBB");
        seller = userRepository.save(seller);

        p1.setSeller(seller);
        p2.setSeller(seller);
        p3.setSeller(seller);

        for(int i=0; i<10; i++){
            p1.getProductItems().add(new ProductItem(p1));
        }
        for(int i=0; i<5; i++){
            p2.getProductItems().add(new ProductItem(p2));
        }
        for(int i=0; i<5; i++){
            p3.getProductItems().add(new ProductItem(p3));
        }

        p1.setNumberOfCopies(p1.getCopiesCount());
        p2.setNumberOfCopies(p2.getCopiesCount());
        p3.setNumberOfCopies(p3.getCopiesCount());
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);



    }
}
