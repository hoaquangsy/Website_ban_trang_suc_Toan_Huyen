package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CartDetailDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CartRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.GetCartResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(
        description = "Cart controller",
        name = "Các api về giỏ hàng"
)
@CrossOrigin("*")
@RequestMapping(value = "api/v1/cart")
@RestController
public class CartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(summary = "Get list cart_detail by userId")
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getListCartDetailByCartId(@PathVariable("user_id") UUID userId) {
        GetCartResponse getCartResponse = shoppingCartService.getListCartDetailByCartId(userId);

        return ResponseEntity.ok(SampleResponse.success(getCartResponse));
    }

    @PostMapping
    public ResponseEntity<?> createCart (@Validated @RequestBody CartRequest request) {
        CartDetailDTO cartDetailDTO = shoppingCartService.addToCart(request);

        return ResponseEntity.ok(SampleResponse.success(cartDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable("id") UUID id) {
        shoppingCartService.deleteCart(id);
        return ResponseEntity.ok(SampleResponse.success());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable("id") UUID id, @RequestParam("amount") Integer amount) {
        CartDetailDTO cartDetailDTO = shoppingCartService.updateCart(id, amount);
        return ResponseEntity.ok(SampleResponse.success(cartDetailDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartDetailByUserId(@PathVariable("id") UUID id) {
        CartDetailDTO cartDetailDTO = shoppingCartService.deleteCartDetailByUserId(id);
        return ResponseEntity.ok(SampleResponse.success(cartDetailDTO));
    }
}
