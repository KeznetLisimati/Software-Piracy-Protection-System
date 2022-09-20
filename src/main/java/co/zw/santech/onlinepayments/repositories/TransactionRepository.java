package co.zw.santech.onlinepayments.repositories;

import co.zw.santech.onlinepayments.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Override
    Optional<Transaction> findById(Long aLong);

    List<Transaction> findByUser(String username);
}
