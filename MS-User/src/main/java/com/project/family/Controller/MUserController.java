package com.project.family.Controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.family.DTO.MUserDTO;
import com.project.family.Entity.MUserEntity;
import com.project.family.Service.MUserService;


@RestController
@RequestMapping("/m-user")
public class MUserController  {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MUserService mUserService;
	
//  --------------- retreave single data ------------------
	@GetMapping("/{id}")
	public ResponseEntity<?> retrieveMUserById(@PathVariable("id") Long id) {
		
		MUserDTO dto = new MUserDTO();
		Optional<MUserEntity> mUserEntity = mUserService.findById(id);
		
		if (mUserEntity.isPresent()) {
			BeanUtils.copyProperties(mUserEntity.get(), dto);
			logger.info("data user: {}",dto);
			return new ResponseEntity<> (dto, HttpStatus.OK);
		}else {
			logger.error("id not found: ",id);
			return new ResponseEntity<>("Unable to find id " + id, HttpStatus.NOT_FOUND);
		}
	}
	
	// ---------------- retrieve all data ----------------------
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllMstUser(){
		
		List<MUserEntity> entities = mUserService.findMUserAll();
		
		if (entities.isEmpty()) {
			return new ResponseEntity<MUserEntity>(HttpStatus.NO_CONTENT);
		} else {
			
			List<MUserDTO> listDto = entities.stream().map(MUserDTO::new).collect(Collectors.toList());
			
			return new ResponseEntity<>(listDto,HttpStatus.OK);
		}
	}
	
	// ---------------- retrieve pagination all data ----------------------
	@RequestMapping(value = "/{page}/{pagingSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getPagingAllMstUser(@PathVariable("page") int page, @PathVariable("pagingSize") int pagingSize){
		
		Page<MUserEntity> pages = mUserService.findPaginated(page, pagingSize);
        
        List<MUserEntity> entities = pages.getContent();
		
		if (entities.isEmpty()) {
			return new ResponseEntity<MUserEntity>(HttpStatus.NO_CONTENT);
		} else {
			
			List<MUserDTO> listDto = entities.stream().map(new Function<MUserEntity, MUserDTO>() {
   				@Override
   				public MUserDTO apply(MUserEntity entity) {
   					
   					MUserDTO dto = new MUserDTO();
   					BeanUtils.copyProperties(entity, dto);
   						
   					return dto;
   				}
   			}).collect(Collectors.toList());
			return new ResponseEntity<>(listDto,HttpStatus.OK);
		}
	}
	
	/*for register user*/
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE},
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createUserProfiles(@RequestBody MUserDTO mUserDTO, UriComponentsBuilder ucBuilder){
		try {

			MUserEntity mUserEntity = new MUserEntity();
			BeanUtils.copyProperties(mUserDTO, mUserEntity);
			mUserEntity.setAccountNonExpired(true);								/*define by sistem*/
			mUserEntity.setAccountNonLocked(true);								/*define by sistem*/
			mUserEntity.setChangePwdCounter(0);									/*define by sistem*/
			mUserEntity.setCredentialsNonExpired(true);							/*define by sistem*/
			mUserEntity.setEnabled(true);											/*define by sistem*/
			mUserEntity.setFailCounter(0);										/*define by sistem*/
			mUserEntity.setLastLogin(Instant.now());								/*define by sistem*/
			mUserEntity.setPassword(new BCryptPasswordEncoder().encode(mUserDTO.getPassword()));
			
			mUserService.saveMUser(mUserEntity);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/{username}").buildAndExpand(mUserEntity.getUsername()).toUri());
			
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfiles(@RequestBody MUserDTO mUserDTO){
		
		Optional<MUserEntity> mUser = mUserService.findById(mUserDTO.getId());
		if (!mUser.isPresent()) {
			logger.error("Unable to update. UserProfiles with user {} not found",mUserDTO.getUsername());
			return new ResponseEntity<>("Unable to update. User with id "+mUserDTO.getUsername()+" not found", HttpStatus.NOT_FOUND);
		}
		
		MUserEntity mUserEntity = new MUserEntity();
		
		BeanUtils.copyProperties(mUserDTO, mUserEntity);
		mUserEntity.setAccountNonExpired(true);								/*define by sistem*/
		mUserEntity.setAccountNonLocked(true);								/*define by sistem*/
		mUserEntity.setChangePwdCounter(0);									/*define by sistem*/
		mUserEntity.setCredentialsNonExpired(true);							/*define by sistem*/
		mUserEntity.setFailCounter(0);										/*define by sistem*/
		mUserEntity.setLastLogin(Instant.now());								/*define by sistem*/
		mUserEntity.setPassword(new BCryptPasswordEncoder().encode(mUserDTO.getPassword()));
		
		mUserService.saveMUser(mUserEntity);
		
		return new ResponseEntity<>(mUser, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<?> deleteUserProfilesById(@PathVariable("id") int id){
//		
//		logger.info("Start deleteUserProfilesById by: {}",generalUtil.currentUserEntity().getUserId());
//		logger.info("Fetching & Deleting UserProfiles with id {}", id);
//		
//		Optional<MUserEntity> mUserEntity = mUserService.findById(id);
//		if (!mUserEntity.isPresent()) {
//			logger.error("Unable to update. UserProfiles with id {} not found",id);
//			return new ResponseEntity<>("Unable to update. User with id "+id+" not found", HttpStatus.NOT_FOUND);
//		}
//		
//		mUserService.deleteMstUserById(id);
//		
//		logger.info("End deleteUserProfilesById by: {}",generalUtil.currentUserEntity().getUserId());
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
}
