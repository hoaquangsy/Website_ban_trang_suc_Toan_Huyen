package com.example.website_ban_trang_suc_toan_huyen.payload.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
public class CategoryRequest implements Serializable {
    @NotNull(message = "Tên thể loại không để trống")
    private String name;

   private  String description;

   private List<String>  properties;

}
