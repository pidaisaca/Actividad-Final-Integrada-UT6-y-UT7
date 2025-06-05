package com.tuapp.notasapi.controller;

import java.util.List;

import org.apache.logging.log4j.util.PropertySource.Comparator;
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

import com.tuapp.notasapi.model.Nota;
import com.tuapp.notasapi.service.NotaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping({ "api/v1/notas" })
@Validated
public class NotaController {
    private final NotaService notaSvc;

    public NotaController(NotaService notaSvc) {
        this.notaSvc = notaSvc;
    }

    @GetMapping
    public ResponseEntity<List<Nota>> getAllNotas() {
        return ResponseEntity.ok(notaSvc.getAllNotas());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Nota> getNotaById(@PathVariable @Positive Long id) {
        return notaSvc.getNotaById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   @GetMapping("/usuarios/{usuarioId}")
   public ResponseEntity<List<Nota>> getNotasByUsuarioId(
           @PathVariable @Positive Long usuarioId,
           @RequestParam(defaultValue = "id,asc") String sort) {
       List<Nota> notas = notaSvc.getNotasByUsuarioId(usuarioId);
       notas.sort((n1, n2) -> {
           if (sort.equals("id,asc")) {
               return Long.compare(n1.getId(), n2.getId());
           } else if (sort.equals("id,desc")) {
               return Long.compare(n2.getId(), n1.getId());
           } else {
               return 0;
           }
       });
       return ResponseEntity.ok(notas);
   }

    @PostMapping
    public ResponseEntity<Nota> saveNota(@Valid @RequestBody Nota n) {
        Nota createdNota = notaSvc.saveNota(n);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(@PathVariable @Positive Long id, @Valid @RequestBody Nota n) {
        return notaSvc.getNotaById(id).map(existing -> {
            Nota updatedNota = notaSvc.updateNota(id, n);
            return ResponseEntity.ok(updatedNota);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable @Positive Long id) {
        return notaSvc.getNotaById(id).map(existing -> {
            notaSvc.deleteNota(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
