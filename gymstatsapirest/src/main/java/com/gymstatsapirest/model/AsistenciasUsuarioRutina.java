package com.gymstatsapirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asistencias_usuario_rutinas database table.
 * 
 */
@Entity
@Table(name="asistencias_usuario_rutinas")
public class AsistenciasUsuarioRutina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	//bi-directional many-to-one association to AsistenciasUsuario
	@JoinColumn(name="asistencias_usuario_id")
	@ManyToOne
	private AsistenciasUsuario asistenciasUsuario;

	//bi-directional many-to-one association to Rutina
	@ManyToOne
	@JoinColumn(name="rutinasid")
	private Rutina rutina;

	public AsistenciasUsuarioRutina() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AsistenciasUsuario getAsistenciasUsuario() {
		return this.asistenciasUsuario;
	}

	public void setAsistenciasUsuario(AsistenciasUsuario asistenciasUsuario) {
		this.asistenciasUsuario = asistenciasUsuario;
	}

	public Rutina getRutina() {
		return this.rutina;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

}
