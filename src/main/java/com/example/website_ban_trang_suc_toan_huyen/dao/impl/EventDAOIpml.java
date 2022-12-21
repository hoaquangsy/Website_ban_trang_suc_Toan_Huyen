package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.EventDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.EventEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventSearchRequest;
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
public class EventDAOIpml implements EventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EventEntity> search(EventSearchRequest eventSearchRequest) throws ParseException{
            Map<String, Object> values = new HashMap<>();
            StringBuilder sql = new StringBuilder("SELECT C FROM EventEntity C");
            sql.append(createWhereQuery(eventSearchRequest,values));
            sql.append(createOrderQuery(eventSearchRequest.getSortBy()));
            Query query = entityManager.createQuery(sql.toString(), EventEntity.class);
            values.forEach(query::setParameter);
            query.setFirstResult((eventSearchRequest.getPageIndex() - 1) * eventSearchRequest.getPageSize());
            query.setMaxResults(eventSearchRequest.getPageSize());
            return query.getResultList();

    }

    private String createWhereQuery(EventSearchRequest eventSearchRequest,Map<String, Object> values) throws ParseException {
        StringBuilder builder = new StringBuilder(" where 1=1 ");
        if(!eventSearchRequest.getKeyword().trim().equals("")){
            builder.append(" and ( C.name like :reason");
            values.put("reason","%"+eventSearchRequest.getKeyword()+"%");
            builder.append(" or  C.code like :code)");
            values.put("code","%"+eventSearchRequest.getKeyword()+"%");
        }
        if (Objects.nonNull(eventSearchRequest.getStartDate())) {
            builder.append(" AND  C.startDate >= :startDate ");
            values.put("startDate",new SimpleDateFormat("yyyy/MM/dd").parse(eventSearchRequest.getStartDate()).toInstant());
        }
        if (Objects.nonNull(eventSearchRequest.getEndDate())) {
            builder.append(" AND  C.endDate <= :endDate ");
            values.put("endDate", new SimpleDateFormat("yyyy/MM/dd").parse(eventSearchRequest.getEndDate()).toInstant());
        }

//       builder.append(" group by C.orderId,C.time ");
        return builder.toString();
    }

    private String createOrderQuery(String sortBy){
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        }else{
            sql.append(" order by C.startDate desc");
        }
        return sql.toString();
    }
    @Override
    public Long count(EventSearchRequest eventSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM EventEntity C");
        sql.append(createWhereQuery(eventSearchRequest,values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
