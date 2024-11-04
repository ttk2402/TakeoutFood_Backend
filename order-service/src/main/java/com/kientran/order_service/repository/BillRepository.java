package com.kientran.order_service.repository;

import com.kientran.order_service.entity.Bill;
import com.kientran.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    Bill findByOrder(Order order);

    @Query(value =  "SELECT DATE(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s')) AS issue_date, "+
            "SUM(totalprice) AS total_for_day " +
            "FROM bill " +
            "WHERE DATE(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s')) BETWEEN STR_TO_DATE(:startDate, '%Y-%m-%d') AND STR_TO_DATE(:endDate, '%Y-%m-%d') " +
            "GROUP BY issue_date " +
            "ORDER BY issue_date ASC",
            nativeQuery = true)
    List<Object[]> calculateRevenueDayToDay(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value =  "SELECT DATE(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s')) AS issue_date, " +
            "SUM(totalprice) AS total_for_day " +
            "FROM bill " +
            "WHERE DATE(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s')) >= CURDATE() - INTERVAL 6 DAY " + // Lấy 7 ngày gần nhất
            "GROUP BY issue_date " +
            "ORDER BY issue_date ASC",
            nativeQuery = true)
    List<Object[]> calculateRevenueLatest();


    @Query(value = "SELECT DATE_FORMAT(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s'), '%Y-%m') AS month_year, SUM(totalprice) AS total_revenue " +
            "FROM bill WHERE STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s') >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) GROUP BY month_year " +
            "ORDER BY month_year ASC;",
            nativeQuery = true)
    List<Object[]> calculateRevenueMonthLatest();

    @Query(value =  "SELECT DATE_FORMAT(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s'), '%Y-%m') AS issue_month, " +
            "SUM(totalprice) AS total_for_month " +
            "FROM bill " +
            "WHERE DATE_FORMAT(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s'), '%Y-%m') BETWEEN :startDate AND :endDate " +
            "GROUP BY issue_month " +
            "ORDER BY issue_month ASC",
            nativeQuery = true)
    List<Object[]> calculateRevenueMonthToMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value =  "SELECT YEAR(STR_TO_DATE(issuedate, '%d-%m-%Y %H:%i:%s')) AS issue_year, " +
            "SUM(totalprice) AS total_for_year " +
            "FROM bill " +
            "GROUP BY issue_year " +
            "ORDER BY issue_year ASC LIMIT 3;",
            nativeQuery = true)
    List<Object[]> calculateRevenueLastYear();

}
