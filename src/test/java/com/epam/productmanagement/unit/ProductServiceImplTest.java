package com.epam.productmanagement.unit;

import com.epam.productmanagement.api.dto.ProductDTO;
import com.epam.productmanagement.api.mapper.ProductMapper;
import com.epam.productmanagement.client.CurrencyExchanger;
import com.epam.productmanagement.dao.CategoryRepository;
import com.epam.productmanagement.dao.ProductRepository;
import com.epam.productmanagement.model.Category;
import com.epam.productmanagement.model.Product;
import com.epam.productmanagement.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static com.epam.productmanagement.ModelUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CurrencyExchanger currencyExchanger;

    private Product product;

    @BeforeEach
    void beforeEach() {
        product = createProduct();
        Category category = createCategory();
        category.addProduct(product);
    }

    @Test
    void getAll() {
        when(productRepository.findAllByCategoryId(anyLong())).thenReturn(List.of(product));
        when(productMapper.productToProductDTOList(List.of(product))).thenReturn(List.of(createProductDTO()));

        List<ProductDTO> result = productService.getAll(CATEGORY_ID);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    void getProduct() {
        when(productRepository.findByIdAndCategoryId(anyLong(), anyLong())).thenReturn(product);
        when(productMapper.productToProductDTO(product)).thenReturn(createProductDTO());

        ProductDTO result = productService.getProduct(PRODUCT_ID, CATEGORY_ID);

        assertThat(result.getId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(result.getCategoryName()).isEqualTo(CATEGORY_NAME);
    }

    @Test
    void saveProductWithUSDCurrency() {
        ProductDTO productDTO = createProductDTO();
        productDTO.setCurrency("USD");

        when(currencyExchanger.convert(PRODUCT_PRICE, Currency.getInstance("USD"), Currency.getInstance("EUR")))
                .thenReturn(BigDecimal.valueOf(8.24));
        when(productMapper.productDTOtoProduct(productDTO)).thenReturn(product);
        when(categoryRepository.getOne(anyLong())).thenReturn(product.getCategory());
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.saveProduct(productDTO, CATEGORY_ID);

        assertThat(result.getId()).isEqualTo(productDTO.getId());
        assertThat(result.getCurrency()).isEqualTo("EUR");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(8.24));
    }

    @Test
    void removeProduct() {
        doNothing().when(productRepository).deleteById(anyLong());

        productService.removeProduct(PRODUCT_ID);

        verify(productRepository).deleteById(PRODUCT_ID);
    }
}