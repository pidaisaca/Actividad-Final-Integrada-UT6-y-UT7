package com.tuapp.notasapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import com.tuapp.notasapi.model.Nota;
import com.tuapp.notasapi.repository.NotaRepository;

@Service
public class NotaService extends AbstractCrudService<Nota, Long> {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        super(notaRepository);
        this.notaRepository = notaRepository;
    }
@Transactional(readOnly = true)
public List<Nota> getAllNotas() {
    List<Nota> notas = notaRepository.findAll();
    if (notas.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron notas");
    }
    return notas;
}

@Transactional(readOnly = true)
public Optional<Nota> getNotaById(Long id) {
    return Optional.ofNullable(notaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la nota con id " + id)));
}

@Transactional(readOnly = true)
public List<Nota> getNotasByUsuarioId(Long usuarioId) {
    List<Nota> notas = notaRepository.findByUsuario_Id(usuarioId, Sort.by("fechaCreacion").descending());
    if (notas.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron notas para el usuario con id " + usuarioId);
    }
    return notas;
}

@Transactional
public Nota saveNota(Nota e) {
    try {
        return notaRepository.save(e);
    } catch (DataIntegrityViolationException ex) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "La nota no se ha podido guardar.");
    }
}

@Transactional
public Nota updateNota(Long id, Nota e) {
    return notaRepository.findById(id).map(n -> {
        n.setTitulo(e.getTitulo());
        n.setContenido(e.getContenido());
        return notaRepository.save(n);
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la nota con id " + id));
}

@Transactional
public void deleteNota(Long id) {
    try {
        notaRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la nota con id " + id);
    }
}

}
