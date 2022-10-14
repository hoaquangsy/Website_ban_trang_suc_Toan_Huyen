package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.AccessoryDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.AccessoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.AccessoryRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.AccessoryService;
import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccessoryServiceImpl implements AccessoryService {
    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccessoryDTO create(AccessoryRequest request) {
        AccessoryEntity accessory = modelMapper.map(request, AccessoryEntity.class);
        accessory.setAccessoryId(UUID.randomUUID());
        accessory.setDeleted(Boolean.FALSE);
        accessory.setStatus(AccessoryStatus.ACTIVE);
        accessoryRepository.save(accessory);
        AccessoryDTO accessoryDTO = modelMapper.map(accessory, AccessoryDTO.class);
        return accessoryDTO;
    }

    @Override
    public AccessoryDTO update(AccessoryRequest request, UUID id) {
        AccessoryEntity accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(400, "NOT FOUND ACCESSORY"));
        BeanUtils.copyProperties(request, accessory);
        return modelMapper.map(accessoryRepository.save(accessory), AccessoryDTO.class);
    }

    @Override
    public HttpStatus delete(UUID id) {
        AccessoryEntity accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(400, "NOT FOUND ACCESSORY"));
        accessory.setDeleted(Boolean.TRUE);
        accessoryRepository.save(accessory);
        return HttpStatus.OK;
    }

    @Override
    public AccessoryDTO getById(UUID id) {
        AccessoryEntity accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(400, "NOT FOUND ACCESSORY"));
        return modelMapper.map(accessory, AccessoryDTO.class);
    }

    @Override
    public PageDTO search(String keyword, Integer page, Integer pageSize,
                          AccessoryStatus status, String sortBy, BigDecimal startPrice, BigDecimal endPrice) {
        List<AccessoryEntity> accessoryEntities = this.accessoryRepository.search(page, pageSize, keyword
                , sortBy, status, startPrice, endPrice);
        List<AccessoryDTO> accessoriesDTO = accessoryEntities.stream()
                .map(accessoryEntity -> modelMapper.map(accessoryEntity, AccessoryDTO.class))
                .collect(Collectors.toList());
        Long count = this.accessoryRepository.count(page, pageSize, keyword, sortBy, status, startPrice, endPrice);
        return new PageDTO<>(accessoriesDTO, page, pageSize, count);
    }
}

