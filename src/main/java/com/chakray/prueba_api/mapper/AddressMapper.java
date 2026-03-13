package com.chakray.prueba_api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chakray.prueba_api.dto.request.AddressRequestDTO;
import com.chakray.prueba_api.dto.response.AddressResponseDTO;
import com.chakray.prueba_api.model.Address;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Mapper para las clases address
 */
@Component
@Data
@RequiredArgsConstructor
public class AddressMapper {
	/**
	 * Método para convertir un AddressRequestDTO a un Address
	 * 
	 * @param addressRequest La clase RequestDTO
	 * @return Entity
	 */
	public Address requestToEntity(AddressRequestDTO addressRequest) {
		return Address.builder().name(addressRequest.getName().trim()).street(addressRequest.getStreet().trim())
				.countryCode(addressRequest.getCountryCode().trim()).build();
	}

	/**
	 * Método para convertir una lista con AddressRequestDTO a una lista con Address
	 * 
	 * @param addressesRequest La lista con los RequestDTO
	 * @return Lista con los Entity
	 */
	public List<Address> requestListToEntityList(List<AddressRequestDTO> addressesRequest) {
		List<Address> addresses = new ArrayList<>();
		Address address;
		for (AddressRequestDTO addressRequestDTO : addressesRequest) {
			address = requestToEntity(addressRequestDTO);
			addresses.add(address);
		}
		return addresses;
	}

	/**
	 * Método para convertir un Address a un AddressResponseDTO
	 * 
	 * @param address La clase Entity
	 * @return Response
	 */
	public AddressResponseDTO entityToResponse(Address address) {
		return AddressResponseDTO.builder().id(address.getId()).name(address.getName()).street(address.getStreet())
				.countryCode(address.getCountryCode()).build();
	}

	/**
	 * Método para convertir una lista con Address a una lista con
	 * AddressResponseDTO
	 * 
	 * @param addresses La lista con las Entity
	 * @return Lista con los Response
	 */
	public List<AddressResponseDTO> entityListToResponseList(List<Address> addresses) {
		List<AddressResponseDTO> addressesResponse = new ArrayList<>();
		AddressResponseDTO addressResponseDTO;
		for (Address address : addresses) {
			addressResponseDTO = entityToResponse(address);
			addressesResponse.add(addressResponseDTO);
		}
		return addressesResponse;
	}

}
