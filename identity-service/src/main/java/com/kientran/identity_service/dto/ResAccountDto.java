package com.kientran.identity_service.dto;

import com.kientran.identity_service.entity.AccountStatus;
import com.kientran.identity_service.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResAccountDto {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private Role role;
    private AccountStatus account_status;
}
