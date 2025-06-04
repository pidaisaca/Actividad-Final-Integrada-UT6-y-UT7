package com.tuapp.notasapi.repository;

import com.tuapp.notasapi.model.Nota;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long>{
    
    List<Nota> findByUsuario_Id(Long usuarioId, Sort sort);
} 
    
