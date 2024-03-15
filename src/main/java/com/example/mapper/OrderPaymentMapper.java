package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.OrderPayment;

@Mapper
public interface OrderPaymentMapper {

	void insertPayment(OrderPayment orderPayment);
	OrderPayment getOrderPaymentByOrderNo(int orderNo);
}
