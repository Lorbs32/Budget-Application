package com.budget.app.controller;

import java.math.BigDecimal;

public class LineItemRequestDTO {

    private String lineItemName;
    private BigDecimal plannedAmount;
    private Boolean isIncome;
    private Integer categoryId;

    public LineItemRequestDTO(){}

    public LineItemRequestDTO(String lineItemName, BigDecimal plannedAmount, Boolean isIncome, Integer categoryId) {
        this.lineItemName = lineItemName;
        this.plannedAmount = plannedAmount;
        this.isIncome = isIncome;
        this.categoryId = categoryId;
    }
        // Getters and Setters
        public String getLineItemName() {
            return lineItemName;
        }

        public void setLineItemName(String lineItemName) {
            this.lineItemName = lineItemName;
        }

        public BigDecimal getPlannedAmount() {
            return plannedAmount;
        }

        public void setPlannedAmount(BigDecimal plannedAmount) {
            this.plannedAmount = plannedAmount;
        }

        public Boolean getIsIncome() {
            return isIncome;
        }

        public void setIsIncome(Boolean isIncome) {
            this.isIncome = isIncome;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }
}
