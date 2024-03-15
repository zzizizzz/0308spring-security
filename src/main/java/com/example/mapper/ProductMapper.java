package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.Product;

@Mapper
public interface ProductMapper {
	
	List<Product> getProductsByCategoryNo(int categoryNo);
	Product getProductByNo(int no);
	void insertProduct(Product product);
	List<Product> getAllProducts();
	void updateProduct(Product product);
}
