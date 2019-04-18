package com.gymstatsapirest.controller;
import com.gymstatsapirest.model.AutenticacionUsuario;
import com.gymstatsapirest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/pruebas")
@CrossOrigin
public class RestPrueba 
{
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    private EstadoUsuarioRepository estadoUsuarioRepository;
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    @Autowired
    private AutenticacionUsuarioRepository autenticacionUsuarioRepository;

    @GetMapping(path = "/utilidades", produces = "application/json")
	public Map<String,List> utilidadesUsuario()
    {
        Map<String, List> map = new HashMap<>();
        map.put("genero",generoRepository.findAll());
        map.put("tipousuario",tipoUsuarioRepository.findAll());
        map.put("estadousuario",estadoUsuarioRepository.findAll());
        map.put("tipodocumento",tipoDocumentoRepository.findAll());
        return map;
    }
    @GetMapping(path = "/autenticacions", produces = "application/json")
    public List<AutenticacionUsuario>darAuntenticaciones()
    {
        return autenticacionUsuarioRepository.findAll();
    }


}