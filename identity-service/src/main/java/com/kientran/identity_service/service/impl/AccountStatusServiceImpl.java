package com.kientran.identity_service.service.impl;

import com.kientran.identity_service.dto.AccountStatusDto;
import com.kientran.identity_service.entity.AccountStatus;
import com.kientran.identity_service.exception.ResourceNotFoundException;
import com.kientran.identity_service.repository.AccountStatusRepository;
import com.kientran.identity_service.service.AccountStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {
    private final AccountStatusRepository acStatusRepository;
    private final ModelMapper modelMapper;

    public AccountStatusServiceImpl(AccountStatusRepository acStatusRepository, ModelMapper modelMapper) {
        this.acStatusRepository = acStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountStatusDto createAccountStatus(AccountStatusDto acStatusDto) {
        AccountStatus acStatus = this.modelMapper.map(acStatusDto, AccountStatus.class);
        AccountStatus addAcStatus = this.acStatusRepository.save(acStatus);
        return this.modelMapper.map(addAcStatus, AccountStatusDto.class);
    }

    @Override
    public void deleteAccountStatus(Integer acStatusId) {
        AccountStatus acStatus = this.acStatusRepository.findById(acStatusId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountStatus ", "AccountStatusId", acStatusId));
        this.acStatusRepository.delete(acStatus);
    }

    @Override
    public AccountStatusDto updateAccountStatus(AccountStatusDto acStatusDto, Integer acStatusId) {
        AccountStatus accountStatus = this.acStatusRepository.findById(acStatusId).orElseThrow(()-> new ResourceNotFoundException("AccountStatus","AccountStatusId", acStatusId));
        accountStatus.setStatus(acStatusDto.getStatus());
        AccountStatus updatedAcStatus = this.acStatusRepository.save(accountStatus);
        return this.modelMapper.map(updatedAcStatus, AccountStatusDto.class);
    }

    @Override
    public List<AccountStatusDto> getAccountStatuses() {
        List<AccountStatus> accountStatuses = this.acStatusRepository.findAll();
        List<AccountStatusDto> accountStatusDtos = accountStatuses.stream().map((accountStatus)-> this.modelMapper.map(accountStatus, AccountStatusDto.class)).collect(Collectors.toList());
        return accountStatusDtos;
    }
}
