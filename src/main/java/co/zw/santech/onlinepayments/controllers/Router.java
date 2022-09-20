package co.zw.santech.onlinepayments.controllers;

import co.zw.santech.onlinepayments.repositories.ProductRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Router {

    private final ProductRespository productRespository;

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("countWebBased", this.productRespository.countByStatusEquals("Web"));
        model.addAttribute("countMobile", this.productRespository.countByStatusEquals("Mobile"));
        model.addAttribute("countDesktop", this.productRespository.countByStatusEquals("Desktop"));
        model.addAttribute("countOther", this.productRespository.countByStatusEquals("Other"));
        return "home";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "sign-in";
    }

    @GetMapping("/Signup")
    public String viewSignUpPage() {
        return "sign-up";
    }

    @GetMapping("/vr")
    public String viewVR() {
        return "virtual-reality";
    }

    @GetMapping("/tables")
    public String tables() {
        return "tables";
    }

    @GetMapping("/dstv")
    public String viewDSTVPaymentPage() {
        return "purchase";
    }

    @GetMapping("/billing")
    public String billing() {
        return "payment";
    }

    @SuppressWarnings("ConstantConditions")
    protected String fetchClientIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
        Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
        return ip;
    }
}
