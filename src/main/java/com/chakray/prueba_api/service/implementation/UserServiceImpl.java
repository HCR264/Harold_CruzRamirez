package com.chakray.prueba_api.service.implementation;

import com.chakray.prueba_api.util.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakray.prueba_api.dto.request.UserRequestDTO;
import com.chakray.prueba_api.dto.response.UserResponseDTO;
import com.chakray.prueba_api.dto.update.UserUpdateDTO;
import com.chakray.prueba_api.mapper.UserMapper;
import com.chakray.prueba_api.model.User;
import com.chakray.prueba_api.repository.UserRepository;
import com.chakray.prueba_api.service.UserServiceI;
import com.chakray.prueba_api.shared.exception.BusinessException;
import com.chakray.prueba_api.shared.exception.ResourceNotFoundException;
import com.chakray.prueba_api.util.AesEncryptionUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase service para usuarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserServiceI {
	private final AesEncryptionUtil aesEncryptionUtil;
	private final DateUtil dateUtil;
	private final UserMapper userMapper;
	private final UserRepository userRepository;

	private static final Map<String, String> CONVERT_FIELDS = new HashMap<>();
	static {
		CONVERT_FIELDS.put("tax_id", "taxId");
		CONVERT_FIELDS.put("created_at", "createdAt");
	}

	private static final Map<String, Function<User, String>> USER_GETTERS = new HashMap<>();
	static {
		USER_GETTERS.put("email", User::getEmail);
		USER_GETTERS.put("name", User::getName);
		USER_GETTERS.put("phone", User::getPhone);
		USER_GETTERS.put("taxId", User::getTaxId);
		USER_GETTERS.put("createdAt", User::getCreatedAt);
		USER_GETTERS.put("id", u -> u.getId().toString());
	}

	public static String toCamelCase(String snakeCase) {
		return CONVERT_FIELDS.getOrDefault(snakeCase, snakeCase);
	}

	@Override
	public List<UserResponseDTO> getUsers(String sortedBy, String filter) {
		if (filter != null && !filter.isBlank()) {
			String[] filters = filter.split("\\+");
			if (filters.length != 3) {
				throw new BusinessException("Invalid filter");
			}
			String field = toCamelCase(filters[0]);
			String condition = filters[1];
			String value = filters[2];
			List<User> users = userRepository.findAll();

			Function<User, String> getter = USER_GETTERS.get(field);
			if (getter == null) throw new BusinessException("Invalid filter field: " + field);
			users = users.stream().filter(u -> {
				String fieldValue = getter.apply(u);
				return switch (condition) {
				case "co" -> fieldValue.contains(value);
				case "eq" -> fieldValue.equals(value);
				case "sw" -> fieldValue.startsWith(value);
				case "ew" -> fieldValue.endsWith(value);
				default -> throw new BusinessException("Invalid operator");
				};
			}).toList();

			return userMapper.entityListToResponseList(users);
		}
		if (sortedBy == null || sortedBy.isEmpty()) {
			return userMapper.entityListToResponseList(userRepository.findAll());
		}
		List<User> users = userRepository.findAll(Sort.by(toCamelCase(sortedBy)));
		return userMapper.entityListToResponseList(users);
	}

	@Override
	public UserResponseDTO createUser(UserRequestDTO userRequest) throws Exception {
		if (userRepository.existsByTaxId(userRequest.getTaxId().trim())) {
			throw new BusinessException("Tax_ID already exist");
		}
		userRequest.setPassword(aesEncryptionUtil.encrypt(userRequest.getPassword().trim()));
		User user = userMapper.requestToEntity(userRequest);
		user.setCreatedAt(dateUtil.getCurrentMadagascarTime());
		return userMapper.entityToResponse(userRepository.save(user));
	}

	@Override
	public UserResponseDTO updateUser(UUID id, UserUpdateDTO userUpdate) throws Exception {
		if (id == null || userUpdate == null) {
			throw new BusinessException("Invalid data");
		}
		if (userRepository.existsByTaxIdAndIdNot(userUpdate.getTaxId(), id)) {
			throw new BusinessException("Tax_ID already exist");
		}
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));
		user = userRepository.save(copyChanges(userUpdate, user));
		return userMapper.entityToResponse(user);

	}

	@Override
	public void deleteUser(UUID id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User doesn't exist with ID: " + id);
		}
		userRepository.deleteById(id);

	}

	private User copyChanges(UserUpdateDTO userUpdate, User user) throws Exception {
		Boolean hasChanges = false;
		if (userUpdate.getEmail() != null && !userUpdate.getEmail().isBlank()) {
			String emailRequest = userUpdate.getEmail().trim();
			if (!emailRequest.equals(user.getEmail())) {
				user.setEmail(emailRequest);
				hasChanges = true;
			}
		}
		if (userUpdate.getName() != null && !userUpdate.getName().isBlank()) {
			String nameRequest = userUpdate.getName().trim();
			if (!nameRequest.equals(user.getName())) {
				user.setName(nameRequest);
				hasChanges = true;
			}
		}
		if (userUpdate.getPhone() != null && !userUpdate.getPhone().isBlank()) {
			String phoneRequest = userUpdate.getPhone().trim();
			if (!phoneRequest.equals(user.getPhone())) {
				user.setPhone(phoneRequest);
				hasChanges = true;
			}
		}
		if (userUpdate.getPassword() != null && !userUpdate.getPassword().isBlank()) {
			String passwordRequest = userUpdate.getPassword().trim();
			if (!passwordRequest.equals(user.getPassword())) {
				user.setPassword(aesEncryptionUtil.encrypt(passwordRequest));
				hasChanges = true;
			}
		}
		if (userUpdate.getTaxId() != null && !userUpdate.getTaxId().isBlank()) {
			String taxIdRequest = userUpdate.getTaxId().trim();
			if (!taxIdRequest.equals(user.getTaxId())) {
				user.setTaxId(taxIdRequest);
				hasChanges = true;
			}
		}
		if (!hasChanges) {
			throw new BusinessException("No changes");
		}
		return user;
	}

}
