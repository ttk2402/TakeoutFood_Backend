package com.kientran.identity_service.controller;

import com.kientran.identity_service.dto.AccountStatusDto;
import com.kientran.identity_service.response.ApiResponse;
import com.kientran.identity_service.service.AccountStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/account_status")
public class AccountStatusController {
    private final AccountStatusService acStatusService;

    public AccountStatusController(AccountStatusService acStatusService) {
        this.acStatusService = acStatusService;
    }

    @PostMapping("/add")
    public ResponseEntity<AccountStatusDto> addAccountStatus(@RequestBody AccountStatusDto acStatusDto) {
        AccountStatusDto createAcStatusDto = this.acStatusService.createAccountStatus(acStatusDto);
        return new ResponseEntity<>(createAcStatusDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{acStatusId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer acStatusId) {
        this.acStatusService.deleteAccountStatus(acStatusId);
        return new ResponseEntity<>(new ApiResponse("Account status is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/{acStatusId}")
    public ResponseEntity<AccountStatusDto> updateCategory(@RequestBody AccountStatusDto acStatusDto,
                                                  @PathVariable Integer acStatusId) {
        AccountStatusDto updatedacStatus = this.acStatusService.updateAccountStatus(acStatusDto, acStatusId);
        return new ResponseEntity<>(updatedacStatus, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountStatusDto>> getAllAccountStatus() {
        List<AccountStatusDto> acStatusDtos = this.acStatusService.getAccountStatuses();
        return ResponseEntity.ok(acStatusDtos);
    }
}