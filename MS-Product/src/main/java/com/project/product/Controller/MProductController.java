package com.project.product.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.product.DTO.MProductDTO;
import com.project.product.Entity.MProductEntity;
import com.project.product.Service.MProductService;

@RestController
@RequestMapping("/m-product")
public class MProductController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private MProductService mProductService;
	
	@GetMapping("/{id}")
    public ResponseEntity<MProductDTO> findMProductById(@PathVariable("id") Long id) {
        log.info("REST API for get findMProductById by id : {}", id);
        
        Optional<MProductEntity> opt = mProductService.findById(id);
        MProductDTO mProductDTO = new MProductDTO();
        
        if (opt.isPresent()) {
        	BeanUtils.copyProperties(opt.get(), mProductDTO);
        	
        	log.info("port: {}",environment.getProperty("server.port"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(mProductDTO);
    }
	
	@GetMapping("/")
    public ResponseEntity<List<MProductDTO>> findMProductAll() {
        log.info("REST API for get findMProductAll");
        
        List<MProductEntity> entities = mProductService.findAll();
        log.info("iss: {}",entities.size());
        if (entities.size() > 0) {
        	List<MProductDTO> dtos = entities.stream().map(MProductDTO::new).collect(Collectors.toList());
        	return new ResponseEntity<List<MProductDTO>> (dtos, HttpStatus.OK);
        } else {
        	return new ResponseEntity<List<MProductDTO>> (HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("/")
    public ResponseEntity<?> postMProduct(@RequestBody MProductDTO dto) {
        log.info("REST API for insert postMProduct: {}", dto);
        
        try {
        	MProductEntity entity = new MProductEntity();
 			BeanUtils.copyProperties(dto, entity);
 			
 			mProductService.save(entity);
 			return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    }
	
	@PutMapping("/")
    public ResponseEntity<?>putMProduct(@RequestBody MProductDTO dto) {
        log.info("REST API for update putMProduct");
        
        Optional<MProductEntity> opt = mProductService.findById(dto.getId());
        
        if (!opt.isPresent()) {
 			log.error("Unable to update. M User with id {} not found",dto.getId());
 			return new ResponseEntity<>("Unable to update. M User with id " +dto.getId()+ " not found", HttpStatus.NOT_FOUND);
 		}
        
        try {
        	MProductEntity entity = new MProductEntity();
 			BeanUtils.copyProperties(dto, entity);
 			
 			mProductService.update(entity);
 			return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMProductById(@PathVariable("id") Long id) {
        log.info("REST API for delete deleteMProductById by id : {}", id);
        
        Optional<MProductEntity> opt = mProductService.findById(id);
        
        if (!opt.isPresent()) {
 			log.error("Unable to delete. M User with id {} not found",id);
 			return new ResponseEntity<>("Unable to delete. M User with id " +id+ " not found", HttpStatus.NOT_FOUND);
 		}
 		
        try {
        	mProductService.deleteById(id);
        	return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
