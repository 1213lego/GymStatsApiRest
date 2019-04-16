package com.gymstatsapirest.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rutinas database table.
 * 
 */
@Entity
@Table(name="rutinas")
@NamedQuery(name="Rutina.findAll", query="SELECT r FROM Rutina r")
public class Rutina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name="descripcion_rutina")
	private String descripcionRutina;

	@Column(name="nombre_rutina")
	private String nombreRutina;

	//bi-directional many-to-one association to AsistenciasUsuarioRutina
	@OneToMany(mappedBy="rutina")
	private List<AsistenciasUsuarioRutina> asistenciasUsuarioRutinas;

	//bi-directional many-to-one association to MaquinaRutina
	@OneToMany(mappedBy="rutina")
	private List<MaquinaRutina> maquinaRutinas;

	public Rutina() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcionRutina() {
		return this.descripcionRutina;
	}

	public void setDescripcionRutina(String descripcionRutina) {
		this.descripcionRutina = descripcionRutina;
	}

	public String getNombreRutina() {
		return this.nombreRutina;
	}

	public void setNombreRutina(String nombreRutina) {
		this.nombreRutina = nombreRutina;
	}

	public List<AsistenciasUsuarioRutina> getAsistenciasUsuarioRutinas() {
		return this.asistenciasUsuarioRutinas;
	}

	public void setAsistenciasUsuarioRutinas(List<AsistenciasUsuarioRutina> asistenciasUsuarioRutinas) {
		this.asistenciasUsuarioRutinas = asistenciasUsuarioRutinas;
	}

	public AsistenciasUsuarioRutina addAsistenciasUsuarioRutina(AsistenciasUsuarioRutina asistenciasUsuarioRutina) {
		getAsistenciasUsuarioRutinas().add(asistenciasUsuarioRutina);
		asistenciasUsuarioRutina.setRutina(this);

		return asistenciasUsuarioRutina;
	}

	public AsistenciasUsuarioRutina removeAsistenciasUsuarioRutina(AsistenciasUsuarioRutina asistenciasUsuarioRutina) {
		getAsistenciasUsuarioRutinas().remove(asistenciasUsuarioRutina);
		asistenciasUsuarioRutina.setRutina(null);

		return asistenciasUsuarioRutina;
	}

	public List<MaquinaRutina> getMaquinaRutinas() {
		return this.maquinaRutinas;
	}

	public void setMaquinaRutinas(List<MaquinaRutina> maquinaRutinas) {
		this.maquinaRutinas = maquinaRutinas;
	}

	public MaquinaRutina addMaquinaRutina(MaquinaRutina maquinaRutina) {
		getMaquinaRutinas().add(maquinaRutina);
		maquinaRutina.setRutina(this);

		return maquinaRutina;
	}

	public MaquinaRutina removeMaquinaRutina(MaquinaRutina maquinaRutina) {
		getMaquinaRutinas().remove(maquinaRutina);
		maquinaRutina.setRutina(null);

		return maquinaRutina;
	}

}