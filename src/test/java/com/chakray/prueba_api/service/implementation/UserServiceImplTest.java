package com.chakray.prueba_api.service.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chakray.prueba_api.config.UserData;
import com.chakray.prueba_api.dto.request.UserRequestDTO;
import com.chakray.prueba_api.dto.response.UserResponseDTO;
import com.chakray.prueba_api.mapper.UserMapper;
import com.chakray.prueba_api.model.User;
import com.chakray.prueba_api.shared.exception.BusinessException;
import com.chakray.prueba_api.shared.exception.ResourceNotFoundException;
import com.chakray.prueba_api.util.AesEncryptionUtil;
import com.chakray.prueba_api.util.DateUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserData userData;
	@Mock
	private UserMapper userMapper;
	@Mock
	private AesEncryptionUtil aesEncryptionUtil;
	@Mock
	private DateUtil dateUtil;

	@InjectMocks
	private UserServiceImpl userService;

	private User user1;
	private User user2;

	@BeforeEach
	void setUp() {
		user1 = User.builder().id(UUID.randomUUID()).email("carlos@mail.com").name("Carlos").phone("5512345678")
				.password("encryptedPass1").taxId("CARL900101AB1").createdAt("01-01-2026 00:00").build();

		user2 = User.builder().id(UUID.randomUUID()).email("diana@mail.com").name("Diana").phone("5598765432")
				.password("encryptedPass2").taxId("DIAN900202CD2").createdAt("01-01-2026 00:00").build();
	}

	@Test
	void getUsers_returnsAllUsers_whenNoParamsGiven() {
		when(userData.findAll()).thenReturn(List.of(user1, user2));
		when(userMapper.entityListToResponseList(any()))
				.thenReturn(List.of(new UserResponseDTO(), new UserResponseDTO()));

		List<UserResponseDTO> result = userService.getUsers(null, null);

		assertEquals(2, result.size());
		verify(userData).findAll();
	}

	@Test
	void getUsers_returnsFilteredUsers_whenFilterByNameContains() {
		when(userData.findAll()).thenReturn(List.of(user1, user2));
		when(userMapper.entityListToResponseList(any())).thenReturn(List.of(new UserResponseDTO()));

		List<UserResponseDTO> result = userService.getUsers(null, "name+co+Carlos");

		assertEquals(1, result.size());
	}

	@Test
	void getUsers_throwsException_whenFilterFormatIsInvalid() {
		assertThrows(BusinessException.class, () -> userService.getUsers(null, "formatoInvalido"));
	}

	// createUser

	@Test
	void createUser_throwsException_whenTaxIdAlreadyExists() {
		UserRequestDTO request = new UserRequestDTO();
		request.setTaxId("CARL900101AB1");
		request.setPassword("pass");

		when(userData.existsByTaxId("CARL900101AB1")).thenReturn(true);

		assertThrows(BusinessException.class, () -> userService.createUser(request));
	}

	@Test
	void createUser_savesAndReturnsUser_whenDataIsValid() throws Exception {
		UserRequestDTO request = new UserRequestDTO();
		request.setEmail("nuevo@mail.com");
		request.setName("Nuevo");
		request.setPhone("5511112222");
		request.setPassword("miPassword");
		request.setTaxId("NUEV900101EF3");
		request.setAddresses(List.of());

		when(userData.existsByTaxId(any())).thenReturn(false);
		when(aesEncryptionUtil.encrypt(any())).thenReturn("encryptedPass");
		when(dateUtil.getCurrentMadagascarTime()).thenReturn("13-03-2026 10:00");
		when(userMapper.requestToEntity(any())).thenReturn(user1);
		when(userData.save(any())).thenReturn(user1);
		when(userMapper.entityToResponse(any())).thenReturn(new UserResponseDTO());

		UserResponseDTO result = userService.createUser(request);

		assertNotNull(result);
		verify(userData).save(any());
	}

	// deleteUser

	@Test
	void deleteUser_throwsException_whenUserDoesNotExist() {
		UUID id = UUID.randomUUID();
		when(userData.existsById(id)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(id));
	}

	@Test
	void deleteUser_removesUser_whenUserExists() {
		UUID id = UUID.randomUUID();
		when(userData.existsById(id)).thenReturn(true);

		userService.deleteUser(id);

		verify(userData).deleteById(id);
	}

	// updateUser

	@Test
	void updateUser_throwsException_whenUserNotFound() {
		UUID id = UUID.randomUUID();
		when(userData.existsByTaxIdAndIdNot(any(), any())).thenReturn(false);
		when(userData.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> userService.updateUser(id, new com.chakray.prueba_api.dto.update.UserUpdateDTO()));
	}
}