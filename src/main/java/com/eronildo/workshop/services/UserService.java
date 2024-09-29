package com.eronildo.workshop.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eronildo.workshop.domain.User;
import com.eronildo.workshop.dto.UserDTO;
import com.eronildo.workshop.repository.UserRepository;
import com.eronildo.workshop.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User obj) {
		return repository.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repository.deleteById(id);
	}
	
	public User update(User obj) {
		try {
			Optional<User> newUser = repository.findById(obj.getId());
			User user = newUser.get();
			updateData(user, obj);
			return repository.save(user);
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
		
	}

	private void updateData(User user, User obj) {
		user.setName(obj.getName());
		user.setEmail(obj.getEmail());
		
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
