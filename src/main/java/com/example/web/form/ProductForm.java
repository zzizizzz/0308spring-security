package com.example.web.form;

import com.example.vo.Company;
import com.example.vo.Product;
import com.example.vo.ProductCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {

	private Integer categoryNo;
	private String name;
	private Integer companyNo;
	private Integer price;
	private Integer amount;
	private String description;
	
	public Product toProduct() {
		Product product = new Product();
		product.setCategory(new ProductCategory(categoryNo));
		product.setCompany(new Company(companyNo));
		product.setName(name);
		product.setPrice(price);
		product.setStock(amount);
		product.setDescription(description);
		
		return product;
	}
}
