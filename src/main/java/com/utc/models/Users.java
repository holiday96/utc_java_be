package com.utc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 25.5.2024
 * Description :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roleId;
    private String fullName;
    private String avatar;
    private String address;
    private String phone;
    private String username;
    private String password;
    private Integer status;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String modifiedBy;
}
