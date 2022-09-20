package co.zw.santech.onlinepayments.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private double		amount;
    private String		accountNumber;
    private String		currency;
    private String		customerMobile;
    private String		customerName;
    private String		customerPaymentDetails1;
    private String[]	customerPaymentDetails2;
    private String		customerPaymentDetails3;
    private Double		customerPaymentDetails4;
    private String		customerPaymentDetails5;
    private String		customerPaymentDetails6;
    private String		customerUtilityAccount;
    private String		merchantId;
    private int			invoicePeriod;
    private String		methodOfPayment;
}
