package com.project.product.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.product.Entity.MProductEntity;

@Repository
public interface MProductRepository extends JpaRepository<MProductEntity, Long> {
	
	List<MProductEntity> findByProductCode(String productCode);
	Page<MProductEntity> findByProductCode(String productCode, Pageable pageable);

}
