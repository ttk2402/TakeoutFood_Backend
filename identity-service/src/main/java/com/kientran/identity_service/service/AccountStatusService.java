package com.kientran.identity_service.service;

import com.kientran.identity_service.dto.AccountStatusDto;
import java.util.List;

public interface AccountStatusService {
    AccountStatusDto createAccountStatus(AccountStatusDto acStatusDto);
    void deleteAccountStatus(Integer acStatusId);
    AccountStatusDto updateAccountStatus(AccountStatusDto acStatusDto, Integer acStatusId);
    List<AccountStatusDto> getAccountStatuses();
}
