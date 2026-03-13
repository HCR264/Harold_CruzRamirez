package com.chakray.prueba_api.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.chakray.prueba_api.model.Address;
import com.chakray.prueba_api.model.User;
import com.chakray.prueba_api.util.DateUtil;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserData {
	private final DateUtil dateUtil;

	private final List<User> users = new ArrayList<>();

	@PostConstruct
	public void initUsers() {
		Address address1 = Address.builder().id(1L).name("workaddress").street("street 10").countryCode("MX").build();

		Address address2 = Address.builder().id(2L).name("homeaddress").street("street 12").countryCode("MX").build();

		List<Address> addresses1 = new ArrayList<>();
		addresses1.add(address1);
		addresses1.add(address2);

		User user1 = User.builder().id(UUID.randomUUID()).email("usuario_1@gmail.com").name("Luis Perez")
				.phone("+1 55 555 555 55").password("7c4a8d09ca3762af61e59520943dc26494f8941b").taxId("AAAA0101011A1")
				.createdAt(dateUtil.getCurrentMadagascarTime()).addresses(addresses1).build();

		Address address3 = Address.builder().id(3L).name("homeaddress").street("Oxford Street 25").countryCode("UK")
				.build();

		Address address4 = Address.builder().id(4L).name("workaddress").street("Baker Street 221B").countryCode("UK")
				.build();

		List<Address> addresses2 = new ArrayList<>();
		addresses2.add(address3);
		addresses2.add(address4);

		User user2 = User.builder().id(UUID.randomUUID()).email("usuario_2@gmail.com").name("Luisa Perez")
				.phone("+44 20 7946 0123").password("8c4a8d09ca3762af61e59520943dc26494f8941c").taxId("BBBB0202022B2")
				.createdAt(dateUtil.getCurrentMadagascarTime()).addresses(addresses2).build();

		Address address5 = Address.builder().id(5L).name("officeaddress").street("George Street 42").countryCode("AU")
				.build();

		Address address6 = Address.builder().id(6L).name("vacationhome").street("Bondi Beach Road 7").countryCode("AU")
				.build();

		List<Address> addresses3 = new ArrayList<>();
		addresses3.add(address5);
		addresses3.add(address6);

		User user3 = User.builder().id(UUID.randomUUID()).email("usuario_3@gmail.com").name("Pedro Hernandez")
				.phone("+61 2 9876 5432").password("9c4a8d09ca3762af61e59520943dc26494f8941d").taxId("CCCC0303033C3")
				.createdAt(dateUtil.getCurrentMadagascarTime()).addresses(addresses3).build();
		this.users.add(user1);
		this.users.add(user2);
		this.users.add(user3);
	}

	public List<User> findAll() {
		return this.users;
	}

	public Optional<User> findById(UUID id) {
		return this.users.stream().filter(u -> u.getId().equals(id)).findFirst();
	}
	
	public Optional<User> findByTaxId(String taxId) {
		return this.users.stream().filter(u -> u.getTaxId().equals(taxId)).findFirst();
	}

	public User save(User user) {
		user.setId(UUID.randomUUID());
		this.users.add(user);
		return user;
	}

	public boolean existsById(UUID id) {
		return this.users.stream().anyMatch(u -> u.getId().equals(id));
	}

	public boolean existsByTaxId(String taxId) {
		return this.users.stream().anyMatch(u -> u.getTaxId().equals(taxId));
	}

	public boolean existsByTaxIdAndIdNot(String taxId, UUID id) {
		return this.users.stream().filter(user -> !user.getId().equals(id))
				.anyMatch(user -> taxId.equals(user.getTaxId()));
	}

	public void deleteById(UUID id) {
		this.users.removeIf(u -> u.getId().equals(id));
	}

	public List<User> findAllSorted(String sortBy) {
		Comparator<User> comparator = switch (sortBy) {
		case "email" -> Comparator.comparing(User::getEmail, String::compareToIgnoreCase);
		case "name" -> Comparator.comparing(User::getName, String::compareToIgnoreCase);
		case "phone" -> Comparator.comparing(User::getPhone);
		case "taxId" -> Comparator.comparing(User::getTaxId);
		case "createdAt" -> Comparator.comparing(User::getCreatedAt);
		default -> Comparator.comparing(User::getId);
		};

		return users.stream().sorted(comparator).collect(Collectors.toList());
	}
}
