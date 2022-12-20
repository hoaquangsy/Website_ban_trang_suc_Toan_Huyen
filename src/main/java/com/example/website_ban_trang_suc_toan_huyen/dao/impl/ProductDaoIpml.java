package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.ProductDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class ProductDaoIpml implements ProductDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductEntity> search(Integer pageIndex,
                                       Integer pageSize,
                                       String keyword,
                                       ProductEntity.StatusEnum status,
                                       UUID materialId,
                                       UUID vendorId,
                                       UUID categoryId,
                                       UUID accessoryId,
                                       BigDecimal startPrice,
                                       BigDecimal endPrice,
                                       String sortBy,
                                      ProductEntity.ProductGender gender) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("select  p from ProductEntity p ");
        sql.append(createWhereQuery(values,keyword,status,materialId,vendorId,categoryId,accessoryId,startPrice,endPrice,gender));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), ProductEntity.class);
        values.forEach(query::setParameter);
        System.out.println(sql.toString() + "       "  + values);
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long count(Integer pageIndex,
                      Integer pageSize,
                      String keyword,
                      ProductEntity.StatusEnum status,
                      UUID materialId,
                      UUID vendorId,
                      UUID categoryId,
                      UUID accessoryId,
                      BigDecimal startPrice,
                      BigDecimal endPrice,
                      String sortBy,
                      ProductEntity.ProductGender gender) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(p) FROM ProductEntity p ");
        sql.append(createWhereQuery(values,keyword,status,materialId,vendorId,categoryId,accessoryId,startPrice,endPrice,gender));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        System.out.println(sql.toString());
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(Map<String,Object> values,
                                     String keyword,
                                     ProductEntity.StatusEnum status,
                                     UUID materialId,
                                     UUID vendorId,
                                     UUID categoryId,
                                     UUID accessoryId,
                                     BigDecimal startPrice,
                                     BigDecimal endPrice,
                                    ProductEntity.ProductGender gender) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1 ");
        if (!keyword.trim().equals("")) {
            sql.append(" AND ( p.nameProduct like :name ");
            values.put("name", "%"+keyword+"%");
            sql.append(" OR p.code like :code  ");
            values.put("code", "%"+keyword+"%");
            sql.append(" OR p.note like :note ) ");
            values.put("note", "%"+keyword+"%");
        }
        if(Objects.nonNull(vendorId)){
            sql.append(" AND  p.vendorId = :vendorId ");
            values.put("vendorId", vendorId);
        }
        if(Objects.nonNull(materialId)){
            sql.append(" AND  p.materialId = :materialId ");
            values.put("materialId", materialId);
        }
        if(Objects.nonNull(categoryId)){
            sql.append(" AND  p.categoryId = :categoryId ");
            values.put("categoryId", categoryId);
        }
        if(Objects.nonNull(accessoryId)){
            sql.append(" AND  p.accessoryId = :accessoryId ");
            values.put("accessoryId", accessoryId);
        }
        if(Objects.nonNull(gender)){
            sql.append(" AND  p.gender = :gender ");
            values.put("gender", gender);
        }
        if(Objects.nonNull(status)) {
            sql.append(" AND  p.status = :status ");
            values.put("status", status);
        }
        if(Objects.nonNull(startPrice)) {
            sql.append(" AND  p.productId in ( select s.productId from ProductSizeEntity s where s.purchasePrice >= :startPrice) ");
            values.put("startPrice", startPrice);
        }
        if(Objects.nonNull(endPrice)) {
            sql.append(" AND  p.productId in ( select s.productId from ProductSizeEntity s where s.purchasePrice <= :endPrice) ");
            values.put("endPrice", endPrice);
        }
        sql.append(" AND  p.deleted = :delete ");
        values.put("delete", Boolean.FALSE);
        return sql.toString();
    }
    private StringBuilder createOrderQuery(String sortBy) {
        StringBuilder sql = new StringBuilder(" ");
        if (!sortBy.trim().equals("")) {
            sql.append(" order by p.").append(sortBy.replace(".", " "));
        }else {
            sql.append(" order by p.createAt desc");
        }
        return sql;
    }
}
