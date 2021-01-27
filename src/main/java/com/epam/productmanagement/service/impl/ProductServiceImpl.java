package com.epam.productmanagement.service.impl;

import com.epam.productmanagement.api.dto.ProductDTO;
import com.epam.productmanagement.api.mapper.ProductMapper;
import com.epam.productmanagement.client.CurrencyExchanger;
import com.epam.productmanagement.dao.CategoryRepository;
import com.epam.productmanagement.dao.ProductRepository;
import com.epam.productmanagement.model.Product;
import com.epam.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CurrencyExchanger currencyExchanger;

    @Override
    public List<ProductDTO> getAll(Long categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        log.info("Fetching all products with categoryId = {}. Found {} records.", categoryId, products.size());
        return productMapper.productToProductDTOList(products);
    }

    @Override
    public ProductDTO getProduct(Long id, Long categoryId) {
        Product product = productRepository.findByIdAndCategoryId(id, categoryId);
        log.info("Found product with id = {}: {}", id, product);
        return productMapper.productToProductDTO(product);
    }

    @Transactional
    @Override
    public ProductDTO saveProduct(ProductDTO productDTO, Long categoryId) {
        if (productDTO.getCurrency() != null &&
                !productDTO.getCurrency().equals("EUR")) {
            BigDecimal convertedPrice = currencyExchanger.convert(productDTO.getPrice(),
                    Currency.getInstance(productDTO.getCurrency()),
                    Currency.getInstance("EUR"));
            productDTO.setPrice(convertedPrice);
        }
        Product product = productMapper.productDTOtoProduct(productDTO);
        product.setCategory(categoryRepository.getOne(categoryId));
        log.info("Saving product: {}", product);
        product = productRepository.save(product);
        ProductDTO result = productMapper.productToProductDTO(product);
        result.setCurrency("EUR");
        return result;
    }

    @Override
    public void removeProduct(Long id) {
        log.info("Removing product with id = {}", id);
        productRepository.deleteById(id);
    }
}
