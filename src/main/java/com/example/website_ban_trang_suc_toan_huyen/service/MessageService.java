package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MessageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MessageRequest;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDTO createMessage(MessageRequest request);

    List<MessageDTO> getAll();

    List<MessageDTO> getMessageByUserId(UUID userID);
}
