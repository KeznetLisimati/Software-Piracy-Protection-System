package co.zw.santech.onlinepayments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private java.lang.String accountNumber;
    private java.lang.String reference;
    private double amount;
    private java.lang.String branchCode;
    private java.lang.String captureTime;
    private java.lang.String chargeResponseCode;
    private double commission;
    private double addonFee;
    private java.lang.String currency;
    private java.lang.String customerBankAccount;
    private java.lang.String customerMobile;
    private java.lang.String customerName;
    private java.lang.String customerPaymentDetails1;
    private java.lang.String customerPaymentDetails2;
    private java.lang.String customerPaymentDetails3;
    private java.lang.String customerUtilityAccount;
    private java.lang.String depositor;
    private java.lang.String email;
    private int invoicePeriod;
    private java.lang.String mainResponseCode;
    private java.lang.String merchantId;
    private java.lang.String methodOfPayment;
    private java.lang.String narrative;
    private java.lang.String operatorId;
    private java.lang.String paymentDate;
    private long referenceNumber;
    private java.util.Calendar requesttime;
    private java.util.Calendar responsetime;
    private boolean showActions;
    private java.lang.String status;
    private java.lang.String terminalId;
    private java.util.Calendar timestamp;
    private java.lang.String vendorBranch;
    private java.lang.String vendorId;
}
