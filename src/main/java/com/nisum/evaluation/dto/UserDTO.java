package com.nisum.evaluation.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private boolean active;
    private Set<PhoneDTO> phones;
}
