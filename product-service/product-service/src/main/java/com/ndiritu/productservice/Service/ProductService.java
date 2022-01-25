package com.ndiritu.productservice.Service;

import com.ndiritu.productservice.Dto.ProductDto;
import com.ndiritu.productservice.Model.Product;
import com.ndiritu.productservice.Repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {

private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    public List<Product> findall(){
         try {
             List<Product> findall=productRepo.findAll();
             return findall;
         }catch (Exception e){
             throw new RuntimeException("An exception occurred while trying to fetch products "+e.getMessage());
         }
    }
    public ProductDto save(ProductDto productDto){
         try {
             Product saveproduct=new Product();
             saveproduct.setName(productDto.getName());
             saveproduct.setDescription(productDto.getDescription());
             saveproduct.setPrice(productDto.getPrice());
             saveproduct.setCreatedAt(Instant.now());
              productRepo.save(saveproduct);
              return ProductDto.builder()
                      .name(saveproduct.getName())
                      .description(saveproduct.getDescription())
                      .price(saveproduct.getPrice())
                      .createdAt(saveproduct.getCreatedAt())
                      .message("Product created successfully")
                      .build();

         }catch (Exception e){
             throw new RuntimeException("An exception occurred while saving product "+e.getMessage());
         }
    }
}
