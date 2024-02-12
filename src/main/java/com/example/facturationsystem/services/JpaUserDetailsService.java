package com.example.facturationsystem.services;

import com.example.facturationsystem.models.entities.Role;
import com.example.facturationsystem.models.entities.User;
import com.example.facturationsystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase implementa la interfaz
 * {@link org.springframework.security.core.userdetails.UserDetailsService}
 * para cargar los detalles del usuario desde una base de datos JPA.
 */
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    /**
     * Carga los detalles del usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario para buscar en la base de datos
     * @return los detalles del usuario como un objeto {@link UserDetails}
     * @throws UsernameNotFoundException si el usuario no es encontrado en la base
     *                                   de datos
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("Error en el login: no existe el usuario '" + username + "'");
            throw new UsernameNotFoundException("Username " + username + " no existe");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        if (authorities.isEmpty()) {
            logger.error("Error en el login: no existe el usuario '" + username + "' no tiene roles asignados");
            throw new UsernameNotFoundException(
                    "Error en el login: no existe el usuario '" + username + "' no tiene roles asignados");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, true, authorities);
    }
}
