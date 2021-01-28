package com.project.family.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.family.Entity.MUserEntity;


@Repository
public interface MUserRepository extends JpaRepository<MUserEntity, Long> {
	
	Optional<MUserEntity> findByUsername(String username);

	@Query
	(value = "SELECT usr FROM MUserEntity usr " + 
	" WHERE " + 
	"(?1 IS NULL OR ?1='' OR usr.username LIKE CONCAT('%',?1,'%')) " +
	"AND (?2 IS NULL OR ?2='' OR usr.email LIKE CONCAT('%',?2,'%')) "
	, countQuery = 
			"SELECT count(usr) FROM MUserEntity usr " + 
			" WHERE " + 
			"(?1 IS NULL OR ?1='' OR usr.username LIKE CONCAT('%',?1,'%')) " +
			"AND (?2 IS NULL OR ?2='' OR usr.email LIKE CONCAT('%',?2,'%')) ")
	Page<MUserEntity> findByLikeUsernameAndLikeEmailPaging(String username, String email, Pageable pageable);
	Optional<MUserEntity> findByEmail(String email);
	Optional<MUserEntity> findByResetToken (String resetToken);
	Optional<MUserEntity> findByPhoneNumberOrEmail(String phoneNumber, String email);

}
