package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryStatistical;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductStatistical;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.RevenueDTO;

import java.util.List;

public interface StatisticalDAO {

    List<RevenueDTO> revenueTotal(Integer year,Boolean isRepurchase);

    List<CategoryStatistical> category();

    List<CategoryStatistical> material();

    List<ProductStatistical> productTop(Integer year);

     List<CategoryStatistical> userTop(Integer year);

    Long countproductTop(Integer year);

}
