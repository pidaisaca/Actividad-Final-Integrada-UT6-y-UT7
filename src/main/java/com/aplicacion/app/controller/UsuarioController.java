package com.aplicacion.app.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.aplicacion.app.service.UsuarioService;
import com.aplicacion.app.model.Usuario;

@RestController
@RequestMapping({"/api/v1/usuarios","api/v2/usuarios"})
@Validated
public class UsuarioController {
    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        log.info("Obteniendo todos los usuarios");
        log.debug("GET /api/v1/usuarios");
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        log.info("Obteniendo usuario con id: {}", id);
        log.debug("GET /api/v1/usuarios/{}", id);
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Validated @RequestBody Usuario usuario) {
        log.info("Guardando usuario: {}", usuario);
        log.debug("POST /api/v1/usuarios");
        Usuario user = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(Long id, @RequestBody Usuario usuario) {
        log.info("Actualizando usuario con id: {}", id);
        log.debug("PUT /api/v1/usuarios/{}", id);
        return usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(Long id) {
        log.info("Eliminando usuario con id: {}", id);
        log.debug("DELETE /api/v1/usuarios/{}", id);
        usuarioService.delete(id);
    }

    
}
