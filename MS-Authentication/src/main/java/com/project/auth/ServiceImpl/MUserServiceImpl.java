package com.project.auth.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.auth.Entity.MUserEntity;
import com.project.auth.Repository.MUserRepository;
import com.project.auth.Service.MUserService;


@Service("mUserService")
@Transactional("transactionManager")
public class MUserServiceImpl implements MUserService {
	
	@Autowired
	MUserRepository mUserRepository;
	
	@Override
	public Optional<MUserEntity> findById(Long id) {
		return mUserRepository.findById(id);
	}
	
	@Override
	public Optional<MUserEntity> findByUsername(String userId) {
		return mUserRepository.findByUsername(userId);
	}
	
	@Override
	public Optional<MUserEntity> findByEmail(String email){
		return mUserRepository.findByEmail(email);
	}
	
	@Override
	public Optional<MUserEntity> findByResetToken(String resetToken){
		return mUserRepository.findByResetToken(resetToken);
	}

	@Override
	public Optional<MUserEntity> findByPhoneNumberOrEmail(String phoneNumber, String email) {
		// TODO Auto-generated method stub
		return mUserRepository.findByPhoneNumberOrEmail(phoneNumber, email);
	}
	
	@Override
    public void saveMUser(MUserEntity MUserEntity) {
        mUserRepository.save(MUserEntity);
    }
	
	@Override
	public void updateMUser(MUserEntity MUserEntity) {
		saveMUser(MUserEntity);
	}
	
	@Override
	public void deleteMUserById(Long userId) {
		mUserRepository.deleteById(userId);
	}
	
	@Override
	public void deleteMUserAll() {
		mUserRepository.deleteAll();
	}
	
	@Override
	public List<MUserEntity> findMUserAll(){
		return mUserRepository.findAll();
	}
	
	@Override
	public Page<MUserEntity> findPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mUserRepository.findAll(pageable);
	}
	
	@Override
	public Page<MUserEntity >findPagingMUserAll(String userId, String email, Pageable pageable) {
		return mUserRepository.findByLikeUsernameAndLikeEmailPaging(userId, email, pageable);
	}

}
