package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "calendar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntity extends BaseEntity{
    @Id
    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;
    @Column
    @Type(type ="uuid-char")
    private UUID orderId;
    @Column
    private Date time;
    @Column
    private Integer status;
    @Column
    private String userName;
    @Column(length = 12)
    private String phoneNumber;
    @Column
    private BigDecimal customerMoney;
    @Column
    private BigDecimal arrears;
    @Getter
    public enum Status{
        WAIT_CONFIRM(0),
        ACTIVE(1),
        DONE(100),
        CLOSE(101);


        private int code;

        Status(int code) {
            code=this.code;
        }
    }
}
