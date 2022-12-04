package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.WaitingProductDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.WaitingProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductSearchRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class WaitingProductDAOImpl implements WaitingProductDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<WaitingProductEntity> search(WaitingProductSearchRequest waitingProductSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM WaitingProductEntity C");
        sql.append(createWhereQuery(waitingProductSearchRequest, values));
        sql.append(createOrderQuery(waitingProductSearchRequest.getSortBy()));
        Query query = entityManager.createQuery(sql.toString(), WaitingProductEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((waitingProductSearchRequest.getPageIndex() - 1) * waitingProductSearchRequest.getPageSize());
        query.setMaxResults(waitingProductSearchRequest.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(WaitingProductSearchRequest waitingProductSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM WaitingProductEntity C");
        sql.append(createWhereQuery(waitingProductSearchRequest, values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
    private String createWhereQuery( WaitingProductSearchRequest waitingProductSearchRequest, Map<String, Object> values) throws ParseException {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1");
        if (waitingProductSearchRequest.getKeyword() != null && !waitingProductSearchRequest.getKeyword().trim().equals("")) {
            sql.append(" and C.note like :keyword");
            values.put("keyword",'%' + waitingProductSearchRequest.getKeyword()+'%');
        }
        if(!ObjectUtils.isEmpty(waitingProductSearchRequest.getProductId())){
            sql.append( " and C.productId = :productId");
            values.put("productId",waitingProductSearchRequest.getProductId());
        }
        if(!ObjectUtils.isEmpty(waitingProductSearchRequest.getProductId())){
            sql.append(" and C.sizeId = :sizeId");
            values.put("sizeId",waitingProductSearchRequest.getSizeId());
        }
        if(!ObjectUtils.isEmpty(waitingProductSearchRequest.getStartDate())){
            sql.append(" and C.createAt >= :start");
            values.put("start",new SimpleDateFormat("yyyy/MM/dd").parse(waitingProductSearchRequest.getStartDate()).toInstant());
        }
        if(!ObjectUtils.isEmpty(waitingProductSearchRequest.getEndDate())){
            sql.append(" and C.createAt <= :end");
            values.put("end",new SimpleDateFormat("yyyy/MM/dd").parse(waitingProductSearchRequest.getEndDate()).toInstant());
        }
        return sql.toString();
    }
    private StringBuilder createOrderQuery(String sortBy) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        } else {
            sql.append(" order by C.createAt desc ");
        }
        return sql;
    }
}
