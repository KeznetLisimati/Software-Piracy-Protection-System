package co.zw.santech.onlinepayments.services.dstv;

import co.zw.santech.onlinepayments.models.Payment;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DstvMsgPublisher {

    private final ProducerTemplate producerTemplate;
    private final Gson gson = new Gson();

    public DstvMsgPublisher(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public int sendDSTVMsgToQueue(Payment payment) {
        try {
            producerTemplate.sendBody("activemq:queue:payment.queue", gson.toJson(payment));
            log.info("Message sent to Queue:::: {}", gson.toJson(payment));
            return 10;
        } catch (Exception e) {
            log.error("Could not send Message to Queue!:: {}", gson.toJson(payment));
            return 99;
        }
    }
}
