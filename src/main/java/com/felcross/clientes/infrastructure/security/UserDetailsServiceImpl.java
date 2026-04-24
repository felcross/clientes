package com.felcross.clientes.infrastructure.security;

import com.felcross.clientes.domain.entity.Cliente;
import com.felcross.clientes.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente nao encontrado: " + email));

        return User.withUsername(cliente.getEmail())
                .password(cliente.getPassword())
                .build();
    }
}
