package net.dancier.producer;

import net.dancier.producer.model.Contract;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContractRepository extends CrudRepository<Contract, UUID> {
}
