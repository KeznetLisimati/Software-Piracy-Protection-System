package co.zw.santech.onlinepayments.services.dstv;

import co.zw.santech.onlinepayments.dto.Payment;
import co.zw.santech.onlinepayments.models.DSTVCharges;
import co.zw.santech.onlinepayments.models.Product;
import co.zw.santech.onlinepayments.repositories.DstvChargesRepository;
import co.zw.santech.onlinepayments.repositories.ProductRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class DstvServiceImpl implements DstvService {

    private final ProductRespository productRespository;
    private final DstvChargesRepository dstvChargesRepository;

    public DstvServiceImpl(ProductRespository productRespository,
                           DstvChargesRepository dstvChargesRepository) {
        this.productRespository = productRespository;
        this.dstvChargesRepository = dstvChargesRepository;
    }

    @Override
    public Payment calculateAmount(String bouquetId, int months, String[] addons) {
        log.info("finding product details with parameters: " + bouquetId + " " + months + "" + Arrays.toString(addons));
        Optional<Product> product = this.productRespository.findProductByProductId(bouquetId);
        double addonFee = 0.0;
        if (product.isPresent()) {
            double amount = product.get().getCost();
            if (addons != null) {
                for (String addon : addons) {
                    Optional<Product> addonProduct = this.productRespository.findProductByProductId(addon);
                    if (addonProduct.isPresent()) {
                        addonFee += addonProduct.get().getCost();
                        amount += addonFee;
                    }
                }
            }
            amount *= months;
            double charge = getChargeForMonths(months);
            return Payment.builder()
                    .amount(amount)
                    .addonFee(addonFee)
                    .commission(charge)
                    .build();
        }
        return Payment.builder()
                .amount(0.0)
                .commission(0.0)
                .build();
    }
    private double getChargeForMonths(int months) {
        Optional<DSTVCharges> charge = this.dstvChargesRepository
                .findDSTVChargesByLowerLimitLessThanEqualAndUpperLimitGreaterThanEqual(months, months);
        return charge.map(DSTVCharges::getAmount).orElse(0.0);
    }
}
