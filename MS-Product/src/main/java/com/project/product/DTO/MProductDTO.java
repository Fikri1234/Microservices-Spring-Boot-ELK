package com.project.product.DTO;

import com.project.product.Entity.MProductEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MProductDTO {
	
	private Long id;
	private String productName;
	private String productCode;
	private double productPrice;
	private String storeName;
	private String brandName;
	private String categoryNames;
	private String description;
	
	public MProductDTO(MProductEntity entity) {
		super();
		this.id = entity.getId();
		this.productName = entity.getProductName();
		this.productCode = entity.getProductCode();
		this.productPrice = entity.getProductPrice();
		this.storeName = entity.getStoreName();
		this.brandName = entity.getBrandName();
		this.categoryNames = entity.getCategoryNames();
		this.description = entity.getDescription();
	}

}
