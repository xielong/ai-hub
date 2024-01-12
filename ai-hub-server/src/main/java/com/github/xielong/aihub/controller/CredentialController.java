package com.github.xielong.aihub.controller;

import com.github.xielong.aihub.model.CredentialRequest;
import com.github.xielong.aihub.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credentials")
@Slf4j
public class CredentialController {

    @Autowired
    private CredentialService apiCredentialService;

    @PostMapping("/{provider}")
    public ResponseEntity<Boolean> addOrUpdateCredentials(
            @PathVariable String provider,
            @RequestBody List<CredentialRequest> requests) {
        for (CredentialRequest request : requests) {
            apiCredentialService.addOrUpdateCredentials(provider, request.getKey(), request.getValue());
        }

        return ResponseEntity.ok(true);
    }


}
