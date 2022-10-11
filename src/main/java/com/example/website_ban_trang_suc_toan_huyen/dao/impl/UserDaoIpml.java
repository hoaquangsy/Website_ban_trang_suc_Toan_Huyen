package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.UserDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDaoIpml implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<UserEntity> search(String keyword, UserEntity.Role role, Integer page, Integer pageSize, String sortBy,Boolean status) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM UserEntity C");
        sql.append(createWhereQuery(keyword,role,status, values));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), UserEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    private String createWhereQuery( String keyword, UserEntity.Role role,Boolean status, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1 AND C.deleted = false");
        if (!keyword.trim().equals("")) {
            sql.append(" AND ( C.fullName like :name ");
            values.put("name", "%"+keyword+"%");
            sql.append(" OR C.phoneNumber like :phoneNumber ");
            values.put("phoneNumber", "%"+keyword+"%");
            sql.append(" OR C.userName like :userName ");
            values.put("username", "%"+keyword+"%");
            sql.append(" OR C.email like :email ");
            values.put("email", "%"+keyword+"%");
            sql.append(" OR C.address like :address )");
            values.put("address", "%"+keyword+"%");
        }
        if(Objects.nonNull(role)){
            sql.append(" AND  C.role = :role ");
            values.put("role", role);
        }
        if(Objects.nonNull(status)){
            sql.append(" AND  C.status = :status ");
            values.put("status", status);
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

    @Override
    public Long count(String keyword, UserEntity.Role role, Integer page, Integer pageSize, String sortBy,Boolean status) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM UserEntity C");
        sql.append(createWhereQuery(keyword,role,status, values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
