package co.zw.santech.onlinepayments.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationResponse {
    private boolean successful;
    private String billerId;
    private String customerAccount;
    private String responseDetails;
}
