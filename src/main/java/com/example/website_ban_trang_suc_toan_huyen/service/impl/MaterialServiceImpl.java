package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MaterialDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.BadRequestException;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MaterialRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.MaterialRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.MaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MaterialDto createMaterial(MaterialRequest request) {
        MaterialEntity entity = modelMapper.map(request, MaterialEntity.class);
        if (materialRepository.existsByMaterialNameAndTypeAndStatus(entity.getMaterialName(), entity.getType(), MaterialEntity.StatusEnum.ACTIVE.getCode())) {
            throw new BadRequestException("Material is existed");
        }
        MaterialDto dto = modelMapper.map(materialRepository.save(entity), MaterialDto.class);
        return dto;
    }

    @Override
    public MaterialDto updateMaterial(MaterialRequest request, Integer id) {
        MaterialEntity entity = materialRepository.findById(id).get();
        if (ObjectUtils.isEmpty(entity)){
            throw new NotFoundException(400,"Not found material");
        }
        if (materialRepository.existsByMaterialNameAndTypeAndStatus(entity.getMaterialName(), entity.getType(), MaterialEntity.StatusEnum.ACTIVE.getCode())) {
            throw new BadRequestException("Material is existed");
        }
        BeanUtils.copyProperties(request,entity);
        MaterialDto dto = modelMapper.map(materialRepository.save(entity),MaterialDto.class);
        return dto;
    }
    @Override
    public void deleteMaterial(Integer id){
        MaterialEntity entity = materialRepository.findById(id).get();
        entity.setStatus(MaterialEntity.StatusEnum.INACTIVE.getCode());
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

}
