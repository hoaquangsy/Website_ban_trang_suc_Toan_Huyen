package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.ProductDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.*;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.*;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.*;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ProductPropertyRepository productPropertyRepository;


    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private HttpSession session;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public ProductDto createProduct(ProductRequest productRequest) {
//        validate(productRequest);
        //Luu Product
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);
        product.setProductId(UUID.randomUUID());
        product.setDeleted(Boolean.FALSE);
        product.setStatus(productRequest.getStatus());
        String code = LocalDate.now().toString().replaceAll("-","");
        product.setCode("PRODUCT"+code+ RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT));
        this.productRepository.save(product);

        //Luu Product Size
        List<ProductSizeEntity> productSizeEntities = productRequest.getSizeProducts().stream()
                .map(sizeProduct -> this.modelMapper.map(sizeProduct, ProductSizeEntity.class)).collect(Collectors.toList());
        productSizeEntities.forEach(productSizeEntity -> {
            productSizeEntity.setIdProductSize(UUID.randomUUID());
            productSizeEntity.setProductId(product.getProductId());
            productSizeEntity.setDeleted(Boolean.FALSE);

        });
        this.productSizeRepository.saveAll(productSizeEntities);


        //Luu Product Property
        if(!CollectionUtils.isEmpty(productRequest.getProductProperties())){
            List<ProductPropertyEntity> productPropertyEntities = productRequest.getProductProperties().stream()
                    .map(productProperty -> this.modelMapper.map(productProperty, ProductPropertyEntity.class)).collect(Collectors.toList());
            productPropertyEntities.forEach(productPropertyEntity -> {
                productPropertyEntity.setProductPropertyId(UUID.randomUUID());
                productPropertyEntity.setProductId(product.getProductId());
                productPropertyEntity.setDeleted(Boolean.FALSE);
            });
            this.productPropertyRepository.saveAll(productPropertyEntities);
        }


        //Lưu Product Image

        List<ProductImageEntity> productImageEntities = productRequest.getImageUrls().stream()
                .map(s -> new ProductImageEntity(UUID.randomUUID(), s, product.getProductId(),Boolean.FALSE)).collect(Collectors.toList());

        this.productImageRepository.saveAll(productImageEntities);
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(UUID id, ProductRequest productRequest) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
        product.setNameProduct(productRequest.getNameProduct());
        product.setAccessoryId(productRequest.getAccessoryId());
        product.setCategoryId(productRequest.getCategoryId());
        product.setMaterialId(productRequest.getMaterialId());
        product.setNote(productRequest.getNote());
        product.setGender(productRequest.getGender());
        product.setEventId(productRequest.getEventId());
        product.setSalary(productRequest.getSalary());
        product.setVendorId(productRequest.getVendorId());
        this.productRepository.save(product);


        //Update Size Product
        List<ProductSizeEntity> productSize = this.productSizeRepository.findByProductId(id);
        if(productSize != null){
            this.productSizeRepository.deleteAll(productSize);
        }
        List<ProductSizeEntity> productSizeEntities = productRequest.getSizeProducts().stream()
                .map(sizeProduct -> this.modelMapper.map(sizeProduct, ProductSizeEntity.class)).collect(Collectors.toList());
        productSizeEntities.forEach(productSizeEntity -> {
            productSizeEntity.setIdProductSize(UUID.randomUUID());
            productSizeEntity.setProductId(product.getProductId());
            productSizeEntity.setDeleted(Boolean.FALSE);
        });
        this.productSizeRepository.saveAll(productSizeEntities);

        //Update Product Property
        List<ProductPropertyEntity> productPropertyDeleted = this.productPropertyRepository.findByProductId(id);
        if(productPropertyDeleted != null){
            this.productPropertyRepository.deleteAll(productPropertyDeleted);
        }
        List<ProductPropertyEntity> productPropertyEntities = productRequest.getProductProperties().stream()
                .map(productProperty -> this.modelMapper.map(productProperty, ProductPropertyEntity.class)).collect(Collectors.toList());
        productPropertyEntities.forEach(productPropertyEntity -> {
            productPropertyEntity.setProductPropertyId(UUID.randomUUID());
            productPropertyEntity.setProductId(product.getProductId());
            productPropertyEntity.setDeleted(Boolean.FALSE);
        });
        this.productPropertyRepository.saveAll(productPropertyEntities);

        //Lưu Product Image
        if(!CollectionUtils.isEmpty(productRequest.getImageUrls())){
            List<ProductImageEntity> productImageDeleted = this.productImageRepository.findByProductId(id);
            if(!CollectionUtils.isEmpty(productImageDeleted)){
                this.productImageRepository.deleteAll(productImageDeleted);
            }
            List<ProductImageEntity> productImageEntities = productRequest.getImageUrls().stream()
                    .map(s -> new ProductImageEntity(UUID.randomUUID(), s, product.getProductId(),Boolean.FALSE)).collect(Collectors.toList());
            this.productImageRepository.saveAll(productImageEntities);
        }

        return this.modelMapper.map(product, ProductDto.class);

    }

    @Override
    public ProductDto getProductById(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
       ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(this.modelMapper.map(this.categoryRepository.findId(productDto.getCategoryId()).orElse(new CategoryEntity()), CategoryDto.class));
        productDto.setAccessory(this.modelMapper.map(this.accessoryRepository.findById(productDto.getAccessoryId()).orElse(new AccessoryEntity()), AccessoryDTO.class));
        productDto.setMaterial(this.modelMapper.map(this.materialRepository.findByID(productDto.getMaterialId()).orElse(new MaterialEntity()),MaterialDto.class));
        productDto.setVendor(this.modelMapper.map(this.vendorRepository.findByID(productDto.getVendorId()).orElse(new VendorEntity()), VendorDto.class));
       if(!CollectionUtils.isEmpty( this.productImageRepository.findByProductId(productId))){
           List<ProductImageDTO> imageDTOList =  this.productImageRepository.findByProductId(productId).stream()
                   .map(productImage -> this.modelMapper.map(productImage,ProductImageDTO.class)).collect(Collectors.toList());
           productDto.setProductImages(imageDTOList);
       }
        if(!CollectionUtils.isEmpty( this.productPropertyRepository.findByProductId(productId))){
            List<ProductPropertyDto> productPropertyDtos =  this.productPropertyRepository.findByProductId(productId).stream()
                    .map(productProperty -> this.modelMapper.map(productProperty,ProductPropertyDto.class)).collect(Collectors.toList());
            productDto.setProductProperties(productPropertyDtos);
        }
        if(!CollectionUtils.isEmpty( this.productSizeRepository.findByProductId(productId))){
            List<ProductSizeDto> productSizeDtos =  this.productSizeRepository.findByProductId(productId).stream()
                    .map(productSize -> this.modelMapper.map(productSize,ProductSizeDto.class)).collect(Collectors.toList());
            productSizeDtos.forEach(productSizeDto -> {
                Optional<SizeEntity> sizeEntity =  this.sizeRepository.getSizeEntitiesBy(productSizeDto.getSizeId());
                sizeEntity.ifPresent(entity -> productSizeDto.setSize(entity.getSize()));
            });
            productSizeDtos =  productSizeDtos.stream().sorted(Comparator.comparing(ProductSizeDto::getSize)).collect(Collectors.toList());
            productDto.setProductSizes(productSizeDtos);
        }
        return productDto;
    }

    @Override
    public ProductDto lock(UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
        product.setStatus(ProductEntity.StatusEnum.INACTIVE);
        List<CartDetailEntity> cartDetailEntities = this.cartDetailRepository.findByProductId(product.getProductId());
        if(!CollectionUtils.isEmpty(cartDetailEntities)){
            this.cartDetailRepository.deleteAll(cartDetailEntities);
        }
        return this.modelMapper.map(this.productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto unlock(UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
        product.setStatus(ProductEntity.StatusEnum.ACTIVE);
        return this.modelMapper.map(this.productRepository.save(product), ProductDto.class);
    }

    @Override
    @Transactional
    public HttpStatus deleteProduct(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
        product.setDeleted(Boolean.TRUE);
        productRepository.save(product);
        List<ProductSizeEntity> sizeEntities  = this.productSizeRepository.findByProductId(productId);
        if(!CollectionUtils.isEmpty(sizeEntities)){
            sizeEntities.forEach(productSizeEntity -> productSizeEntity.setDeleted(Boolean.TRUE));
            this.productSizeRepository.saveAll(sizeEntities);
        }
        List<ProductPropertyEntity> productPropertyDeleted = this.productPropertyRepository.findByProductId(productId);
        if(!CollectionUtils.isEmpty(productPropertyDeleted)){
            productPropertyDeleted.forEach(productPropertyEntity -> productPropertyEntity.setDeleted(Boolean.TRUE));
            this.productPropertyRepository.saveAll(productPropertyDeleted);
        }
        List<ProductImageEntity> productImageDeleted = this.productImageRepository.findByProductId(productId);
        if(!CollectionUtils.isEmpty(productImageDeleted)){
            productImageDeleted.forEach(productImage -> productImage.setDeleted(Boolean.TRUE));
            this.productImageRepository.saveAll(productImageDeleted);
        }
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

    @Override
    public PageDTO search(Integer pageIndex, Integer pageSize, String keyword, ProductEntity.StatusEnum status, UUID materialId, UUID vendorId, UUID categoryId, UUID accessoryId, BigDecimal startPrice, BigDecimal endPrice, String sortBy, ProductEntity.ProductGender gender) {
        List<ProductEntity> productEntities = this.productDao.search(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);
        List<ProductDto> proProductDtos = productEntities.stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductDto.class)).collect(Collectors.toList());
        Long count = this.productDao.count(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);
        proProductDtos.forEach(productDto -> {
            productDto.setCategory(this.modelMapper.map(this.categoryRepository.findId(productDto.getCategoryId()).orElse(new CategoryEntity()), CategoryDto.class));
            productDto.setAccessory(this.modelMapper.map(this.accessoryRepository.findById(productDto.getAccessoryId()).get(), AccessoryDTO.class));
            productDto.setMaterial(this.modelMapper.map(this.materialRepository.findByID(productDto.getMaterialId()).get(),MaterialDto.class));
            productDto.setVendor(this.modelMapper.map(this.vendorRepository.findByID(productDto.getVendorId()).get(), VendorDto.class));
            if(!CollectionUtils.isEmpty( this.productImageRepository.findByProductId(productDto.getProductId()))){
                List<ProductImageDTO> imageDTOList =  this.productImageRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productImage -> this.modelMapper.map(productImage,ProductImageDTO.class)).collect(Collectors.toList());
                productDto.setProductImages(imageDTOList);
            }
            if(!CollectionUtils.isEmpty( this.productPropertyRepository.findByProductId(productDto.getProductId()))){
                List<ProductPropertyDto> productPropertyDtos =  this.productPropertyRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productProperty -> this.modelMapper.map(productProperty,ProductPropertyDto.class)).collect(Collectors.toList());
                productDto.setProductProperties(productPropertyDtos);
            }
            if(!CollectionUtils.isEmpty( this.productSizeRepository.findByProductId(productDto.getProductId()))){
                List<ProductSizeDto> productSizeDtos =  this.productSizeRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productSize -> this.modelMapper.map(productSize,ProductSizeDto.class)).collect(Collectors.toList());
                productSizeDtos.forEach(productSizeDto -> {
                    Optional<SizeEntity> sizeEntity =  this.sizeRepository.getSizeEntitiesBy(productSizeDto.getSizeId());
                    sizeEntity.ifPresent(entity -> productSizeDto.setSize(entity.getSize()));
                });
                productDto.setProductSizes(productSizeDtos);
            }

        });
        return new PageDTO(proProductDtos,pageIndex,pageSize,count);
    }
    @Override
    public PageDTO searchV2(Integer pageIndex, Integer pageSize, String keyword, ProductEntity.StatusEnum status, List<UUID> materialId, List<UUID> vendorId, List<UUID> categoryId, List<UUID> accessoryId, BigDecimal startPrice, BigDecimal endPrice, String sortBy, ProductEntity.ProductGender gender) {
        List<ProductEntity> productEntities = this.productDao.searchV2(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);
        List<ProductDto> proProductDtos = productEntities.stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductDto.class)).collect(Collectors.toList());
        Long count = this.productDao.countV2(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);
        proProductDtos.forEach(productDto -> {
            productDto.setCategory(this.modelMapper.map(this.categoryRepository.findId(productDto.getCategoryId()).orElse(new CategoryEntity()), CategoryDto.class));
            productDto.setAccessory(this.modelMapper.map(this.accessoryRepository.findById(productDto.getAccessoryId()).get(), AccessoryDTO.class));
            productDto.setMaterial(this.modelMapper.map(this.materialRepository.findByID(productDto.getMaterialId()).get(),MaterialDto.class));
            productDto.setVendor(this.modelMapper.map(this.vendorRepository.findByID(productDto.getVendorId()).get(), VendorDto.class));
            if(!CollectionUtils.isEmpty( this.productImageRepository.findByProductId(productDto.getProductId()))){
                List<ProductImageDTO> imageDTOList =  this.productImageRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productImage -> this.modelMapper.map(productImage,ProductImageDTO.class)).collect(Collectors.toList());
                productDto.setProductImages(imageDTOList);
            }
            if(!CollectionUtils.isEmpty( this.productPropertyRepository.findByProductId(productDto.getProductId()))){
                List<ProductPropertyDto> productPropertyDtos =  this.productPropertyRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productProperty -> this.modelMapper.map(productProperty,ProductPropertyDto.class)).collect(Collectors.toList());
                productDto.setProductProperties(productPropertyDtos);
            }
            if(!CollectionUtils.isEmpty( this.productSizeRepository.findByProductId(productDto.getProductId()))){
                List<ProductSizeDto> productSizeDtos =  this.productSizeRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productSize -> this.modelMapper.map(productSize,ProductSizeDto.class)).collect(Collectors.toList());
                productSizeDtos.forEach(productSizeDto -> {
                    Optional<SizeEntity> sizeEntity =  this.sizeRepository.getSizeEntitiesBy(productSizeDto.getSizeId());
                    sizeEntity.ifPresent(entity -> productSizeDto.setSize(entity.getSize()));
                });
                productDto.setProductSizes(productSizeDtos);
            }

        });
        return new PageDTO(proProductDtos,pageIndex,pageSize,count);
    }

    @Override
    public PageDTO autoComplete(Integer pageIndex, Integer pageSize, String keyword, ProductEntity.StatusEnum status, UUID materialId, UUID vendorId, UUID categoryId, UUID accessoryId, BigDecimal startPrice, BigDecimal endPrice, String sortBy, ProductEntity.ProductGender gender) {
        Long count = this.productDao.count(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);
        if(count == 0L){
            return PageDTO.empty();
        }
        pageSize = Integer.parseInt(count+ "");
        List<ProductEntity> productEntities = this.productDao.search(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);
        List<ProductDto> proProductDtos = productEntities.stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductDto.class)).collect(Collectors.toList());
        proProductDtos.forEach(productDto -> {
            productDto.setCategory(this.modelMapper.map(this.categoryRepository.findId(productDto.getCategoryId()).orElse(new CategoryEntity()), CategoryDto.class));
            productDto.setAccessory(this.modelMapper.map(this.accessoryRepository.findById(productDto.getAccessoryId()).orElse(new AccessoryEntity()), AccessoryDTO.class));
            productDto.setMaterial(this.modelMapper.map(this.materialRepository.findByID(productDto.getMaterialId()).orElse(new MaterialEntity()),MaterialDto.class));
            productDto.setVendor(this.modelMapper.map(this.vendorRepository.findByID(productDto.getVendorId()).orElse(new VendorEntity()), VendorDto.class));
            if(!CollectionUtils.isEmpty( this.productImageRepository.findByProductId(productDto.getProductId()))){
                List<ProductImageDTO> imageDTOList =  this.productImageRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productImage -> this.modelMapper.map(productImage,ProductImageDTO.class)).collect(Collectors.toList());
                productDto.setProductImages(imageDTOList);
            }
            if(!CollectionUtils.isEmpty( this.productSizeRepository.findByProductId(productDto.getProductId()))){
                List<ProductSizeDto> productSizeDtos =  this.productSizeRepository.findByProductId(productDto.getProductId()).stream()
                        .map(productSize -> this.modelMapper.map(productSize,ProductSizeDto.class)).collect(Collectors.toList());
                productSizeDtos.forEach(productSizeDto -> {
                    Optional<SizeEntity> sizeEntity =  this.sizeRepository.getSizeEntitiesBy(productSizeDto.getSizeId());
                    sizeEntity.ifPresent(entity -> productSizeDto.setSize(entity.getSize()));
                });
                productDto.setProductSizes(productSizeDtos);
            }
        });
        return new PageDTO(proProductDtos,pageIndex,pageSize,count);
    }

    private void validate(ProductRequest productRequest) {
        // kiểm tra category tồn tại
        if (categoryRepository.findId(productRequest.getCategoryId()).isEmpty()) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Category not exits");
        }
        // kiểm tra size tồn tại
        productRequest.getSizeProducts().forEach(sizeProduct -> {
            if(this.sizeRepository.getSizeEntitiesBy(sizeProduct.getSizeId()).isEmpty()) {
                throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Size not exits");
            }
        });
        // kiểm tra tên product tồn tại
        if (productRepository.findByNameProduct(productRequest.getNameProduct()).isPresent()){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Product is exits");
        }
        // kiểm tra material tồn tại
        if (materialRepository.findByID(productRequest.getMaterialId()).isEmpty()){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Material not exits");
        }
        if (this.vendorRepository.findByID(productRequest.getVendorId()).isEmpty()){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Vendor not exits");
        }
    }
    @Override
    public List<ProductOrderDto> getProductOrder(){
        List<ProductSizeEntity> productSizeEntities = this.productSizeRepository.findAllProductOrder();
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productSizeEntities.forEach((productSize) -> {
            ProductOrderDto productOrderDto = new ProductOrderDto();
            productOrderDto.setId(productSize.getIdProductSize());
            productOrderDto.setProductId(productSize.getProductId());
            productOrderDto.setPrice(productSize.getSalePrice());
            productOrderDto.setPricePurchase(productSize.getPurchasePrice());
            productOrderDto.setProductCode(this.productRepository.findID(productSize.getProductId()).orElse(new ProductEntity()).getCode());
            productOrderDto.setSizeId(productSize.getSizeId());
            productOrderDto.setQuantity(productSize.getQuantity());
            productOrderDto.setNameProduct(this.productRepository.findID(productSize.getProductId()).orElse(new ProductEntity()).getNameProduct());
            productOrderDto.setSize(this.sizeRepository.getSizeEntitiesBy(productSize.getSizeId()).orElse(new SizeEntity()).getSize());
            productOrderDto.setImageUrl(this.productImageRepository.findByProductId(productSize.getProductId()).stream().map(productImage -> productImage.getImageUrl()).collect(Collectors.toList()));
            productOrderDtos.add(productOrderDto);
        });
        return productOrderDtos;
    }

    @Override
    public List<ProductOrderDto> getProductOrder1() {
        List<ProductSizeEntity> productSizeEntities = this.productSizeRepository.findAllProductOrder1();
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productSizeEntities.forEach((productSize) -> {
            ProductOrderDto productOrderDto = new ProductOrderDto();
            productOrderDto.setId(productSize.getIdProductSize());
            productOrderDto.setProductId(productSize.getProductId());
            productOrderDto.setPrice(productSize.getSalePrice());
            productOrderDto.setPricePurchase(productSize.getPurchasePrice());
            productOrderDto.setProductCode(this.productRepository.findID(productSize.getProductId()).orElse(new ProductEntity()).getCode());
            productOrderDto.setSizeId(productSize.getSizeId());
            productOrderDto.setQuantity(productSize.getQuantity());
            productOrderDto.setNameProduct(this.productRepository.findID(productSize.getProductId()).orElse(new ProductEntity()).getNameProduct());
            productOrderDto.setSize(this.sizeRepository.getSizeEntitiesBy(productSize.getSizeId()).orElse(new SizeEntity()).getSize());
            productOrderDto.setImageUrl(this.productImageRepository.findByProductId(productSize.getProductId()).stream().map(productImage -> productImage.getImageUrl()).collect(Collectors.toList()));
            productOrderDtos.add(productOrderDto);
        });
        return productOrderDtos;
    }

    @Override
    public List<ProductDto> getProductTrending() {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findByProductTrending().subList(0,4);

        List<UUID> ProductIds = orderDetailEntityList.stream().map(OrderDetailEntity::getProductId).collect(Collectors.toList());
        List<ProductDto> productDtos = new ArrayList<>();
        ProductIds.forEach(uuid -> {
            ProductEntity product = productRepository.findById(uuid).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "ProductId not found"));
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            if (!CollectionUtils.isEmpty(this.productImageRepository.findByProductId(uuid))) {
                List<ProductImageDTO> imageDTOList = this.productImageRepository.findByProductId(uuid).stream()
                        .map(productImage -> this.modelMapper.map(productImage, ProductImageDTO.class)).collect(Collectors.toList());
                productDto.setProductImages(imageDTOList);
            }
            if (!CollectionUtils.isEmpty(this.productPropertyRepository.findByProductId(uuid))) {
                List<ProductPropertyDto> productPropertyDtos = this.productPropertyRepository.findByProductId(uuid).stream()
                        .map(productProperty -> this.modelMapper.map(productProperty, ProductPropertyDto.class)).collect(Collectors.toList());
                productDto.setProductProperties(productPropertyDtos);
            }
            if (!CollectionUtils.isEmpty(this.productSizeRepository.findByProductId(uuid))) {
                List<ProductSizeDto> productSizeDtos = this.productSizeRepository.findByProductId(uuid).stream()
                        .map(productSize -> this.modelMapper.map(productSize, ProductSizeDto.class)).collect(Collectors.toList());
                productSizeDtos.forEach(productSizeDto -> {
                    Optional<SizeEntity> sizeEntity = this.sizeRepository.getSizeEntitiesBy(productSizeDto.getSizeId());
                    sizeEntity.ifPresent(entity -> productSizeDto.setSize(entity.getSize()));
                });
                productSizeDtos = productSizeDtos.stream().sorted(Comparator.comparing(ProductSizeDto::getSize)).collect(Collectors.toList());
                productDto.setProductSizes(productSizeDtos);
            }
            productDtos.add(productDto);
        });
        return productDtos;
    }
}
