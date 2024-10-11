package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.exceptions.ProductDoesNotExists;
import com.sudheer.productcatalogservice.dtos.UserDto;
import com.sudheer.productcatalogservice.exceptions.UserDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserNotAnAdmin;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.models.Role;
import com.sudheer.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
//@Primary
public class ProductStorageService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getProductByUserScope(Long productid, Long userid) throws ProductDoesNotExists, UserDoesNotExists, UserNotAnAdmin {

        UserDto userDto = restTemplate.getForEntity("http://userservice/user/{userId}", UserDto.class, userid).getBody();

       // UserDto userDto  = restTemplate.getForEntity("http://userservice/user/{userId}", UserDto.class,userId).getBody();


        if(userDto != null){
            Set<Role> roles = userDto.getRoles();
            for(Role role : roles){
                if(role.getRoleName().equals("admin")){
                    Optional<Product> product = productRepository.findById(productid);
                    if(product.isPresent()){
                        return product.get();
                    }
                    throw new ProductDoesNotExists("Product with id " + productid + " does not exist");
                }
            }
            throw new UserNotAnAdmin("User with id " + userid + " not an admin");
        }

        throw new UserDoesNotExists("User with id " + userid + " does not exist");

    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            Product productInDB = optionalProduct.get();
            productInDB.setName(product.getName());
            productInDB.setPrice(product.getPrice());
            productInDB.setDescription(product.getDescription());
            if(product.getCategory() != null){
                productInDB.setCategory(product.getCategory());
            }
            productInDB.setDescription(product.getDescription());
            productInDB.setImageUrl(product.getImageUrl());
            productInDB.setStatus(product.getStatus());
            return productRepository.save(productInDB);
        }
        return null;
    }
}
