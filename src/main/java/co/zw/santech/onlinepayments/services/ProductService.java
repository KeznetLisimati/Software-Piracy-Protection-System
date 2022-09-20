package co.zw.santech.onlinepayments.services;

import co.zw.santech.onlinepayments.models.Product;
import co.zw.santech.onlinepayments.repositories.ProductRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRespository productRespository;

    public void saveProduct(Product product) {
        this.productRespository.save(product);
    }

    public List<Product> findAll() {
        return this.productRespository.findByMerchantEquals("SOFTWARE");
    }

    public Product findById(String id) {
        return this.productRespository.findProductByProductId(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Product> findByType(String type) {
        return this.productRespository.findByStatusEquals(type);
    }

    public void removeProduct(Product product) {
        this.productRespository.delete(product);
    }
}
