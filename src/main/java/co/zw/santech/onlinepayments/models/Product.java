package co.zw.santech.onlinepayments.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String productId;
    private String productName;
    private double cost;
    private double commission;
    private String status;
    private boolean dstvAddon;
    private String merchant;
    @Transient
    private MultipartFile file;}
