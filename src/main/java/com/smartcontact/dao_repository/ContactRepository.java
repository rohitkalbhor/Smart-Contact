package com.smartcontact.dao_repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.entity.Contact;
import com.smartcontact.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	@Query("from Contact as c where c.user.id=:userId")
	public Page<Contact> findContactByUser(@Param("userId") int userId,Pageable pePageable);

	//search
	

	public List<Contact> findByNameContainingAndUser(String name, User user);
}
