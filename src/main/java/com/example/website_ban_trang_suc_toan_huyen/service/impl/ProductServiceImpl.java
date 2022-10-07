package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductSizeDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
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

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductRequest productRequest) {
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);
        ProductSizeEntity productSize = modelMapper.map(productRequest, ProductSizeEntity.class);
        ProductEntity productByName = productRepository.findByNameProduct(productRequest.getNameProduct());
        if (ObjectUtils.isEmpty(productByName)) {
            // Không có tên  => add Product
            ProductDto productDto= modelMapper.map(productRepository.save(product), ProductDto.class);
            ProductEntity productById = productRepository.findById(productDto.getProductId()).get();
            productSize.setProductId(productDto.getProductId());
            productSize.setSizId(productRequest.getSizeId());
            productSize.setQuantity(productRequest.getQuantity());
            // Add ProductSize
            modelMapper.map(productSizeRepository.save(productSize), ProductSizeDto.class);
            return productDto;
        }
        else {
            // Nếu tên tồn tại => Check sản phẩm và size có tồn tại trong ProductSize không
            ProductSizeEntity productSizeOpt = productSizeRepository.findByProductIdAndSizId(productByName.getProductId(),productRequest.getSizeId());
            if (!ObjectUtils.isEmpty(productSizeOpt)) {
                throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductSize is exits");
            }
//            UserDetailsImpl currentUser = new UserDetailsImpl();
//                = CurrentUserUtils.getCurrentUserDetails();
            productSize.setProductId(productByName.getProductId());
            productSize.setSizId(productRequest.getSizeId());
            productSize.setQuantity(productRequest.getQuantity());
            // Add ProductSize
            modelMapper.map(productSizeRepository.save(productSize), ProductSizeDto.class);
            return modelMapper.map(productByName, ProductDto.class);
        }
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductRequest productRequest) {
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);
        ProductSizeEntity productSize = modelMapper.map(productRequest, ProductSizeEntity.class);
        ProductEntity productOpt = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Product not found")
        );
        ProductSizeEntity productSizeOpt = productSizeRepository.findByProductIdAndSizId(id,productRequest.getSizeId());
        productSize.setIdProductSize(productSizeOpt.getIdProductSize());
        productSize.setProductId(id);
        productSize.setSizId(productRequest.getSizeId());
        productSize.setQuantity(productRequest.getQuantity());
        // Add ProductSize
        productSizeRepository.save(productSize);
        product.setLastModifiedAt(new Date(System.currentTimeMillis()));
        product.setProductId(id);
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }
    @Override
    public ProductDto getProductById(Integer productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found")
        );
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public HttpStatus  deleteProduct(Integer productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found")
        );
        product.setStatus("Inactive");
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
}
