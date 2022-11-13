package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MessageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MessageRequest;
import com.example.website_ban_trang_suc_toan_huyen.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class MessageController {
    @Autowired
    private MessageServiceImpl messageService;

    @MessageMapping("/send-message/{userID}")
    @SendTo(value = {"/topic/public/{userID}","/topic/public/all"})
    public MessageDTO sendMessage(MessageRequest messageRequest) {
        return messageService.createMessage(messageRequest);
    }
    @SubscribeMapping("/topic/public/all")
    public List<MessageDTO> getAllMessage(){
        return messageService.getAll();
    }
    @SubscribeMapping("/topic/public/{userID}")
    public List<MessageDTO> getAllMessageByUser(@DestinationVariable UUID userID){
        return messageService.getMessageByUserId(userID);
    }

}
