package com.alanensina.productstock.services;

import com.alanensina.productstock.domain.Product;
import com.alanensina.productstock.dtos.ProductResponseDTO;
import com.alanensina.productstock.dtos.ProductSaveRequestDTO;
import com.alanensina.productstock.dtos.ProductUpdateRequestDTO;
import com.alanensina.productstock.exceptions.ProductNotFoundException;
import com.alanensina.productstock.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<ProductResponseDTO> save(@Valid ProductSaveRequestDTO dto) {
        var newProduct = new Product();
        newProduct.setName(dto.name());
        newProduct.setAvailable(dto.available());
        newProduct.setStock(dto.stock());
        newProduct.setUnitPrice(dto.unitPrice());

        try{
            newProduct = productRepository.save(newProduct);
            return ResponseEntity.ok(ProductResponseDTO.from(newProduct));
        } catch (Exception e) {
            throw new ProductNotFoundException("Error to save product in database: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ProductResponseDTO>> list() {
        try{
            return ResponseEntity.ok(ProductResponseDTO.from(productRepository.findAll()));
        } catch (Exception e) {
            throw new ProductNotFoundException("Error to list products in database: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProductResponseDTO> findById(UUID productId) {
        try{
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found. ID: " + productId,
                            HttpStatus.BAD_REQUEST));

            return ResponseEntity.ok(ProductResponseDTO.from(product));
        } catch (Exception e) {
            throw new ProductNotFoundException("Error to find product in database: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProductResponseDTO> deleteById(UUID productId) {
        try{
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found. ID: " + productId,
                            HttpStatus.BAD_REQUEST));

            productRepository.delete(product);

            return ResponseEntity.ok(ProductResponseDTO.from(product));
        } catch (Exception e) {
            throw new ProductNotFoundException("Error to delete product in database: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProductResponseDTO> update(ProductUpdateRequestDTO dto) {
        try{
            // Check if the product exists
            var oldProduct = productRepository.findById(dto.id())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found. ID: " + dto.id(),
                            HttpStatus.BAD_REQUEST));

            // Update only the fields that came from request
            var updatedProduct = new Product();
            updatedProduct.setId(dto.id());
            updatedProduct.setName(dto.name() != null ? dto.name() : oldProduct.getName());
            updatedProduct.setAvailable(dto.available() != null ? dto.available() : oldProduct.isAvailable());
            updatedProduct.setStock(dto.stock() != null ? dto.stock() : oldProduct.getStock());
            updatedProduct.setUnitPrice(dto.unitPrice() != null ? dto.unitPrice() : oldProduct.getUnitPrice());


            updatedProduct = productRepository.save(updatedProduct);
            return ResponseEntity.ok(ProductResponseDTO.from(updatedProduct));
        } catch (Exception e) {
            throw new ProductNotFoundException("Error to update product in database: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
