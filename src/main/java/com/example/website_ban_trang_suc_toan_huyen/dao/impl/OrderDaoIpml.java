package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.OrderDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Repository
public class OrderDaoIpml implements OrderDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderEntity> search(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, String startDate, String endDate, BigDecimal startPrice, BigDecimal endPrice, UUID userId, String sortBy) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM OrderEntity C");
        sql.append(createWhereQuery(keyword, values,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), OrderEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long count(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status,
                      OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, String startDate,
                      String endDate, BigDecimal startPrice, BigDecimal endPrice, UUID userId, String sortBy) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM OrderEntity C");
        sql.append(createWhereQuery(keyword, values,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        System.out.println(sql.toString());
        return (Long) query.getSingleResult();
    }
    private String createWhereQuery(String keyword, Map<String, Object> values,OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, String startDate, String endDate, BigDecimal startPrice, BigDecimal endPrice, UUID userId
                                  ) throws ParseException {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1");
        if (!keyword.trim().equals("")) {
            sql.append(" AND  C.address like :address ");
            values.put("address", "%"+keyword+"%");
        }
        if (Objects.nonNull(status)) {
            sql.append(" AND  C.status = :status ");
            values.put("status", status);
        }
        if (Objects.nonNull(orderType)) {
            sql.append(" AND C.purchaseType = :orderType ");
            values.put("orderType", orderType);
        }
        if (Objects.nonNull(payMethod)) {
            sql.append(" AND  C.paymentMethod = :payMethod ");
            values.put("payMethod", payMethod);
        }
        if (Objects.nonNull(startDate)) {
            sql.append(" AND  C.createAt >= :startDate ");
            values.put("startDate",new SimpleDateFormat("yyyy/MM/dd").parse(startDate).toInstant());
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND  C.createAt <= :endDate ");
            values.put("endDate", new SimpleDateFormat("yyyy/MM/dd").parse(endDate).toInstant());
        }
        if (Objects.nonNull(startPrice)) {
            sql.append(" AND  C.total >= :startPrice ");
            values.put("startPrice", startPrice);
        }
        if (Objects.nonNull(endPrice)) {
            sql.append(" AND  C.total <= :endPrice ");
            values.put("endPrice", endPrice);
        }
        if (Objects.nonNull(userId)) {
            sql.append(" AND  C.userId = :userId ");
            values.put("userId", userId);
        }

        return sql.toString();
    }
    private StringBuilder createOrderQuery(String sortBy) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        }else{
            sql.append(" order by C.createAt desc");
        }
        return sql;
    }
}
