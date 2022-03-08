package net.dancier.producer;

import lombok.RequiredArgsConstructor;
import net.dancier.producer.model.Contract;
import net.dancier.producer.model.ContractStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final static String CONTRACT_UPDATE_EVENT_NAME = "ContractUpdatedEvent";

    private final ContractRepository contractRepository;

    public final KafkaService kafkaService;

    public List<Contract> getAll() {
        return StreamSupport
                .stream(contractRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void changeStatus(String contractIdAsString, String contractStatusAsString) {
        UUID contractId = UUID.fromString(contractIdAsString);
        ContractStatus contractStatus = ContractStatus.valueOf(contractStatusAsString);
        changeStatus(contractId, contractStatus);
    }

    public void changeStatus(UUID contractId, ContractStatus contractStatus) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new IllegalArgumentException());
        contract.setStatus(contractStatus);
        contractRepository.save(contract);

        kafkaService.sendMessage(CONTRACT_UPDATE_EVENT_NAME,
                contractId + " - " + contractStatus);
    }

    public Contract createContract() {
        Contract contract = new Contract();
        contract.setStatus(ContractStatus.APPLICATION);
        contractRepository.save(contract);
        return contract;
    }

}
