package co.zw.santech.onlinepayments.controllers;

import co.zw.santech.onlinepayments.dto.Payment;
import co.zw.santech.onlinepayments.models.Transaction;
import co.zw.santech.onlinepayments.models.ValidationResponse;
import co.zw.santech.onlinepayments.repositories.TransactionRepository;
import co.zw.santech.onlinepayments.services.PaymentService;
import co.zw.santech.onlinepayments.services.dstv.DstvMsgPublisher;
import co.zw.santech.onlinepayments.services.dstv.DstvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

/**
 * @author sanders (Jul 1, 2022)
 */

@Slf4j
@Controller
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OnlinePaymentsController {

    private final DstvService dstvService;
    private final DstvMsgPublisher dstvMsgPublisher;
    private final PaymentService paymentService;
    private final TransactionRepository transactionRepository;


    @ResponseBody
    @GetMapping("/{cardNo}")
    public ValidationResponse validateCard(@PathVariable("cardNo") String cardNo) {
        if (cardNo.equals("00999999999")) {
            return ValidationResponse.builder()
                    .billerId("DSTV")
                    .customerAccount(cardNo)
                    .successful(Boolean.TRUE)
                    .responseDetails("Jordan Sancho")
                    .build();
        } else {
            return ValidationResponse.builder()
                    .billerId("DSTV")
                    .customerAccount(cardNo)
                    .successful(Boolean.FALSE)
                    .responseDetails("Failed to validate Card")
                    .build();
        }
    }

    @ResponseBody
    @GetMapping("/validateAmount")
    public void validateAmount(String amount) {
        try {
            amount = amount.replace(",", "");
            double amt = Double.parseDouble(amount);
            if (amt <= 0) {
                ValidationResponse.builder()
                        .responseDetails("Amount Should be greater than 0!")
                        .successful(Boolean.FALSE)
                        .build();
            }
            ValidationResponse.builder().successful(Boolean.TRUE).build();

        } catch (Exception e) {
            ValidationResponse.builder()
                    .successful(Boolean.FALSE)
                    .responseDetails(e.getMessage()).build();
        }
    }

    @ResponseBody
    @PostMapping("/calculateAmount")
    public Payment calculateAmount(@RequestBody co.zw.santech.onlinepayments.models.Payment payment) {
        return this.dstvService.calculateAmount(payment.getCustomerPaymentDetails1(),
                payment.getInvoicePeriod(), payment.getCustomerPaymentDetails2());
    }

    /**
     * to be removed
     */
    @ResponseBody
    @PostMapping("/postToDSTV")
    public int postDSTVToMerchant(@RequestBody co.zw.santech.onlinepayments.models.Payment payment) {
        return this.dstvMsgPublisher.sendDSTVMsgToQueue(payment);
    }

    @ResponseBody
    @PostMapping("/post")
    public Transaction postPaymentTest(@RequestBody co.zw.santech.onlinepayments.models.Payment payment, HttpServletRequest request, Principal principal)
            throws InterruptedException {
        log.info(payment.toString());
        String un = principal.getName();

        Transaction transaction = Transaction.builder()
                .amount(payment.getAmount())
                .cardNo(payment.getCustomerUtilityAccount())
                .charge(3.0)
                .customerName(un)
                .fileFormat(payment.getCustomerPaymentDetails3())
                .productId(payment.getCustomerPaymentDetails5())
                .qty(1)
                .build();

        this.paymentService.makePayment(transaction, un, request);

        Thread.sleep(5000);
        return transaction;
    }

    @GetMapping("/ref")
    public String completePayment(@RequestParam("id") Long id, Model model) {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow();
        log.info(transaction.toString());
        model.addAttribute("transaction", transaction);
        return "billing";
    }
}
