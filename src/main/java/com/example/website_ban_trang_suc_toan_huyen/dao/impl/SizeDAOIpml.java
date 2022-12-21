package com.example.website_ban_trang_suc_toan_huyen.dao.impl;


import com.example.website_ban_trang_suc_toan_huyen.dao.SizeDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.SizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeSearchRequest;
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
public class SizeDAOIpml implements SizeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SizeEntity> search(SizeSearchRequest sizeSearchRequest) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM SizeEntity C");
        sql.append(createWhereQuery(sizeSearchRequest,values));
        sql.append(createOrderQuery(sizeSearchRequest.getSortBy()));
        Query query = entityManager.createQuery(sql.toString(), SizeEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((sizeSearchRequest.getPageIndex() - 1) * sizeSearchRequest.getPageSize());
        query.setMaxResults(sizeSearchRequest.getPageSize());
        return query.getResultList();
    }
    private String createWhereQuery(SizeSearchRequest sizeSearchRequest, Map<String, Object> values) {
        StringBuilder builder = new StringBuilder(" where 1=1 ");
        if(!sizeSearchRequest.getKeyword().trim().equals("")){
            builder.append(" and  C.size like :reason");
            values.put("reason","%"+sizeSearchRequest.getKeyword()+"%");
        }


//       builder.append(" group by C.orderId,C.time ");
        return builder.toString();
    }
    private String createOrderQuery(String sortBy){
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        }else{
            sql.append(" order by C.size desc");
        }
        return sql.toString();
    }
    @Override
    public Long count(SizeSearchRequest sizeSearchRequest) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM SizeEntity C");
        sql.append(createWhereQuery(sizeSearchRequest,values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
