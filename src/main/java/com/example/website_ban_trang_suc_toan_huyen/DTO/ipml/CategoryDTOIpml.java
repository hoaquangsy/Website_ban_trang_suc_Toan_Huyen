package com.example.website_ban_trang_suc_toan_huyen.DTO.ipml;

import com.example.website_ban_trang_suc_toan_huyen.DTO.CategoryDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;
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
public class CategoryDTOIpml implements CategoryDTO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryEntity> search(int page, int pageSize, String keyword, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT C FROM CategoryEntity C");
        sql.append(createWhereQuery(page,pageSize,keyword,sortBy, values));
        sql.append(createOrderQuery(sortBy));
        Query query = entityManager.createQuery(sql.toString(), CategoryEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long countCategory(int page, int pageSize, String keyword, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(C) FROM CategoryEntity C");
        sql.append(createWhereQuery(page,pageSize,keyword,sortBy, values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(int page, int pageSize, String keyword, String sortBy, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1 ");
        if (!keyword.equals("")) {
            sql.append(" AND C.name like :name ");
            values.put("name", "%"+keyword+"%");
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
