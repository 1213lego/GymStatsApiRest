package com.gymstatsapirest.controller;
import com.gymstatsapirest.model.AutenticacionUsuario;
import com.gymstatsapirest.model.Maquina;
import com.gymstatsapirest.model.Tarifa;
import com.gymstatsapirest.service.ServicioMain;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Api(value="main ", description="Se encarga de las operaciones en general que no requieren de autenticacion")
@RestController
@CrossOrigin(origins = "*")
public class RestMain
{
    @Autowired
    private ServicioMain servicioMain;

    @GetMapping(path = "/tarifas", produces = "application/json")
    public List<Tarifa> darTarifas()
    {
        return servicioMain.darTarifas();
    }

    @GetMapping(path = "/maquinas", produces = "application/json")
    public List<Maquina> darMaquinas()
    {
        return servicioMain.darMaquina();
    }

    @PostMapping(path = "/login",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> login(@Valid @RequestBody AutenticacionUsuario autenticacionUsuario, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return servicioMain.getUtils().badRequestErrorFields(bindingResult.getFieldErrors());
        }
        return servicioMain.login(autenticacionUsuario);
    }

}
