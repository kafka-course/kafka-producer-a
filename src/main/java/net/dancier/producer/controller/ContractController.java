package net.dancier.producer.controller;

import lombok.RequiredArgsConstructor;
import net.dancier.producer.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("contracts")
@RestController()
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public ResponseEntity getAllContracts() {
        return ResponseEntity.ok(contractService.getAll());
    }

    @PostMapping
    public ResponseEntity createContract() {
        return ResponseEntity.ok(contractService.createContract());
    }

    @RequestMapping("/{contractId}")
    @PostMapping
    public ResponseEntity changeContractStatus(
            @PathVariable String contractId,
            @RequestBody String desiredStatus) {
        contractService.changeStatus(contractId, desiredStatus);
        return ResponseEntity.ok().build();
    }
}
