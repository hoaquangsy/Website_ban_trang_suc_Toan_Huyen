package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CartDetailDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CartRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import com.example.website_ban_trang_suc_toan_huyen.service.ShoppingCartService;
import com.example.website_ban_trang_suc_toan_huyen.service.impl.ShoppingCartServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
//    @Autowired
//    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

//    @GetMapping
//    public ResponseEntity<?> getAllCart(@RequestParam(value = "page") int page,
//                                        @RequestParam(value = "page_size") int pageSize) {
//        Page<CartDetailDTO> cartItemPage = shoppingCartService.getAllCart(page, pageSize);
//        return ResponseEntity.ok(DefaultPagingResponse.success(cartItemPage));
//    }
//
//    @GetMapping("/index")
//    public ResponseEntity<?> getCartId(@RequestParam("userId") UUID userId) {
//        CartDTO cartDTO = shoppingCartService.getCartId(userId);
//        return ResponseEntity.ok(SampleResponse.success(cartDTO));
//    }

    @Operation(summary = "Get list cart_detail by userId")
    @GetMapping("/{cart_id}/{id}")
    public ResponseEntity<?> getListCartDetailByCartId(@PathVariable("cart_id") UUID cartId, @PathVariable("id") UUID  id) {
        List<CartDetailDTO> listCartDetail = shoppingCartService.getListCartDetailByCartId(cartId, id);
        return ResponseEntity.ok(SampleResponse.success(listCartDetail));
    }

    @PostMapping
    public ResponseEntity<?> createCart (@Validated @RequestBody CartRequest request) {
        CartDetailDTO cartDetailDTO = shoppingCartService.addToCart(request);

        return ResponseEntity.ok(SampleResponse.success(cartDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable("id") UUID id) {
        CartDetailDTO cartDetailDTO = shoppingCartService.deleteCart(id);
        return ResponseEntity.ok(SampleResponse.success(cartDetailDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable("id") UUID id, @RequestParam("amount") Integer amount) {
        CartDetailDTO cartDetailDTO = shoppingCartService.updateCart(id, amount);
        return ResponseEntity.ok(SampleResponse.success(cartDetailDTO));
    }
}
