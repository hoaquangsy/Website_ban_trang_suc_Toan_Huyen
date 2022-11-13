package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "message")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity extends BaseEntity{
    @Id
    @Column(name = "id", length = 36)
    @Type(type = "uuid-char")
    private UUID id;
    @Column(name = "userId")
    @Type(type = "uuid-char")
    private UUID userId;
    @Column(name = "message" )
    private String message;
    @Column(name = "send" )
    private Boolean isSend;
    @Column(name = "image")
    private String image;
}
