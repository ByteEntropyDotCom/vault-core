package com.byteentropy.vault_core.api;

import com.byteentropy.vault_core.crypto.model.VaultRequest;
import com.byteentropy.vault_core.crypto.model.VaultResponse;
import com.byteentropy.vault_core.service.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vault")
public class VaultController {
    private final TokenService tokenService;

    public VaultController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/tokenize")
    public VaultResponse tokenize(@RequestBody VaultRequest request) throws Exception {
        return tokenService.tokenize(request);
    }
}