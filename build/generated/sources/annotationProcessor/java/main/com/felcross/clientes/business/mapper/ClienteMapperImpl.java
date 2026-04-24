package com.felcross.clientes.business.mapper;

import com.felcross.clientes.api.dto.ClienteRequest;
import com.felcross.clientes.api.dto.ClienteResponse;
import com.felcross.clientes.domain.entity.Cliente;
import com.felcross.clientes.domain.entity.Endereco;
import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-02T14:57:27-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(ClienteRequest request) {
        if ( request == null ) {
            return null;
        }

        Cliente.ClienteBuilder cliente = Cliente.builder();

        cliente.nome( request.getNome() );
        cliente.email( request.getEmail() );
        cliente.password( request.getPassword() );
        cliente.cpf( request.getCpf() );
        cliente.telefone( request.getTelefone() );

        return cliente.build();
    }

    @Override
    public ClienteResponse toResponse(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteResponse.ClienteResponseBuilder clienteResponse = ClienteResponse.builder();

        clienteResponse.id( cliente.getId() );
        clienteResponse.nome( cliente.getNome() );
        clienteResponse.email( cliente.getEmail() );
        clienteResponse.cpf( cliente.getCpf() );
        clienteResponse.telefone( cliente.getTelefone() );
        clienteResponse.endereco( toEnderecoResponse( cliente.getEndereco() ) );
        clienteResponse.createdAt( cliente.getCreatedAt() );

        return clienteResponse.build();
    }

    @Override
    public Endereco toEndereco(EnderecoViaCepResponse viacep) {
        if ( viacep == null ) {
            return null;
        }

        Endereco.EnderecoBuilder endereco = Endereco.builder();

        endereco.cidade( viacep.getLocalidade() );
        endereco.cep( viacep.getCep() );
        endereco.logradouro( viacep.getLogradouro() );
        endereco.bairro( viacep.getBairro() );
        endereco.uf( viacep.getUf() );

        return endereco.build();
    }

    @Override
    public EnderecoViaCepResponse toEnderecoResponse(Endereco endereco) {
        if ( endereco == null ) {
            return null;
        }

        EnderecoViaCepResponse.EnderecoViaCepResponseBuilder enderecoViaCepResponse = EnderecoViaCepResponse.builder();

        enderecoViaCepResponse.localidade( endereco.getCidade() );
        enderecoViaCepResponse.cep( endereco.getCep() );
        enderecoViaCepResponse.logradouro( endereco.getLogradouro() );
        enderecoViaCepResponse.bairro( endereco.getBairro() );
        enderecoViaCepResponse.uf( endereco.getUf() );

        return enderecoViaCepResponse.build();
    }
}
