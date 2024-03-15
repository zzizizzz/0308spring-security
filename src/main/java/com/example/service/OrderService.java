package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ShopException;
import com.example.mapper.OrderItemMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.OrderPaymentMapper;
import com.example.mapper.ProductMapper;
import com.example.mapper.UserMapper;
import com.example.vo.Order;
import com.example.vo.OrderItem;
import com.example.vo.OrderPayment;
import com.example.vo.Product;
import com.example.vo.User;
import com.example.web.dto.OrderDetailDto;
import com.example.web.dto.OrderListDto;
import com.example.web.form.OrderForm;

import lombok.RequiredArgsConstructor;

/*
 * @Transactional
 * 		- 선언적 트랜잭션처리를 지원하는 어노테이션이다.
 * 		- @Transactional 어노테이션을 지정한 인터페이스, 클래스, 메소드에 붙일수 있다.
 * 			+ 인터페이스에 지정하면 해당 인터페이스를 구현한 구현객체의 각 메소드가 실행될 때 마다 트랜잭션처리가 된다.
 * 			+ 클래스에 지정하면 해당 클래스로 생성한 객체의 각 메소드가 실행될 때 마다 트랜잭션처리가 지원된다.
 * 			+ 메소드에 지정하면 해당 메소드가 실행 될 때마다 트랜잭션처리가 지원된다.
 * 		 - 선언적 트랜잭션 처리가 지원되면 해당 메소드가 실행되기 전에 새로운 트랜잭선이 시작딘다.
 * 			+ 트랜잭션이 시작되 말은 데이터베이스에서 지금부터 실행하는 모든SQL 구문의 하나의 논리적인 그룹으로 유출준비가 되었다는 점이다.
 * 		 	+ 메소드내에서 데이터베이스 엑세스 작업을 할 때마다 해당 SQL작업을 트랜잭션에 추가한다.
 * 		 - 선언적 트랜잭션 처리가 지원되면 해당 메소드가 종료될 떄 트랜잭션을 종료한다.
 * 			+ 해당 메소드를 실행하면서RuntimeException의 하위 예외가 발생하면 트랜잭션 추가로 모든 작업에 대해서 rollback을 실행한다.
 * 			+ 해당 메소드를 실행하면서 예외가 발생하않으며 해당 트랜잭션에 추가된 모든작업에 대해서 commit을 실행한다. 
 */
@Service
@RequiredArgsConstructor
@Transactional
// 트랜잭션널은 서비스에 붙인다.
public class OrderService {
	
		private final ProductMapper productMapper;
		private final OrderMapper orderMapper;
		private final OrderItemMapper orderItemMapper;
		private final OrderPaymentMapper orderPaymentMapper;
		private final UserMapper	userMapper;
		//누가 주문한걸 알기위해서 userNo
		public Order saveOrder(OrderForm orderForm, String userId) {
			// 주문 정보를 저장한다.
			User user =  userMapper.getUserById(userId);
			
			Order order = new Order();
			order.setUser(user);
			order.setTotalPrice(orderForm.getTotalPrice());
			
			orderMapper.insertOrder(order);
			// 주문 상품정보를 저장한다.
			Product product = productMapper.getProductByNo(orderForm.getProductNo());
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderNo(order.getNo());
			orderItem.setAmount(orderForm.getAmount());
			orderItem.setPrice(product.getPrice());
			orderItem.setProduct(product);
			
			orderItemMapper.insertOrderItem(orderItem);
			// 결재 정보를 저장한다.
			OrderPayment orderPayment = new OrderPayment();
			orderPayment.setOrder(order);
			orderPayment.setType(orderForm.getPayType());
			orderPayment.setAccNo(orderForm.getCardno());
			orderPayment.setMonths(orderForm.getMonths());
			orderPayment.setAmount(orderForm.getTotalPrice());
			
			orderPaymentMapper.insertPayment(orderPayment);
			
			return order; //주문번호를 알기위해
		}
		public OrderDetailDto getOrderDeatail(int orderNo, String userId) {
			OrderDetailDto dto = new OrderDetailDto();
			
			User user = userMapper.getUserById(userId);
			Order order = orderMapper.getOrderByNo(orderNo); // 주문번호를 조회
			if(user.getNo() !=order.getUser().getNo()) {
				throw new ShopException("다른 사용자의 주문정보를 조회할수 없습니다");
			}
			
			List<OrderItem> orderItems = orderItemMapper.getOrderItemByOrderNo(orderNo);
			OrderPayment payment = orderPaymentMapper.getOrderPaymentByOrderNo(orderNo);
			
			// 담은것 
			dto.setOrder(order);
			dto.setOrderItems(orderItems);
			dto.setPayment(payment);
			
			return dto;
			
		}
		public List<OrderListDto> getMyOrders(String userId) {
			User user = userMapper.getUserById(userId);
			List<OrderListDto> dtos = orderMapper.getOrdersByUserNo(user.getNo());
			// user 컨트롤러로 반환
			return dtos;
		}
		
}
