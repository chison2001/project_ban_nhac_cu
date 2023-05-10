package com.example.projectbanhang.model;

import java.util.List;

public class Bill {
    private int id;
    private int userId;
    private String address;
    private String phoneNumber;
    private String orderDate;
    private long tongtien;
    private List<BillDetail> billDetails;

    public List<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    public Bill() {
    }

    public Bill(int userId, String address, String phoneNumber, String orderDate, long tongtien) {
        this.userId = userId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.tongtien = tongtien;
    }

    public Bill(int id, int userId, String address, String phoneNumber, String orderDate, long tongtien) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.tongtien = tongtien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }
}
