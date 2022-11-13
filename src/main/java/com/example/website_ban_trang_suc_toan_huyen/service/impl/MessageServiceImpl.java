package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MessageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MessageEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MessageRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.MessageRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDTO createMessage(MessageRequest request) {
        MessageEntity entity = new MessageEntity();
        entity.setId(UUID.randomUUID());
        entity.setIsSend(request.getSend());
        entity.setMessage(request.getMessage());
        entity.setUserId(request.getUserId());
        entity.setImage(request.getImage());
        return modelMapper.map(messageRepository.save(entity), MessageDTO.class);
    }

    @Override
    public List<MessageDTO> getAll() {
        List<MessageEntity> messageEntities = messageRepository.findByUserIdUnique();
        List<MessageDTO> response = new ArrayList<>();
        messageEntities.forEach(messageEntity -> {
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(messageEntity, messageDTO);
            response.add(messageDTO);
        });
        return response;
    }

    @Override
    public List<MessageDTO> getMessageByUserId(UUID userID) {
        List<MessageEntity> messageEntities = messageRepository.findByUserId(userID);
        List<MessageDTO> response = new ArrayList<>();
        messageEntities.forEach(messageEntity -> {
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(messageEntity, messageDTO);
            response.add(messageDTO);
        });
        return response;
    }
}
