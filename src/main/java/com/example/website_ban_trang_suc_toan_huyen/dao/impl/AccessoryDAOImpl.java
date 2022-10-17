package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.AccessoryDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
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
public class AccessoryDAOImpl implements AccessoryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AccessoryEntity> search(Integer pageIndex, Integer pageSize, String keyword, String sortBy,
                                        AccessoryStatus status) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT A FROM AccessoryEntity A");
        sql.append(createWhereQuery(keyword, values, status));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), AccessoryEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long count(Integer pageIndex, Integer pageSize, String keyword, String sortBy,
                      AccessoryStatus status) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(A) FROM AccessoryEntity A");
        sql.append(createWhereQuery(keyword, values, status));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values,
                                    AccessoryStatus status) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1 ");
        if (!keyword.trim().equals("")) {
            sql.append(" AND ( A.name like :name ");
            values.put("name", "%"+keyword+"%");
            sql.append(" OR A.color like :color )");
            values.put("color", "%"+keyword+"%");
        }
        if(Objects.nonNull(status)){
            sql.append(" AND  A.status = :status ");
            values.put("status", status);
        }
        sql.toString();
        return sql.toString();
    }
    private StringBuilder createOrderQuery(String sortBy) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by A.").append(sortBy.replace(".", " "));
        } else {
            sql.append(" order by A.lastModifiedAt desc ");
        }
        return sql;
    }

}
