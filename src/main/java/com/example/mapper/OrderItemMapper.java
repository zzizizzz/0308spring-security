package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.OrderItem;

@Mapper
public interface OrderItemMapper {

	void insertOrderItem(OrderItem orderItem);
	List<OrderItem> getOrderItemByOrderNo(int orderNo);
}
