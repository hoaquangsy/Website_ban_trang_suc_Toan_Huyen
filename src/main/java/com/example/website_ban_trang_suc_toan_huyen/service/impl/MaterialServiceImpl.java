package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.MaterialDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MaterialDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.BadRequestException;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MaterialRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.MaterialRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductSizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.MaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MaterialDao materialDao;

    @Override
    public MaterialDto createMaterial(MaterialRequest request) {
        MaterialEntity entity = modelMapper.map(request, MaterialEntity.class);
        if (materialRepository.existsByMaterialNameAndTypeAndStatus(entity.getMaterialName(), entity.getType(),entity.getStatus())) {
            throw new BadRequestException("Material is existed");
        }
        entity.setMaterialId(UUID.randomUUID());
        entity.setStatus(MaterialEntity.StatusEnum.ACTIVE);
        MaterialDto dto = modelMapper.map(materialRepository.save(entity), MaterialDto.class);
        return dto;
    }

    @Override
    @Transactional
    public MaterialDto updateMaterial(MaterialRequest request, UUID id) {
        MaterialEntity entity = materialRepository.findById(id).orElseThrow(() -> new NotFoundException(400,"Not found material"));
        if (materialRepository.checkMaterialDuplicate(request.getMaterialName(), request.getType(),id ).isPresent()) {
           throw new BadRequestException("Material is existed");
        }
        BigDecimal priceSale= entity.getSalePrice();
        BigDecimal pricePurchase = entity.getPurchasePrice();
        BeanUtils.copyProperties(request,entity);
        MaterialDto materialDto =  modelMapper.map(materialRepository.save(entity),MaterialDto.class);
        if(!Objects.equals(priceSale, request.getSalePrice()) || !Objects.equals(pricePurchase,request.getPurchasePrice())) {
            List<ProductEntity> productEntities = this.productRepository.findMaterial(materialDto.getMaterialId());
            productEntities.forEach(productEntity -> {
                List<ProductSizeEntity> productSizeEntities = this.productSizeRepository.findByProductId(productEntity.getProductId());
                productSizeEntities.forEach(productSizeEntity -> {
                    productSizeEntity.setSalePrice(productSizeEntity.getSalePrice().subtract(priceSale).add(materialDto.getSalePrice()));
                    productSizeEntity.setPurchasePrice(productSizeEntity.getPurchasePrice().subtract(pricePurchase).add(materialDto.getPurchasePrice()));
                });
                this.productSizeRepository.saveAll(productSizeEntities);
            });
        }
        return materialDto;
    }

    @Override
    public MaterialDto lock(UUID id) {
        MaterialEntity entity = materialRepository.findById(id).orElseThrow(() -> new NotFoundException(400,"Not found material"));
        entity.setStatus(MaterialEntity.StatusEnum.INACTIVE);
        return modelMapper.map(materialRepository.save(entity),MaterialDto.class);
    }

    @Override
    public MaterialDto getById(UUID id) {
        MaterialEntity entity = materialRepository.findById(id).orElseThrow(() -> new NotFoundException(400,"Not found material"));
             return this.modelMapper.map(entity,MaterialDto.class);
    }

    @Override
    public MaterialDto unlock(UUID id) {
        MaterialEntity entity = materialRepository.findById(id).orElseThrow(() -> new NotFoundException(400,"Not found material"));
        entity.setStatus(MaterialEntity.StatusEnum.ACTIVE);
        return modelMapper.map(materialRepository.save(entity),MaterialDto.class);
    }

    @Override
    public void deleteMaterial(UUID id){
        MaterialEntity entity = materialRepository.findById(id).orElseThrow(() -> new NotFoundException(400,"Not found material"));
        entity.setStatus(MaterialEntity.StatusEnum.INACTIVE);
        materialRepository.save(entity);
    }
    @Override
    public Page<MaterialDto> getAllMaterial(int page, int pageSize){
        Page<MaterialEntity> materialEntityPage=materialRepository.findAll(PageRequest.of(page,pageSize));
        if (materialEntityPage.getTotalElements()>0){
            return materialEntityPage.map(MaterialEntity -> modelMapper.map(MaterialEntity,MaterialDto.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Material not exist");
    }

    @Override
    public PageDTO search(String keyword, Integer pageIndex,
                          Integer pageSize, MaterialEntity.MaterialType type,
                          MaterialEntity.StatusEnum status, BigDecimal startPrice,
                          BigDecimal endPrice,String sortBy) {
        List<MaterialEntity> materialEntityList = this.materialDao.search(keyword,pageIndex,pageSize,type,status,startPrice,endPrice,sortBy);
        List<MaterialDto> materialDtos = materialEntityList.stream()
                .map(materialEntity -> modelMapper.map(materialEntity,MaterialDto.class)).collect(Collectors.toList());
        Long count = this.materialDao.count(keyword,pageIndex,pageSize,type,status,startPrice,endPrice,sortBy);
        return new PageDTO(materialDtos,pageIndex,pageSize,count);
    }
    @Override
    public PageDTO autoComplete(String keyword, Integer pageIndex,
                          Integer pageSize, MaterialEntity.MaterialType type,
                          MaterialEntity.StatusEnum status, BigDecimal startPrice,
                          BigDecimal endPrice,String sortBy) {
        Long count = this.materialDao.count(keyword,pageIndex,pageSize,type,status,startPrice,endPrice,sortBy);
        pageSize = Integer.parseInt(count.toString());
        List<MaterialEntity> materialEntityList = this.materialDao.search(keyword,pageIndex,pageSize,type,status,startPrice,endPrice,sortBy);
        List<MaterialDto> materialDtos = materialEntityList.stream()
                .map(materialEntity -> modelMapper.map(materialEntity,MaterialDto.class)).collect(Collectors.toList());

        return new PageDTO(materialDtos,pageIndex,pageSize,count);
    }
}
