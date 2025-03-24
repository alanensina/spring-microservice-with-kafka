package com.alanensina.stockservice.services;

import com.alanensina.basedomains.dto.product.ProductCreateRequestDTO;
import com.alanensina.basedomains.dto.product.ProductCreateResponseDTO;
import com.alanensina.basedomains.dto.product.ProductDTO;
import com.alanensina.basedomains.dto.product.ProductQuantityResponseDTO;
import com.alanensina.basedomains.exceptions.ProductErrorException;
import com.alanensina.stockservice.domains.Product;
import com.alanensina.stockservice.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<ProductDTO>> getAll() {
        try {
            List<Product> products = productRepository.findAll();
            return ResponseEntity.ok(
                    products.stream().map(
                            p -> new ProductDTO(
                                    p.getProductId(),
                                    p.getName(),
                                    p.getPrice(),
                                    p.isAvailable(),
                                    p.getStock()
                            )
                    ).toList()
            );
        } catch (Exception e) {
            String errorMessage = "Error to get all products. Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProductCreateResponseDTO> create(ProductCreateRequestDTO dto) {

        Product newProduct = new Product();
        newProduct.setName(dto.name());
        newProduct.setPrice(dto.price());
        newProduct.setAvailable(dto.available());
        newProduct.setStock(dto.stock());

        try{
            newProduct = productRepository.save(newProduct);

            return ResponseEntity.ok(new ProductCreateResponseDTO(
                    newProduct.getProductId(),
                    newProduct.getName(),
                    newProduct.getPrice(),
                    newProduct.isAvailable(),
                    newProduct.getStock()
            ));
        } catch (Exception e) {
            String errorMessage = "Error to save a product: " + newProduct + ". Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ProductQuantityResponseDTO>> getAvailableProducts() {

        try{
            List<Product> products = productRepository.findByAvailableTrue();

            return ResponseEntity.ok(
                    products.stream().map(
                            product -> new ProductQuantityResponseDTO(
                                    product.getProductId(),
                                    product.getName(),
                                    product.getStock())
                    ).toList()
            );
        } catch (Exception e) {
            String errorMessage = "Error to get available products. Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
