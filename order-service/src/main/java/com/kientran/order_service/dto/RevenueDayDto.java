package com.kientran.order_service.dto;

public class RevenueDayDto {
    private String issueDate;
    private Double totalForDay;

    public RevenueDayDto(String issueDate, Double totalForDay) {
        this.issueDate = issueDate;
        this.totalForDay = totalForDay;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Double getTotalForDay() {
        return totalForDay;
    }

    public void setTotalForDay(Double totalForDay) {
        this.totalForDay = totalForDay;
    }

}
