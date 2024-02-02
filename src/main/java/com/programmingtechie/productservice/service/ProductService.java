package com.programmingtechie.productservice.service;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getName());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = this.productRepository.findAll();

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .name(product.getName())
                        .build())
                .toList();
    }

    public ProductResponse getProductByID(String id){
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            return null;
        }
        Product product = optionalProduct.get();

        return ProductResponse.builder()
                .price(product.getPrice())
                .description(product.getDescription())
                .name(product.getName())
                .id(product.getId())
                .build();
    }

    public ResponseEntity<Product> putProductByID(String id, ProductRequest productRequest){
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            product.setDescription(productRequest.getDescription());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());

            this.productRepository.save(product);
            return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Product> deleteProductByID(String productID){
        Optional<Product> productOptional = this.productRepository.findById(productID);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            this.productRepository.delete(product);
            return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
