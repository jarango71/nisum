package com.nisum.evaluation.payload.request;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class RegisterRequest {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;
    
    private Set<String> phones;
}