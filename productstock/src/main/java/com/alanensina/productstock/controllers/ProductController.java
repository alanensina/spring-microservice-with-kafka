package com.alanensina.productstock.controllers;

import com.alanensina.productstock.dtos.ProductResponseDTO;
import com.alanensina.productstock.dtos.ProductSaveRequestDTO;
import com.alanensina.productstock.dtos.ProductUpdateRequestDTO;
import com.alanensina.productstock.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@RequestBody ProductSaveRequestDTO body){
        return productService.save(body);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductUpdateRequestDTO body){
        return productService.update(body);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> list(){
        return productService.list();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable UUID productId){
        return productService.findById(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> deleteById(@PathVariable UUID productId){
        return productService.deleteById(productId);
    }
}
