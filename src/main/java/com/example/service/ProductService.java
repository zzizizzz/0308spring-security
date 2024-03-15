package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mapper.CompanyMapper;
import com.example.mapper.ProductCategoryMapper;
import com.example.mapper.ProductMapper;
import com.example.vo.Company;
import com.example.vo.Product;
import com.example.vo.ProductCategory;
import com.example.web.form.ProductForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final CompanyMapper companyMapper;
	private final ProductMapper productMapper;
	private final ProductCategoryMapper productCategoryMapper;
	
	/**
	 * 상품 상위 카테고리 정보를 반환한다.
	 * @return 상위 카테고리 목록
	 */
	public List<ProductCategory> getAllProductCategories(){
		return productCategoryMapper.getProductCategories();
	}
	/**
	 * 지정된 카테고리의 하위 카테고리 정보를 반환한다.
	 * @param categoryNo 상위 카테고리 번호
	 * @return	하위 카테고리 목록
	 */
	public List<ProductCategory> getAllSubProductCategories(int categoryNo){
		return productCategoryMapper.getProductCategoriesByParentCategoryNo(categoryNo);
	}
	
	
	/**
	 * 지정된 카테고리에 속하는 사유품 목록을 반환한다.
	 * @param categoryNo 카테고리번호
	 * @return 상품목록
	 */
	public List<Product> getProducts(int categoryNo) {
		return productMapper.getProductsByCategoryNo(categoryNo);
	}
	
	/**
	 * 지정된 상품번호에 해당하는 상품정보를 반환한다.
	 * @param productNo productNo 상품번호
	 * @return	상품정보
	 */
	public Product getProduct(int productNo) {
		return productMapper.getProductByNo(productNo);
	}
	public List<Company> getAllCompanies() {
		return companyMapper.getCompanies();
	}
	public void createProduct(ProductForm productForm) {
		Product product = productForm.toProduct();
		productMapper.insertProduct(product);
		
	}
	public List<Product> getAllProducts() {
		return productMapper.getAllProducts();
	}
	public void updateProduct(Product product) {
		productMapper.updateProduct(product);
	}
}
