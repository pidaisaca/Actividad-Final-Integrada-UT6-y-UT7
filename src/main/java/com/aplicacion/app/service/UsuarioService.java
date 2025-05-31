package com.aplicacion.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplicacion.app.model.Usuario;
import com.aplicacion.app.repository.UsuarioRepository;


@Service
public class UsuarioService extends CrudService<Usuario, Long> {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuario) {
        return repository.findById(id).map(a -> {
            usuario.setPassword(a.getPasswordHash());
            a.setnombre(usuario.getnombre());
            a.setEmail(usuario.getEmail());
            a.setPassword(usuario.getPasswordHash());
            return repository.save(a);
        }).orElseGet(() -> {
            usuario.setId(id);
            return repository.save(usuario);
        });
    }
}
