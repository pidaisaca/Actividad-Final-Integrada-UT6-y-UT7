package com.tuapp.notasapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import com.tuapp.notasapi.model.Nota;
import com.tuapp.notasapi.repository.NotaRepository;

@Service
public class NotaService {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    @Transactional(readOnly = true)
    public List<Nota> getAllNotas() {
        return notaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Nota> getNotaById(Long id) {
        return notaRepository.findById(id);
    }

    public Nota saveNota(Nota e) {
        return notaRepository.save(e);
    }

    public Nota updateNota(Long id, Nota e) {
        e.setId(id);
        return notaRepository.save(e);
    }

    public void deleteNota(Long id) {
        notaRepository.deleteById(id);
    }

}
