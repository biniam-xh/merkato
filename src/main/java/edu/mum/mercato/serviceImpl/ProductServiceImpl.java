package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.*;
import edu.mum.mercato.repository.CategoryRepository;
import edu.mum.mercato.repository.ProductImageRepository;
import edu.mum.mercato.repository.ProductItemRepository;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

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
    public Product saveProduct(Product product) throws IOException {


        for(int i=0; i < product.getNumberOfCopies(); i++){

            ProductItem productItem = new ProductItem();
            product.getProductItems().add(productItem);
            productItem.setProduct(product);
        }
        Category newCategory = new Category();
        newCategory = product.getCategory();
//        newCategory.setCategoryName(product.getProductCategoryName());
//        product.setCategory(newCategory);
        newCategory.setProductList(product);
        Category category = categoryRepository.findByCategoryName(newCategory.getCategoryName());
        if (category != null){
            newCategory.setId(category.getId());
        }
        categoryRepository.save(newCategory);

//        ProductImage productImage = new ProductImage();
//
//        if (product.getImageData() != null) {
//            byte[] image = null;
//            try {
//                image = product.getImageData().getBytes();
//            } catch (IOException e) {
//            }
//            if (image != null && image.length > 0) {
//                productImage.setImage(image);
//                product.setImages(productImage);
//                productImage.setProduct(product);
//                productImageRepository.save(productImage);
//
//            }
//        }
        MultipartFile productImage= product.getImageData();
        if(productImage != null && !productImage.isEmpty()) {
            try{

//                String url = "C:\\restImages\\" + productImage.getOriginalFilename();
                String dest = "C:\\Users\\user\\Desktop\\WAA\\October 2019\\Project\\mercato\\src\\main\\resources\\static\\images\\products\\"
                + productImage.getOriginalFilename();
                String url = "images\\products\\" + productImage.getOriginalFilename();

                productImage.transferTo(new File(dest));

                ProductImage productImageOb = new ProductImage();
                productImageOb.setImageURL(url);
                product.setImages(productImageOb);
                productImageOb.setProduct(product);
                productImageRepository.save(productImageOb);

            } catch(Exception e) {
//                throw new RuntimeException("Product Image saving failed", e);
            }
        }

        return productRepository.save(product);
    }

    public Product getProductById(long id){
        return productRepository.findById(id).get();
    }

    public void deleteProductById(long id){
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }

    public void deleteProduct(Product product){
        productRepository.delete(product);
    }

    @Override
    public int getProductsInCartCount(Long id) {
        return productItemRepository.findAllByOrderId(id).size();
    }

    @Override
    public List<ProductItem> getSellerProductItems(Long id) {
        return productItemRepository.findAllByProduct_SellerId(id);
    }

    @Override
    public List<ProductItem> findProductItems(Long id) {
        return productItemRepository.findByProductIdAndOrderIsNull(id);
    }
    @Override
    public ProductItem saveItem(ProductItem p) {
        return productItemRepository.save(p);
    }


    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product saveProduct(Product product, int copies) {
        for(int i=0; i<copies; i++){
            product.getProductItems().add(new ProductItem(product));
        }
        return productRepository.save(product);


    }

    public Product getByProductByTitleAndCategory(String title, String categoryName){
        return productRepository.findByTitleAndCategory_CategoryName(title, categoryName);
    }



}
