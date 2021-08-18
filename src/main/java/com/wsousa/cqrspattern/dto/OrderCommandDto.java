package com.wsousa.cqrspattern.dto;

public class OrderCommandDto {

    private Long userIndex;
    private Long productIndex;

    public Long getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(Long userIndex) {
        this.userIndex = userIndex;
    }

    public Long getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(Long productIndex) {
        this.productIndex = productIndex;
    }
}
