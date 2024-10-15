package com.kientran.identity_service.service;

import com.kientran.identity_service.dto.*;
import java.util.List;

public interface AccountService {
    ResAccountDto createAccount(AccountDto accountDto, Integer roleId);
    void deleteAccount(Integer accountId);
    ResAccountDto blockAccount(Integer accountId);
    ResAccountDto openAccount(Integer accountId);
    ResAccountDto getAccount(Integer accountId);
    ResAccountDto login(LoginDto loginDto);
    ResAccountDto updateAccount(AccountDto updateAccountDto, Integer accountId);
    boolean changePassword(Integer accountId, ChangePasswordDto changePasswordDto);
    List<AccountDto> getAccountsByRole(Integer roleId);
    List<ResAccountDto> getAccounts();
    TotalCustomerDto getTotalCustomerInStore();
}