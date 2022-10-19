package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.MaterialDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class MaterialDaoIpml implements MaterialDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MaterialEntity> search(String keyword, Integer pageIndex, Integer pageSize,
                                       MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                                       BigDecimal startPrice, BigDecimal endPrice,String sortBy) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM MaterialEntity C");
        sql.append(createWhereQuery(keyword,values,type,status,startPrice,endPrice));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), MaterialEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long count(String keyword, Integer pageIndex, Integer pageSize,
                              MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                              BigDecimal startPrice, BigDecimal endPrice,String sortBy) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM MaterialEntity C");
        sql.append(createWhereQuery(keyword, values,type,status,startPrice,endPrice));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        System.out.println(sql.toString());
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery( String keyword, Map<String, Object> values, MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                                     BigDecimal startPrice, BigDecimal endPrice) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1");
        if (!keyword.trim().equals("")) {
            sql.append(" AND ( C.materialName like :name ");
            values.put("name", "%"+keyword+"%");
            sql.append(" OR C.color like :color ) ");
            values.put("color", "%"+keyword+"%");
        }
        if(Objects.nonNull(type)){
            sql.append(" AND  C.type = :type ");
            values.put("type", type);
        }
        if(Objects.nonNull(status)){
            sql.append(" AND  C.status = :status ");
            values.put("status", status);
        }
        if(Objects.nonNull(startPrice)){
            sql.append(" AND  C.purchasePrice >= :startPrice ");
            values.put("startPrice", startPrice);
        }
        if(Objects.nonNull(endPrice)){
            sql.append(" AND  C.purchasePrice <= :endPrice ");
            values.put("endPrice", endPrice);
        }
        return sql.toString();
    }
    private StringBuilder createOrderQuery(String sortBy) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        }
        return sql;
    }
}
