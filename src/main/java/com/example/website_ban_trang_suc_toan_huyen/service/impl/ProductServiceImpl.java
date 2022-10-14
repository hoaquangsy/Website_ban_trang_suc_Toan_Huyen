package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductSizeDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.CategoryRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.MaterialRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductSizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MaterialRepository materialRepository;


    @Override
    public ProductDto createProduct(ProductRequest productRequest) {
        validate(productRequest);
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);
        ProductSizeEntity productSize = modelMapper.map(productRequest, ProductSizeEntity.class);
            // lưu product
            ProductDto productDto = modelMapper.map(productRepository.save(product), ProductDto.class);
            productSize.setProductId(productDto.getProductId());
            productSize.setSizId(productRequest.getSizeId());
            productSize.setQuantity(productRequest.getQuantity());
            // lưu productSize
            modelMapper.map(productSizeRepository.save(productSize), ProductSizeDto.class);
            return productDto;

//        }
    }

    @Override
    public ProductDto updateProduct(UUID id, ProductRequest productRequest) {
        validate(productRequest);
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);
        ProductSizeEntity productSize = modelMapper.map(productRequest, ProductSizeEntity.class);
        ProductEntity productOpt = productRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Product not found"));
        ProductSizeEntity productSizeOpt = productSizeRepository.findByProductIdAndSizId(id, productRequest.getSizeId());
        productSize.setIdProductSize(productSizeOpt.getIdProductSize());
        productSize.setProductId(id);
        productSize.setSizId(productRequest.getSizeId());
        productSize.setQuantity(productRequest.getQuantity());
        // update ProductSize
        productSizeRepository.save(productSize);
        product.setProductId(id);
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public HttpStatus deleteProduct(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
        product.setStatus(ProductEntity.StatusEnum.INACTIVE.toString());
        productRepository.save(product);
        return HttpStatus.OK;
    }

    @Override
    public Page<ProductDto> getAllProduct(int page, int pageSize) {
        Page<ProductEntity> productEntityPage = productRepository.findAll(PageRequest.of(page, pageSize));
        if (productEntityPage.getTotalElements() > 0) {
            return productEntityPage.map(productEntity -> modelMapper.map(productEntity, ProductDto.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Products not exist");
    }

    private void validate(ProductRequest productRequest) {
        // kiểm tra category tồn tại
        if (!categoryRepository.existsById(productRequest.getCategoryId())) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Category not exits");
        }
        // kiểm tra size tồn tại
        if (!productSizeRepository.existsById(productRequest.getSizeId())) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Size not exits");
        }
        // kiểm tra tên product tồn tại
        if (!ObjectUtils.isEmpty(productRepository.findByNameProduct(productRequest.getNameProduct()))){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Product is exits");
        }
        // kiểm tra material tồn tại
        if (!materialRepository.existsById(productRequest.getMaterialId())){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Material not exits");
        }
        // chưa kiểm tra eventID
    }
}
