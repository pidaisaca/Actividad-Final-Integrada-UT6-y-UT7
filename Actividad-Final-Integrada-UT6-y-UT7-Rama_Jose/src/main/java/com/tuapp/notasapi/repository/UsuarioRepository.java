package com.tuapp.notasapi.repository;

import com.tuapp.notasapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}