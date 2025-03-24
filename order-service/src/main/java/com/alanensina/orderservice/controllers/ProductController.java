package com.alanensina.orderservice.controllers;

import com.alanensina.basedomains.dto.product.ProductCreateRequestDTO;
import com.alanensina.basedomains.dto.product.ProductCreateResponseDTO;
import com.alanensina.basedomains.dto.product.ProductDTO;
import com.alanensina.basedomains.dto.product.ProductQuantityResponseDTO;
import com.alanensina.orderservice.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponseDTO> create(@RequestBody ProductCreateRequestDTO dto){
        return productService.create(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list(){
        return productService.getAll();
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProductQuantityResponseDTO>> getAvailableProducts(){
        return productService.getAvailableProducts();
    }
}