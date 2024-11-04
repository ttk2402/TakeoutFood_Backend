package com.kientran.order_service.dto;

public class RevenueMonthDto {
    private String issueMonth;
    private Double totalForMonth;

    public RevenueMonthDto(String issueMonth, Double totalForMonth) {
        this.issueMonth = issueMonth;
        this.totalForMonth = totalForMonth;
    }

    public String getIssueMonth() {
        return issueMonth;
    }

    public void setIssueMonth(String issueMonth) {
        this.issueMonth = issueMonth;
    }

    public void setTotalForMonth(Double totalForMonth) {
        this.totalForMonth = totalForMonth;
    }

    public Double getTotalForMonth() {
        return totalForMonth;
    }
}
