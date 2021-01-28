package com.project.product.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="M_PRODUCT")
public class MProductEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "product_price")
	private double productPrice;
	
	@Column(name = "store_name")
	private String storeName;
	
	@Column(name = "brand_name")
	private String brandName;
	
	@Column(name = "category_names")
	private String categoryNames;
	
	@Column(name = "description")
	private String description;

}
