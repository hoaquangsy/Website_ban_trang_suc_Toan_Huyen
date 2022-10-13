package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.VendorDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VendorDaoImpl implements VendorDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendorEntity> search(Integer page, Integer pageSize, String keyword, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM VendorEntity C");
        sql.append(createWhereQuery(keyword, values));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), VendorEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long count(Integer page, Integer pageSize, String keyword, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM VendorEntity C");
        sql.append(createWhereQuery(keyword, values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery( String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1 AND C.deleted = false");
        if (!keyword.trim().equals("")) {
            sql.append(" AND ( C.name like :name ");
            values.put("name", "%"+keyword+"%");
            sql.append(" OR C.address like :address ");
            values.put("address", "%"+keyword+"%");
            sql.append(" OR C.bankName like :bankName ");
            values.put("bankName", "%"+keyword+"%");
            sql.append(" OR C.bankNumber like :bankNumber ");
            values.put("bankNumber", "%"+keyword+"%");
            sql.append(" OR C.email like :email ");
            values.put("email", "%"+keyword+"%");
            sql.append(" OR C.phone like :phone ) ");
            values.put("phone", "%"+keyword+"%");
        }

        return sql.toString();
    }
    private StringBuilder createOrderQuery(String sortBy) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            sql.append(" order by C.").append(sortBy.replace(".", " "));
        } else {
            sql.append(" order by C.name desc ");
        }
        return sql;
    }

}
