package co.zw.santech.onlinepayments.repositories;

import co.zw.santech.onlinepayments.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRespository extends JpaRepository<Product, String> {

    Optional<Product> findProductByProductId(String productId);

    List<Product> findByMerchantEquals(String merchant);

    List<Product> findByStatusEquals(String status);

    Long countByStatusEquals(String type);
}
