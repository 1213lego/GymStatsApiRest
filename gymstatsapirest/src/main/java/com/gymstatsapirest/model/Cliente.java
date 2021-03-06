package com.gymstatsapirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * The persistent class for the clientes database table.
 * 
 */
@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull
	@Id
	private Integer documento;

	//bi-directional one-to-one association to Usuario
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="documento")
	private Usuario usuario;

	//bi-directional many-to-one association to MedidaCliente
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<MedidaCliente> medidaClientes;

	//bi-directional many-to-one association to Suscripcione
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Suscripcione> suscripciones;

	public Cliente() {
	}

	public Cliente(Integer documento) {
		this.documento=documento;
	}

    public Integer getDocumento() {
		return this.documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<MedidaCliente> getMedidaClientes() {
		return this.medidaClientes;
	}

	public void setMedidaClientes(List<MedidaCliente> medidaClientes) {
		this.medidaClientes = medidaClientes;
	}

	public MedidaCliente addMedidaCliente(MedidaCliente medidaCliente) {
		getMedidaClientes().add(medidaCliente);
		medidaCliente.setCliente(this);

		return medidaCliente;
	}

	public MedidaCliente removeMedidaCliente(MedidaCliente medidaCliente) {
		getMedidaClientes().remove(medidaCliente);
		medidaCliente.setCliente(null);

		return medidaCliente;
	}

	public List<Suscripcione> getSuscripciones() {
		return this.suscripciones;
	}

	public void setSuscripciones(List<Suscripcione> suscripciones) {
		this.suscripciones = suscripciones;
	}

	public Suscripcione addSuscripcione(Suscripcione suscripcione) {
		getSuscripciones().add(suscripcione);
		suscripcione.setCliente(this);

		return suscripcione;
	}

	public Suscripcione removeSuscripcione(Suscripcione suscripcione) {
		getSuscripciones().remove(suscripcione);
		suscripcione.setCliente(null);

		return suscripcione;
	}

}
