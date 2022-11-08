package com.example.website_ban_trang_suc_toan_huyen.dao;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;

import java.sql.*;
import java.util.UUID;
import java.util.Date;

public class ProductDao {
    String query = "insert into product (product_id , category_id, vendor_id, code,name_product,weight,purchase_price,sale_price,status,note,salary,accessory_id,gender,event_id,material_id,create_at,create_by) values (?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbantrangsuc", "root", "123456");
    PreparedStatement pstmt = conn.prepareStatement(query);
    public ProductDao() throws SQLException {

    }
    public PreparedStatement saveProduct(ProductEntity product) throws SQLException {
        pstmt.setString(1, UUID.randomUUID().toString());
        pstmt.setString(2, product.getCategoryId().toString());
        pstmt.setString(3, product.getVendorId().toString());
        pstmt.setString(4, product.getCode());
        pstmt.setString(5, product.getNameProduct());
        pstmt.setFloat(6,product.getWeight());
        pstmt.setBigDecimal(7,product.getPurchasePrice());
        pstmt.setBigDecimal(8,product.getSalePrice());
        pstmt.setString(9,"ACTIVE");
        pstmt.setString(10,product.getNote());
        pstmt.setBigDecimal(11,product.getSalary());
        pstmt.setString(12,product.getAccessoryId());
        pstmt.setString(13,product.getGender());
        pstmt.setString(14,product.getEventId().toString());
        pstmt.setString(15,product.getMaterialId().toString());

        pstmt.setTimestamp(16, new Timestamp(new Date().getTime()));
        pstmt.setString(17,"");
        return pstmt;
    }
    public boolean closeConnection(PreparedStatement psttm, Connection conn) throws SQLException {
        if (psttm != null) psttm.close();
        if (conn != null) conn.close();
        return true;
    }
}
