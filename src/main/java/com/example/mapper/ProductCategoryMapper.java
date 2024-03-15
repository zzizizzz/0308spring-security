package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.ProductCategory;
@Mapper
public interface ProductCategoryMapper {

	List<ProductCategory> getProductCategories();
	List<ProductCategory> getProductCategoriesByParentCategoryNo(int categoryNo);
	ProductCategory getProductCategoryByNo(int no);
	
}

