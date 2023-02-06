package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.config.Config;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PaymentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;

@Tag(
        description = "PaymentController",
        name = "Thanh toan hoa don"
)
@CrossOrigin("*")
@RestController
public class PaymentController {
//    @Autowired
//    PaymentService paymentService;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    OrderService orderService;
//
//    @Autowired
//    ServicePackService servicePackService;
//
//    @Autowired
//    EmailService emailService;
//
//    @Autowired
//    ConfigService configService;
//
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    HttpServletRequest req;

    @GetMapping("/test-vnpay")
    public ResponseEntity<PaymentResponse> test() throws IOException {
//        Phiên bản api mà merchant kết nối. Phiên bản hiện tại là : 2.0.1 và 2.1.0
        String vnp_Version = "2.1.0";

//        Mã API sử dụng, mã cho giao dịch thanh toán là: pay
        String vnp_Command = "pay";

//        Thông tin mô tả nội dung thanh toán
        String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");

//        Mã danh mục hàng hóa
        String orderType = req.getParameter("ordertype");

        /*Mã tham chiếu của giao dịch tại hệ thống của merchant.
        Mã này là duy nhất dùng để phân biệt các đơn hàng gửi sang VNPAY.
        Không được trùng lặp trong ngày.*/
        String vnp_TxnRef = Config.getRandomNumber(8);

//        Địa chỉ IP của khách hàng thực hiện giao dịch
        String vnp_IpAddr = Config.getIpAddress(req);

//        Mã website của merchant trên hệ thống của VNPAY
        String vnp_TmnCode = Config.vnp_TmnCode;

        String amount = req.getParameter("amount") ;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = req.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

//        Ngôn ngữ giao diện hiển thị
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }

        // vnp_ReturnUrl thông báo kết quả giao dịch khi Khách hàng kết thúc thanh toán
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
        vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
        String fullName = (req.getParameter("txt_billing_fullname")).trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
        vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
        vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
        vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
        }

        // Invoice
        vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
        vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
        vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
        vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
        vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
        vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
        vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();

//        Mã kiểm tra (checksum) để đảm bảo dữ liệu của giao dịch không bị thay đổi trong quá trình chuyển từ VNPAY về Website TMĐT.
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        return new ResponseEntity<PaymentResponse>(
                PaymentResponse.builder().paymentUrl(paymentUrl).build(),
                HttpStatus.OK);
    }

//    @GetMapping("success")
//    public String success(Model model) {
//        String amount = req.getParameter("vnp_Amount");
//        model.addAttribute("vnp_Amount", amount);
//        return "success";
//    }

}
