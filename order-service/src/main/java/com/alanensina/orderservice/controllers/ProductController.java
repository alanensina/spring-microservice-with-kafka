package com.alanensina.orderservice.controllers;

import com.alanensina.basedomains.dto.product.*;
import com.alanensina.orderservice.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alanensina.basedomains.dto.product.ProductDTO;

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

    @PutMapping("/update-stock")
    public ResponseEntity<ProductDTO> updateStock(@RequestBody UpdateProductStockByOrderDTO dto){
        return productService.updateStock(dto);
    }
}