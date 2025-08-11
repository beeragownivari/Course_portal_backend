package com.portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {

    public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Orders(Integer orderId, String name, String email, Integer amount, String orderStatus,
			String razorpayOrderId) {
		super();
		this.orderId = orderId;
		this.name = name;
		this.email = email;
		this.amount = amount;
		this.orderStatus = orderStatus;
		this.razorpayOrderId = razorpayOrderId;
	}
	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}
	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private String name;
    private String email;
    private Integer amount;
    private String orderStatus;
    private String razorpayOrderId;

    // Getters and Setters
}
