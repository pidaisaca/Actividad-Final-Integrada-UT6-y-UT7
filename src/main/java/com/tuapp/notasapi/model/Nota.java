package com.tuapp.notasapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
@Table(name = "notas")
@JsonPropertyOrder({"id", "titulo", "contenido", "fechaCreacion", "usuario"})
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id_nota;

    private String titulo;
    private String contenido;

    @Column(name = "fecha_creacion")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "El usuario no puede ser nulo")
    private Usuario usuario;


    public Nota() {
    }

    public Nota(String titulo, String contenido, LocalDateTime fechaCreacion, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
    }


    @PrePersist
    public void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
    
    public Long getId() {
        return id_nota;
    }

    public void setId(Long id) {
        this.id_nota = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nota)) return false;
        Nota nota = (Nota) o;
        return Objects.equals(id_nota, nota.id_nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_nota);
    }
}
