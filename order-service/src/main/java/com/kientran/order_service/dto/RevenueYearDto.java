package com.kientran.order_service.dto;

public class RevenueYearDto {
    private String issueYear;
    private Double totalForYear;

    public RevenueYearDto(String issueYear, Double totalForYear) {
        this.issueYear = issueYear;
        this.totalForYear = totalForYear;
    }

    public String getIssueYear() {
        return issueYear;
    }

    public Double getTotalForYear() {
        return totalForYear;
    }

    public void setIssueYear(String issueYear) {
        this.issueYear = issueYear;
    }

    public void setTotalForYear(Double totalForYear) {
        this.totalForYear = totalForYear;
    }
}
