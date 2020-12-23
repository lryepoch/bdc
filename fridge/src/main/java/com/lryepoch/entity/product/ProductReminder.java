package com.lryepoch.entity.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/11/11 8:57
 * @description TODO
 */
@Getter
@Setter
@Entity
@Table(name = "product_reminder")
public class ProductReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer pid;
    private Date updateTime;
}
