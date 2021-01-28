package com.project.family.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.family.Entity.MUserEntity;


public interface MUserService {
	
	Optional<MUserEntity> findById(Long id);
	Optional<MUserEntity> findByUsername(String username);
	Optional<MUserEntity> findByEmail(String email);
	Optional<MUserEntity> findByResetToken(String resetToken);
	Optional<MUserEntity> findByPhoneNumberOrEmail(String phoneNumber, String email);
	void saveMUser(MUserEntity MUserEntity);
	void updateMUser(MUserEntity MUserEntity);
    void deleteMUserById(Long userId);
    void deleteMUserAll();
    List<MUserEntity> findMUserAll();
    Page<MUserEntity> findPaginated(int page, int size);
    Page<MUserEntity >findPagingMUserAll(String userId, String email, Pageable pageable);

}
