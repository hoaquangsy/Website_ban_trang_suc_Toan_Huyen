package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.AccessoryDAO;
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
    private  ModelMapper modelMapper;
    @Autowired
    private AccessoryDAO accessoryDAO;

    @Override
    public AccessoryDTO create(AccessoryRequest request) {
        AccessoryEntity accessory = modelMapper.map(request,AccessoryEntity.class);
        accessory.setAccessoryId(UUID.randomUUID());
        accessory.setDeleted(Boolean.FALSE);
        accessory.setStatus(AccessoryStatus.ACTIVE);
        accessoryRepository.save(accessory);
        AccessoryDTO accessoryDTO =modelMapper.map(accessory,AccessoryDTO.class);
        return accessoryDTO;
    }

    @Override
    public AccessoryDTO update(AccessoryRequest request, UUID id) {
        AccessoryEntity accessory =accessoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException(400,"NOT FOUND ACCESSORY"));
        BeanUtils.copyProperties(request,accessory);
        return modelMapper.map(accessoryRepository.save(accessory),AccessoryDTO.class);
    }

    @Override
    public HttpStatus delete(UUID id) {
        AccessoryEntity accessory =accessoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException(400,"NOT FOUND ACCESSORY"));
        accessory.setDeleted(Boolean.TRUE);
        accessoryRepository.save(accessory);
        return HttpStatus.OK;
    }

    @Override
    public AccessoryDTO getById(UUID id) {
        AccessoryEntity accessory =accessoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException(400,"NOT FOUND ACCESSORY"));
        return modelMapper.map(accessory,AccessoryDTO.class);
    }

    @Override
    public PageDTO search(String keyword, Integer pageIndex, Integer pageSize,
                          AccessoryStatus status, String sortBy) {
        List<AccessoryEntity> accessoryEntities =this.accessoryDAO.search(pageIndex,pageSize,keyword
                ,sortBy,status);
        List<AccessoryDTO> accessoriesDTO = accessoryEntities.stream()
                .map(accessoryEntity -> modelMapper.map(accessoryEntity,AccessoryDTO.class))
                .collect(Collectors.toList());
        Long count = this.accessoryDAO.count(pageIndex,pageSize,keyword,sortBy,status);
        return new PageDTO<>(accessoriesDTO,pageIndex,pageSize,count);
    }

    @Override
    public HttpStatus active(UUID id) {
            AccessoryEntity accessory =accessoryRepository.findById(id)
                    .orElseThrow(()->new NotFoundException(400,"NOT FOUND ACCESSORY"));
            accessory.setStatus(AccessoryStatus.ACTIVE);
            accessoryRepository.save(accessory);
            return HttpStatus.OK;
    }

    @Override
    public HttpStatus inactive(UUID id) {
        AccessoryEntity accessory =accessoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException(400,"NOT FOUND ACCESSORY"));
        accessory.setStatus(AccessoryStatus.INACTIVE);
        accessoryRepository.save(accessory);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus draft(UUID id) {
        AccessoryEntity accessory =accessoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException(400,"NOT FOUND ACCESSORY"));
        accessory.setStatus(AccessoryStatus.DRAFTS);
        accessoryRepository.save(accessory);
        return HttpStatus.OK;
    }
}

