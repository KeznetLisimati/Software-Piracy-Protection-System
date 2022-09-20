package co.zw.santech.onlinepayments.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productId;
    private String customerName;
    private String cardNo;
    private String fileFormat;
    private int qty;
    private double charge;
    private double amount;
    private String user;
    private String licenseKey;
}
