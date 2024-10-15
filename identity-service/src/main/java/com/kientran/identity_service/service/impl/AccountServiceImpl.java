package com.kientran.identity_service.service.impl;

import com.kientran.identity_service.dto.*;
import com.kientran.identity_service.entity.Account;
import com.kientran.identity_service.entity.AccountStatus;
import com.kientran.identity_service.entity.Role;
import com.kientran.identity_service.exception.ResourceNotFoundException;
import com.kientran.identity_service.repository.AccountRepository;
import com.kientran.identity_service.repository.AccountStatusRepository;
import com.kientran.identity_service.repository.RoleRepository;
import com.kientran.identity_service.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final AccountStatusRepository acStatusRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(RoleRepository roleRepository, AccountRepository accountRepository, AccountStatusRepository acStatusRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.acStatusRepository = acStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResAccountDto createAccount(AccountDto accountDto, Integer roleId) {
        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role ", "roleId", roleId));
        String username = accountDto.getUsername();
        String email = accountDto.getEmail();
        List<Account> listAc = this.accountRepository.isValidOfUsernameAndEmail(username, email);
        if(listAc.isEmpty()) {
            AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("ACTIVED");
            Account account = this.modelMapper.map(accountDto, Account.class);
            account.setRole(role);
            account.setAccount_status(acStatus);
            Account newAccount = this.accountRepository.save(account);
            return this.modelMapper.map(newAccount, ResAccountDto.class);
        }else {
            return new ResAccountDto();
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
            this.accountRepository.delete(account);
    }

    @Override
    public ResAccountDto blockAccount(Integer accountId) {
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
        AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("BLOCKED");
        account.setAccount_status(acStatus);
        Account updatedAccount = this.accountRepository.save(account);
        return this.modelMapper.map(updatedAccount, ResAccountDto.class);
    }

    @Override
    public ResAccountDto openAccount(Integer accountId) {
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
        AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("ACTIVED");
        account.setAccount_status(acStatus);
        Account updatedAccount = this.accountRepository.save(account);
        return this.modelMapper.map(updatedAccount, ResAccountDto.class);
    }

    @Override
    public ResAccountDto getAccount(Integer accountId) {
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
        return this.modelMapper.map(account, ResAccountDto.class);
    }

    @Override
    public ResAccountDto login(LoginDto loginDto) {
        Account account = this.accountRepository.findAccountByLoginDto(loginDto.getUsername(), loginDto.getPassword());
        if(account == null || account.getAccount_status().getStatus().equals("BLOCKED")) {
            return new ResAccountDto();
        }else {
            return this.modelMapper.map(account, ResAccountDto.class);
        }
    }

    @Override
    public ResAccountDto updateAccount(AccountDto updateAccountDto, Integer accountId) {
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
        account.setFirstname(updateAccountDto.getFirstname());
        account.setLastname(updateAccountDto.getLastname());
        account.setUsername(updateAccountDto.getUsername());
        account.setEmail(updateAccountDto.getEmail());
        account.setPassword(updateAccountDto.getPassword());
        Account updatedAccount = this.accountRepository.save(account);
        return this.modelMapper.map(updatedAccount, ResAccountDto.class);
    }

    @Override
    public boolean changePassword(Integer accountId, ChangePasswordDto changePasswordDto) {
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
        if(changePasswordDto.getPassword().equals(account.getPassword())) {
            account.setPassword(changePasswordDto.getNewpassword());
            this.accountRepository.save(account);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<AccountDto> getAccountsByRole(Integer roleId) {
        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role ", "roleId", roleId));
        List<Account> accounts = this.accountRepository.findByRole(role);

        return accounts.stream().map((account) -> this.modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResAccountDto> getAccounts() {
        List<Account> accounts = this.accountRepository.findAll();
        return accounts.stream().map((account)-> this.modelMapper.map(account, ResAccountDto.class)).collect(Collectors.toList());
    }

    @Override
    public TotalCustomerDto getTotalCustomerInStore() {
        TotalCustomerDto customerDto = new TotalCustomerDto();
        customerDto.setTotal(this.accountRepository.getTotalCustomer());
        return customerDto;
    }
}
