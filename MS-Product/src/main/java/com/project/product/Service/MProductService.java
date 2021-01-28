package com.project.product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.product.Entity.MProductEntity;

public interface MProductService {
	
	Optional<MProductEntity> findById(Long id);
	MProductEntity save(MProductEntity entity);
	MProductEntity update(MProductEntity entity);
    void deleteById(Long id);
    List<MProductEntity> findAll();
	
	List<MProductEntity> findByProductCode(String productCode);
	Page<MProductEntity> findByProductCode(String productCode, Pageable pageable);

}
