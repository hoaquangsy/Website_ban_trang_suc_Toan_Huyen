package com.example.website_ban_trang_suc_toan_huyen.dao.impl;


import com.example.website_ban_trang_suc_toan_huyen.dao.ExchangeDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeSearchRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ExchangeDAOImpl implements ExchangeDAO {


    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ExchangeEntity> search(ExchangeSearchRequest exchangeSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM ExchangeEntity C");
        sql.append(createWhereQuery(exchangeSearchRequest,values));
        sql.append(createOrderQuery(exchangeSearchRequest.getSortBy()));
        Query query = entityManager.createQuery(sql.toString(), ExchangeEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((exchangeSearchRequest.getPageIndex() - 1) * exchangeSearchRequest.getPageSize());
        query.setMaxResults(exchangeSearchRequest.getPageSize());
        return query.getResultList();
    }
   private String createWhereQuery(ExchangeSearchRequest exchangeSearchRequest,Map<String, Object> values) throws ParseException {
       StringBuilder builder = new StringBuilder(" where 1=1 ");
       if(!exchangeSearchRequest.getKeyword().trim().equals("")){
          builder.append(" and  C.reason like :reason");
          values.put("reason","%"+exchangeSearchRequest.getKeyword()+"%");
       }
       if(Objects.nonNull(exchangeSearchRequest.getOrderId())){
           builder.append(" and C.orderId = :orderId");
           values.put("orderId",exchangeSearchRequest.getOrderId());
       }
       if(Objects.nonNull(exchangeSearchRequest.getStatus())){
           builder.append(" and C.status = :status");
           values.put("status",exchangeSearchRequest.getStatus());
       }
       if (Objects.nonNull(exchangeSearchRequest.getStartDate())) {
           builder.append(" AND  C.createAt >= :startDate ");
           values.put("startDate",new SimpleDateFormat("yyyy/MM/dd").parse(exchangeSearchRequest.getStartDate()).toInstant());
       }
       if (Objects.nonNull(exchangeSearchRequest.getEndDate())) {
           builder.append(" AND  C.createAt <= :endDate ");
           values.put("endDate", new SimpleDateFormat("yyyy/MM/dd").parse(exchangeSearchRequest.getEndDate()).toInstant());
       }
//       builder.append(" group by C.orderId,C.time ");
       return builder.toString();
   }
    private String createOrderQuery(String sortBy){
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        }else{
            sql.append(" order by C.createAt desc");
        }
        return sql.toString();
    }

    @Override
    public Long count(ExchangeSearchRequest exchangeSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM ExchangeEntity C");
        sql.append(createWhereQuery(exchangeSearchRequest,values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
