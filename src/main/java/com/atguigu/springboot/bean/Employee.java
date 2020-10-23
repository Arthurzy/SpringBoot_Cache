package com.atguigu.springboot.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;  // 1 男 2 女
    private Integer dId;
}
