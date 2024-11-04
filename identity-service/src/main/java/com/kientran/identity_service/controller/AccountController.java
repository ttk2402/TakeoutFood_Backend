package com.kientran.identity_service.controller;

import com.kientran.identity_service.dto.*;
import com.kientran.identity_service.response.ApiResponse;
import com.kientran.identity_service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String ADD_ACCOUNT_TOPIC = "add_account_topic";
    private static final String DELETE_ACCOUNT_TOPIC = "delete_account_topic";
    private static final String ADD_SHIPPER_TOPIC = "add_shipper_topic";
    private static final String DELETE_SHIPPER_TOPIC = "delete_shipper_topic";

    public AccountController(AccountService accountService, KafkaTemplate<String, String> kafkaTemplate) {
        this.accountService = accountService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/add/{roleId}")
    public ResponseEntity<ResAccountDto> addAccount(@RequestBody AccountDto accountDto,
                                                            @PathVariable Integer roleId) {
        ResAccountDto newAccountDto = this.accountService.createAccount(accountDto, roleId);
        if(newAccountDto != null && (!newAccountDto.getRole().getRole().equals("ADMIN"))) {
            if(newAccountDto.getRole().getRole().equals("STAFF") || newAccountDto.getRole().getRole().equals("CUSTOMER")) {
                String account_ID = String.valueOf(newAccountDto.getId());
                kafkaTemplate.send(ADD_ACCOUNT_TOPIC, account_ID);
            }else{
                String account_ID = String.valueOf(newAccountDto.getId());
                kafkaTemplate.send(ADD_SHIPPER_TOPIC, account_ID);
            }
        }
        return new ResponseEntity<>(newAccountDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Integer accountId) {
        ResAccountDto newAccountDto = this.accountService.getAccount(accountId);
        if(newAccountDto != null && (!newAccountDto.getRole().getRole().equals("ADMIN"))) {
            if(newAccountDto.getRole().getRole().equals("SHIPPER")){
                String account_ID = String.valueOf(accountId);
                kafkaTemplate.send(DELETE_SHIPPER_TOPIC, account_ID);
            }else{
                String account_ID = String.valueOf(accountId);
                kafkaTemplate.send(DELETE_ACCOUNT_TOPIC, account_ID);
            }
        }
        this.accountService.deleteAccount(accountId);
        return new ResponseEntity<>(new ApiResponse("Account is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity<ResAccountDto> changeStatusAccountToBlock(@PathVariable Integer accountId) {
        ResAccountDto updatedAc = this.accountService.blockAccount(accountId);
        return new ResponseEntity<>(updatedAc, HttpStatus.OK);
    }

    @PutMapping("/open/{accountId}")
    public ResponseEntity<ResAccountDto> changeStatusAccountToOpen(@PathVariable Integer accountId) {
        ResAccountDto updatedAc = this.accountService.openAccount(accountId);
        return new ResponseEntity<>(updatedAc, HttpStatus.OK);
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<ResAccountDto> getAccountDetail(@PathVariable Integer accountId) {
        ResAccountDto accountInfo = this.accountService.getAccount(accountId);
        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResAccountDto> loginAccount(@RequestBody LoginDto loginDto) {
        ResAccountDto loginAccountDto = this.accountService.login(loginDto);
        return new ResponseEntity<>(loginAccountDto, HttpStatus.OK);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ResAccountDto> updateInfoAccountById(@RequestBody AccountDto accountDto,
                                                               @PathVariable Integer accountId) {
        ResAccountDto updatedAc = this.accountService.updateAccount(accountDto, accountId);
        return new ResponseEntity<>(updatedAc, HttpStatus.OK);
    }

    @PutMapping("/changepassword/{accountId}")
    public ResponseEntity<ApiResponse> changePasswordForAccount(@RequestBody ChangePasswordDto changePasswordDto,
                                                                @PathVariable Integer accountId) {
        boolean result = this.accountService.changePassword(accountId, changePasswordDto);
        if(result) {
            return new ResponseEntity<>(new ApiResponse("Password is changed successfully!", true),
                    HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ApiResponse("Password is not correct", false),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<AccountDto>> getListAccountByRole(@PathVariable Integer roleId) {
        List<AccountDto> accountDtos = this.accountService.getAccountsByRole(roleId);
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ResAccountDto>> getAllAccounts() {
        List<ResAccountDto> accounts = this.accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/totalCustomer")
    public ResponseEntity<TotalCustomerDto> getTotalCustomer() {
        TotalCustomerDto customerDto = this.accountService.getTotalCustomerInStore();
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }
}