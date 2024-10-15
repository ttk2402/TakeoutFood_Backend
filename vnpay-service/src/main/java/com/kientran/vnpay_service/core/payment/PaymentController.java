package com.kientran.vnpay_service.core.payment;

import com.kientran.vnpay_service.core.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("${spring.application.api-prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    @ResponseBody
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public String payCallbackHandler(HttpServletRequest request, Model model) {
        String status = request.getParameter("vnp_ResponseCode");
        String transactionId = request.getParameter("vnp_TxnRef");
        String amount = request.getParameter("vnp_Amount");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String bankCode = request.getParameter("vnp_BankCode");
        String payDate = request.getParameter("vnp_PayDate");
        double amountValue = 0;
        try {
            amountValue = Double.parseDouble(amount) / 100.0;
        } catch (NumberFormatException e) {
            amountValue = 0;
        }
        if ("00".equals(status)) {
            model.addAttribute("message", "Thanh toán thành công!");
            model.addAttribute("transactionId", transactionId);
            model.addAttribute("amount", String.format("%.2f VND", amountValue));
            model.addAttribute("orderInfo", orderInfo);
            model.addAttribute("bankCode", bankCode);
            model.addAttribute("payDate", payDate);
            return "success"; // Renders success.html
        } else {
            model.addAttribute("message", "Thanh toán thất bại. Vui lòng thử lại.");
            model.addAttribute("transactionId", transactionId);
            model.addAttribute("amount", String.format("%.2f VND", amountValue));
            model.addAttribute("orderInfo", orderInfo);
            model.addAttribute("bankCode", bankCode);
            model.addAttribute("payDate", payDate);
            return "failure"; // Renders failure.html
        }
    }
}
