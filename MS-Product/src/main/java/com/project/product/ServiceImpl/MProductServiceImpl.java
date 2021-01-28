package com.project.product.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.product.Entity.MProductEntity;
import com.project.product.Repository.MProductRepository;
import com.project.product.Service.MProductService;

@Service("mProductService")
@Transactional("transactionManager")
public class MProductServiceImpl implements MProductService {
	
	@Autowired
	private MProductRepository repository;

	@Override
	public Optional<MProductEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public MProductEntity save(MProductEntity entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public MProductEntity update(MProductEntity entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public List<MProductEntity> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<MProductEntity> findByProductCode(String productCode) {
		// TODO Auto-generated method stub
		return repository.findByProductCode(productCode);
	}

	@Override
	public Page<MProductEntity> findByProductCode(String productCode, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByProductCode(productCode, pageable);
	}

}
