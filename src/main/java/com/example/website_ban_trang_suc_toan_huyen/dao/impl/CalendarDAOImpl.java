package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.CalendarDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CalendarSearchRequest;
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
public class CalendarDAOImpl implements CalendarDAO {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<CalendarEntity> search(CalendarSearchRequest calendarSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM CalendarEntity C");
        sql.append(createWhereQuery(calendarSearchRequest,values));
        sql.append(createOrderQuery(calendarSearchRequest.getSortBy()));
        Query query = entityManager.createQuery(sql.toString(), CalendarEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((calendarSearchRequest.getPageIndex() - 1) * calendarSearchRequest.getPageSize());
        query.setMaxResults(calendarSearchRequest.getPageSize());
        return query.getResultList();
    }
    private String createWhereQuery(CalendarSearchRequest calendarSearchRequest,Map<String, Object> values) throws ParseException {
        StringBuilder builder = new StringBuilder(" where 1=1 ");
       if(calendarSearchRequest.getKeyword() != null && !calendarSearchRequest.getKeyword().trim().equals("")){
        builder.append(" and ( C.userName like :userName");
        values.put("userName","%"+calendarSearchRequest.getKeyword()+"%");
           builder.append(" or  C.phoneNumber like :phoneNumber");
           values.put("phoneNumber","%"+calendarSearchRequest.getKeyword()+"%");
           builder.append(" or  C.email like :email )");
           values.put("email","%"+calendarSearchRequest.getKeyword()+"%");
    }
       if(Objects.nonNull(calendarSearchRequest.getProductId())){
        builder.append(" and C.productId = :productId");
        values.put("productId",calendarSearchRequest.getProductId());
    }
       if (Objects.nonNull(calendarSearchRequest.getStartDate())) {
        builder.append(" AND  C.time >= :startDate ");
        values.put("startDate",new SimpleDateFormat("yyyy/MM/dd").parse(calendarSearchRequest.getStartDate()).toInstant());
    }
       if (Objects.nonNull(calendarSearchRequest.getEndDate())) {
        builder.append(" AND  C.time <= :endDate ");
        values.put("endDate", new SimpleDateFormat("yyyy/MM/dd").parse(calendarSearchRequest.getEndDate()).toInstant());
    }
//       builder.append(" group by C.orderId,C.time ");
       return builder.toString();
}
    private String createOrderQuery(String sortBy){
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        }else{
            sql.append(" order by C.time desc");
        }
        return sql.toString();
    }

    @Override
    public Long count(CalendarSearchRequest calendarSearchRequest) throws ParseException {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM CalendarEntity C");
        sql.append(createWhereQuery(calendarSearchRequest,values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
