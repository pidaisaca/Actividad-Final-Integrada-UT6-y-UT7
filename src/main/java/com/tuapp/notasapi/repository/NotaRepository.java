package com.tuapp.notasapi.repository;

import com.tuapp.notasapi.model.Nota;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long>{

    List<Nota> findByUsuarioId(Long usuarioId, Sort sort);
} 
    
