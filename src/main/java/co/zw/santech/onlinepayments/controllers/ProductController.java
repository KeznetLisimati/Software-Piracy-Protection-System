package co.zw.santech.onlinepayments.controllers;

import co.zw.santech.onlinepayments.models.Product;
import co.zw.santech.onlinepayments.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public void saveProduct(@RequestBody Product product) {
        this.productService.saveProduct(product);
    }

    @GetMapping
    public String findAll(Model model) {
        List<Product> productList = this.productService.findAll();
        model.addAttribute("products", productList);
        return "tables";
    }

    @GetMapping("/findByType/{type}")
    public String findByType(@PathVariable String type, Model model) {
        List<Product> productList = this.productService.findByType(type);
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/findProduct{id}")
    public String findProductById(@RequestParam("id") @PathVariable String id, Model model) {
        Product product = this.productService.findById(id);
        model.addAttribute("product", product);
        log.info("Product::::" + product.getProductName());
        return "transaction";
    }

    @GetMapping("/removeProduct{id}")
    public String removeProductById(@RequestParam("id") @PathVariable String id) {
        Product product = this.productService.findById(id);
        this.productService.removeProduct(product);
        return "redirect:/api/v1/product";
    }
}
