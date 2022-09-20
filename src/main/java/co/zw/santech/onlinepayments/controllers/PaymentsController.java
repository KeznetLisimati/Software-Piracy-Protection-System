package co.zw.santech.onlinepayments.controllers;

import co.zw.santech.onlinepayments.models.Transaction;
import co.zw.santech.onlinepayments.repositories.TransactionRepository;
import co.zw.santech.onlinepayments.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentsController {

    private final PaymentService paymentService;
    private final TransactionRepository transactionRepository;

    @PostMapping
    public void makePayment(@RequestBody Transaction transaction, HttpServletRequest request, Principal principal) {
        if (principal.getName() == null) {
            //  return "redirect:/login";
        } else {
            String un = principal.getName();
            log.info("User::::" + un);
            this.paymentService.makePayment(transaction, un, request);
        }
    }

    @GetMapping("/history")
    public String myTransactions(Principal principal, Model model) {
        String un = principal.getName();
        List<Transaction> myTransactions = this.transactionRepository.findByUser(un);
        model.addAttribute("myTransactions", myTransactions);
        return "myTransactions";
    }
}
