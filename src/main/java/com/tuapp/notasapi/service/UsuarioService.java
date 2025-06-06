package com.tuapp.notasapi.service;

import java.util.List;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.tuapp.notasapi.model.Usuario;
import com.tuapp.notasapi.repository.UsuarioRepository;


@Service
public class UsuarioService extends AbstractCrudService<Usuario, Long> {
    
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Usuario save(Usuario usuario) {
        if(usuario.getPasswordHash() != null && !usuario.getPasswordHash().isEmpty()){
            usuario.setPassword(usuario.getPasswordHash());
        } try {
            return repository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email " + usuario.getEmail() + " ya se encuentra registrado.");
        }
    }

   @Transactional(readOnly = true)
   public List<Usuario> findAll() {
       List<Usuario> usuarios = repository.findAll();
       if (usuarios.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron usuarios");
       }
       return usuarios;
   }
   
   @Transactional(readOnly = true)
   public Usuario findById(Long id) {
       return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario con id " + id));
   }
   
   public void delete(Long id) {
       try {
           repository.deleteById(id);
       } catch (EmptyResultDataAccessException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario con id " + id);
       }
   }
   
   public Usuario update(Long id, Usuario usuario) {
       return repository.findById(id).map(a -> {
           a.setnombre(usuario.getnombre());
           a.setEmail(usuario.getEmail());
           if(usuario.getPasswordHash() != null && !usuario.getPasswordHash().isEmpty()){
               a.setPassword(usuario.getPasswordHash());
           }
           return repository.save(a);
       }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario con id " + id));
   }
}