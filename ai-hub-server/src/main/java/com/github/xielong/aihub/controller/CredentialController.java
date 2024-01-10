package com.github.xielong.aihub.controller;

import com.github.xielong.aihub.model.CredentialRequest;
import com.github.xielong.aihub.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credentials")
@Slf4j
public class CredentialController {

    private CredentialService apiCredentialService;

    @PutMapping("/{provider}")
    public ResponseEntity<Boolean> invokeModel(
            @PathVariable String provider,
            @RequestBody CredentialRequest request) throws Exception {
        apiCredentialService.addOrUpdateCredentials(provider, request.getKey(), request.getValue());

        return ResponseEntity.ok(true);
    }


}
