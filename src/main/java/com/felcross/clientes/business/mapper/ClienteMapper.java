package com.felcross.clientes.business.mapper;

import com.felcross.clientes.api.dto.ClienteRequest;
import com.felcross.clientes.api.dto.ClienteResponse;

import com.felcross.clientes.domain.entity.Cliente;
import com.felcross.clientes.domain.entity.Endereco;
import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Cliente toEntity(ClienteRequest request);

    ClienteResponse toResponse(Cliente cliente);

    @Mapping(source = "localidade", target = "cidade")
    Endereco toEndereco(EnderecoViaCepResponse viacep);

    @Mapping(source = "cidade", target = "localidade")
    @Mapping(target = "erro", ignore = true)
    EnderecoViaCepResponse toEnderecoResponse(Endereco endereco);
}
