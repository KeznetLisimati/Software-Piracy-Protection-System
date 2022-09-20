package co.zw.santech.onlinepayments.services.dstv;

import co.zw.santech.onlinepayments.dto.Payment;

public interface DstvService {

    Payment calculateAmount(String bouquetId, int months, String... addons);
}
