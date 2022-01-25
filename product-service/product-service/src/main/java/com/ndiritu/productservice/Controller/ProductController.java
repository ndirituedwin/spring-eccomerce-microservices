package com.ndiritu.productservice.Controller;

import com.ndiritu.productservice.Dto.ProductDto;
import com.ndiritu.productservice.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("findall")
    public ResponseEntity<?> findall(){

    return ResponseEntity.ok(productService.findall());
}

    @PostMapping("/save")
    public ResponseEntity<?> saveproduct(@Valid @RequestBody ProductDto productDto, BindingResult result){
        if (result.hasErrors()){
            Map<String,String> errormap=new HashMap<>();
            for (FieldError error:result.getFieldErrors()){
                errormap.put(error.getField(),error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String,String>>(errormap,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productService.save(productDto),HttpStatus.CREATED);
    }
}
