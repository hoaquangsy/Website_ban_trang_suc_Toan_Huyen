package com.example.website_ban_trang_suc_toan_huyen.dao.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.StatisticalDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryStatistical;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductStatistical;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.RevenueDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StatisticalDAOIpml implements StatisticalDAO {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RevenueDTO> revenueTotal(Integer year,Boolean isRepurchase) {
        StringBuilder sql = new StringBuilder(
                "SELECT month(o.createAt) AS month,sum(o.total) as total, SUM(od.quantity) as quantity\n" +
                " FROM OrderEntity o inner JOIN OrderDetailEntity od on o.id = od.orderId\n" +
                " where year(o.createAt) = :year and o.isRepurchase = :isRepurchase and o.status = 'DA_GIAO'\n" +
                " group by month(o.createAt)");
      List<Object[]> objects =  this.entityManager.createQuery(sql.toString(),Object[].class).setParameter("year",year).setParameter("isRepurchase",isRepurchase).getResultList();
      List<RevenueDTO> revenueDTOS = objects.stream().map(objects1 -> new RevenueDTO( (Integer) objects1[0] ,new BigDecimal(objects1[1]+""),Integer.parseInt(objects1[2]+"")))
              .collect(Collectors.toList());
     List<Integer> months = revenueDTOS.stream().map(revenueDTO -> Integer.parseInt(revenueDTO.getMonth()+"")).collect(Collectors.toList());
     List<Integer> moth = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
     if(year == LocalDate.now().getYear()){
         int month = LocalDate.now().getMonthValue();
         for (int i = month +1;i <= 12;i++){
             moth.remove(i-1);
         }
     }
     moth.removeAll(months);
     moth.forEach(integer -> {
         revenueDTOS.add(new RevenueDTO(integer, new BigDecimal(0),0));
     });
        return  revenueDTOS.stream().sorted(Comparator.comparing(RevenueDTO::getMonth)).collect(Collectors.toList());
    }

    @Override
    public List<CategoryStatistical>category() {
        StringBuilder sql = new StringBuilder(
                "select c.name,sum(od.quantity)\n" +
                        "from CategoryEntity c left join ProductEntity p on c.categoryId = p.categoryId\n" +
                        " left join OrderDetailEntity od on od.productId = p.productId and od.orderId in (select o.id from OrderEntity o where o.isRepurchase = false and o.status = 'DA_GIAO')\n" +
                        "group by c.categoryId");
        List<Object[]> objects =  this.entityManager.createQuery(sql.toString(),Object[].class).getResultList();
        List<CategoryStatistical> revenueDTOS = objects.stream().map(objects1 -> new CategoryStatistical(objects1[0]+"",objects1[1] != null ?(Long) objects1[1] :0)).collect(Collectors.toList());

        return revenueDTOS;
    }

    @Override
    public List<CategoryStatistical> material() {
        StringBuilder sql = new StringBuilder(
                "select c.materialName,sum(od.quantity)\n" +
                        "from MaterialEntity c left join ProductEntity p on c.materialId = p.materialId\n" +
                        " left join OrderDetailEntity od on od.productId = p.productId and od.orderId in (select o.id from OrderEntity o where o.isRepurchase = false and o.status = 'DA_GIAO')\n" +
                        "group by c.materialId");
        List<Object[]> objects =  this.entityManager.createQuery(sql.toString(),Object[].class).getResultList();
        List<CategoryStatistical> revenueDTOS = objects.stream().map(objects1 -> new CategoryStatistical(objects1[0]+"",objects1[1] != null ?(Long) objects1[1] :0)).collect(Collectors.toList());

        return revenueDTOS;
    }

    @Override
    public List<ProductStatistical> productTop(Integer year) {
        String sql = "select p.nameProduct,sum(o.quantity) from OrderDetailEntity o\n" +
                " left join ProductEntity p on o.productId = p.productId where o.orderId in (SELECT o.id FROM OrderEntity o \n" +
                " where year(o.createAt) = :year and o.isRepurchase = false and o.status = 'DA_GIAO')\n" +
                " group by o.productId order by  sum(o.quantity) desc " ;
        List<Object[]> objects =  this.entityManager.createQuery(sql,Object[].class).setParameter("year",year).getResultList();
        List<ProductStatistical> revenueDTOS = objects.stream().map(objects1 -> new ProductStatistical(objects1[0]+"",objects1[1] != null ?(Long) objects1[1] :0)).collect(Collectors.toList());
        revenueDTOS = revenueDTOS.stream().limit(5).sorted(Comparator.comparing(ProductStatistical::getQuantity)).collect(Collectors.toList());
        return revenueDTOS;
    }

    @Override
    public List<CategoryStatistical> userTop(Integer year) {
        String sql = "SELECT u.fullName,Sum(od.quantity) \n" +
                "FROM OrderDetailEntity od join OrderEntity o  on o.id = od.orderId\n" +
                "                     join UserEntity u on u.userId = o.userId\n" +
                "where o.status = 'DA_GIAO' and o.isRepurchase = false and year(o.createAt) = :year\n" +
                "group by o.userId order by Sum(od.quantity) desc" ;
        List<Object[]> objects =  this.entityManager.createQuery(sql,Object[].class).setParameter("year",year).getResultList();
        List<CategoryStatistical> revenueDTOS = objects.stream().map(objects1 -> new CategoryStatistical(objects1[0]+"",objects1[1] != null ?(Long) objects1[1] :0)).collect(Collectors.toList());
        revenueDTOS = revenueDTOS.stream().limit(5).sorted(Comparator.comparing(CategoryStatistical::getQuantity)).collect(Collectors.toList());
        return revenueDTOS;
    }

    @Override
    public Long countproductTop(Integer year) {
        String sql = "select p.nameProduct,sum(o.quantity) from OrderDetailEntity o\n" +
                " left join ProductEntity p on o.productId = p.productId where o.orderId in (SELECT o.id FROM OrderEntity o \n" +
                " where year(o.createAt) = :year and o.isRepurchase = false and o.status = 'DA_GIAO')\n" +
                " group by o.productId order by  sum(o.quantity) desc " ;
        List<Object[]> objects =  this.entityManager.createQuery(sql,Object[].class).setParameter("year",year).getResultList();
        return objects.stream().map(objects1 ->(Long) objects1[1]).reduce(0L, Long::sum);
    }
}
