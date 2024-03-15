package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.Order;
import com.example.web.dto.OrderListDto;

@Mapper
public interface OrderMapper {

	void insertOrder(Order order);
	List<OrderListDto> getOrdersByUserNo(int  userNo);
	Order getOrderByNo(int orderNo);
}
