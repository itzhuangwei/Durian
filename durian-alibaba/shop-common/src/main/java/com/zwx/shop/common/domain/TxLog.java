package com.zwx.shop.common.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "shop_txlog")
//消息事务状态记录
public class TxLog {
    @Id
    private String txId;
    private LocalDateTime date;
}