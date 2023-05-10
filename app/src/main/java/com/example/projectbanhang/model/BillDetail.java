package com.example.projectbanhang.model;

public class BillDetail {
    private int billId;
    private int productId;
    private int count;
    private Long price;

    public BillDetail(int billId, int productId, int count, Long price) {
        this.billId = billId;
        this.productId = productId;
        this.count = count;
        this.price = price;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
