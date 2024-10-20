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
        // Xây dựng URL chuyển hướng
        String redirectUrl = "http://localhost:5173"; // Địa chỉ ứng dụng React của bạn
        if ("00".equals(status)) {
            // Nếu thanh toán thành công
            redirectUrl += "/thanh-toan-thanh-cong"
                    + "?transactionId=" + transactionId
                    + "&amount=" + String.format("%.2f VND", amountValue)
                    + "&orderInfo=" + orderInfo
                    + "&bankCode=" + bankCode
                    + "&payDate=" + payDate;
        } else {
            // Nếu thanh toán thất bại
            redirectUrl += "/thanh-toan-that-bai"
                    + "?transactionId=" + transactionId
                    + "&amount=" + String.format("%.2f VND", amountValue)
                    + "&orderInfo=" + orderInfo
                    + "&bankCode=" + bankCode
                    + "&payDate=" + payDate;
        }
        // Chuyển hướng đến ứng dụng React
        return "redirect:" + redirectUrl;
    }
}
