package com.example.web.dto;

import java.util.List;

import com.example.vo.Order;
import com.example.vo.OrderItem;
import com.example.vo.OrderPayment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// 모든 정보를 가져오기 위해서 dto를 만든다
public class OrderDetailDto {

	private Order order;
	private List<OrderItem> orderItems;
	private OrderPayment payment;
	
	public int getTotalItemCount() {
	      int count = 0;
	      for (OrderItem item : orderItems) {
	         count += item.getAmount();
	      }
	      return count;
	   }
	   
	   public String getDescription() {
	      return orderItems.get(0).getProduct().getName() + " 외 " + (getTotalItemCount() - orderItems.get(0).getAmount()) + "개";
	   }
}
