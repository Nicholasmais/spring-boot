package com.programmingtechie.productservice.controller;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        this.productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
        return this.productService.getAllProducts();
    }

    @GetMapping("/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductByID(@PathVariable String productID){
        return this.productService.getProductByID(productID);
    }

    @PutMapping("/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> putProductByID(@PathVariable String productID, @RequestBody ProductRequest productRequest){
        return this.productService.putProductByID(productID, productRequest);
    }

    @DeleteMapping("/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> deleteProductByID(@PathVariable String productID){
        return this.productService.deleteProductByID(productID);
    }
}
