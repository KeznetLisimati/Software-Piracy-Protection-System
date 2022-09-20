package co.zw.santech.onlinepayments.controllers;

import co.zw.santech.onlinepayments.models.Product;
import co.zw.santech.onlinepayments.repositories.ProductRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final ProductRespository productRespository;
    String dir = "/home/sanders/sw/hsb_files/";
    //String dir = "C:/hsb_files/";

    @GetMapping("/add")
    public String addProductView(Model model) {
        model.addAttribute("product", new Product());
        return "product-add";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product, Model model) {

        log.info(product.toString());

        /*
        StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Paths.get(dir, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
         */

        product.setMerchant("SOFTWARE");

        MultipartFile productImage = product.getFile();

        try {
            byte[] bytes = productImage.getBytes();
            String name = product.getProductId() + ".zip";
            try (BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream("src/main/resources/static/assets/files/" + name))) {
                stream.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.productRespository.save(product);
        model.addAttribute("success", true);
        return "product-add";
    }
}
