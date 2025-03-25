package com.alanensina.orderservice.services;

import com.alanensina.basedomains.dto.product.*;
import com.alanensina.basedomains.exceptions.ProductErrorException;
import com.alanensina.orderservice.domains.Product;
import com.alanensina.orderservice.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<ProductQuantityResponseDTO> getAvailableProductList(){
        try{
            List<Product> products = productRepository.findByAvailableTrue();

            return
                    products.stream().map(
                            product -> new ProductQuantityResponseDTO(
                                    product.getProductId(),
                                    product.getName(),
                                    product.getStock())
                    ).toList();
        } catch (Exception e) {
            String errorMessage = "Error to get available products. Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ProductQuantityResponseDTO>> getAvailableProducts() {
        return ResponseEntity.ok(getAvailableProductList());
    }

    public ResponseEntity<ProductDTO> updateStock(UpdateProductStockByOrderDTO dto) {
        Product product = updateStock(dto.productId(), dto.quantity());
        return ResponseEntity.ok(new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.isAvailable(),
                product.getStock())
        );
    }

    public Product updateStock(UUID productId, int quantity){

        Optional<Product> opt = productRepository.findById(productId);

        if(opt.isEmpty()){
            String errorMessage = "Product not found. productId: " + productId;
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Product product = opt.get();

        if(product.getStock() == 0 || (product.getStock() - quantity) < 0){
            String errorMessage = "Insuficient stock. productId: " + productId + ", Stock: " + product.getStock();
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        product.setStock(product.getStock() - quantity);

        if(product.getStock() == 0){
            product.setAvailable(false);
        }

        try{
            return productRepository.save(product);
        } catch (Exception e) {
            String errorMessage = "Error to update stock. productId: " + productId + ", Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new ProductErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
