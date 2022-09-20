package co.zw.santech.onlinepayments.services;

import co.zw.santech.onlinepayments.models.Transaction;
import co.zw.santech.onlinepayments.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final RequestService requestService;

    public void makePayment(Transaction transaction, String user, HttpServletRequest httpServletRequest) {
        transaction.setLicenseKey(requestService.getClientIp(httpServletRequest));
        transaction.setUser(user);
        this.transactionRepository.save(transaction);
    }

    public List<Transaction> findMyTransactions() {
        return this.transactionRepository.findAll();
    }
}
