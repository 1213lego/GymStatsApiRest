package com.gymstatsapirest.controller;
import com.gymstatsapirest.model.Maquina;
import com.gymstatsapirest.model.Tarifa;
import com.gymstatsapirest.model.Usuario;
import com.gymstatsapirest.service.ServicioAdministrador;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(value="Administrador ", description="Se encarga de todas las operaciones del administrador")
@RestController
@RequestMapping(path = "/admin")
@CrossOrigin(origins = "*")
public class RestAdministrador {

    @Autowired
    private ServicioAdministrador servicioAdministrador;

    @ApiOperation(value = "Registra un nuevo empleado en la base de datos" ,
            notes = "Retorna el nuevo empleado si este fue creado, de lo contrario genera un json con sus respectivos erores y codigo de respuesta Bad Request 400 ",
            response = Usuario.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Se ha creado satisfatoriamente"),
            @ApiResponse(code = 400, message = "Los datos suministrados no son validos se retornara un body con su respectivos detalles")
    })
    @PostMapping(path = "/empleados",consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> crearCliente(@ApiParam(value = "cliente a guardar", required = true) @Valid @RequestBody Usuario usuario, BindingResult bindingResult)
    {
        //Si el usuario a crear tiene errores en alguno de sus campos
        if (bindingResult.hasErrors()) {
            //generamos una respuesta con map clave (campo del error) valor(mensaje del error en el campo)
            return servicioAdministrador.badRequestErrorFields(bindingResult.getFieldErrors());
        }
        Map<String, String> validacion = servicioAdministrador.empleadoValidoParaCrear(usuario);
        if (validacion.size() > 0) {
            return new ResponseEntity<>(validacion, HttpStatus.BAD_REQUEST);
        }
        Usuario newEpleado = servicioAdministrador.crearEmpleado(usuario);
        return new ResponseEntity<>(newEpleado, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Crea una nueva tarifa en la base de datos" ,
            notes = "Retorna la nueva tarifa si esta fue creada, de lo contrario genera un json con sus respectivos erores y codigo de respuesta Bad Request 400 "
            )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Se ha creado satisfatoriamente"),
            @ApiResponse(code = 400, message = "Los datos suministrados no son validos se retornara un body con su respectivos detalles")
    })
    @PostMapping(path = "/tarifas",consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> crearTarifa(@ApiParam(value = "cliente a guardar", required = true) @Valid @RequestBody Tarifa tarifa, BindingResult bindingResult)
    {
        //Si el usuario a crear tiene errores en alguno de sus campos
        if (bindingResult.hasErrors()) {
            //generamos una respuesta con map clave (campo del error) valor(mensaje del error en el campo)
            return servicioAdministrador.badRequestErrorFields(bindingResult.getFieldErrors());
        }
        Tarifa newTarifa = servicioAdministrador.crearTarifa(tarifa);
        return new ResponseEntity<>(newTarifa, HttpStatus.CREATED);
    }

    @ApiOperation
            (value = "Actualiza una tarifa" , response = Tarifa.class)
    @ApiResponses
            (value = {
                @ApiResponse(code = 201, message = "Se ha actualizado satisfatoriamente"),
                @ApiResponse(code = 404, message = "No se ha actualizado debido a que no encuentra la tarifa con identificador suministrado"),
                    @ApiResponse(code = 400, message = "Los datos suministrados no son validos se retornara un body con su respectivos detalles")
    })
    @PutMapping(path = "/tarifas/{idTarifa}",consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity modificarTarifa( @Valid @RequestBody Tarifa tarifa, BindingResult bindingResult, @PathVariable Short idTarifa)
    {
        if(bindingResult.hasErrors())
        {
            return servicioAdministrador.badRequestErrorFields(bindingResult.getFieldErrors());
        }
        return servicioAdministrador.actualizarTarifa(tarifa,idTarifa);
    }

    @ApiOperation
            (value = "Agrega un maquina a la base de datos")
    @ApiResponses
            (value = {
                    @ApiResponse(code = 201, message = "Se ha agregado satisfatoriamente"),
                    @ApiResponse(code = 404, message = "No se ha creado debido a que los datos no son validos,se retornara un Json con sus respectivos detalles")
            })
    @PostMapping(path = "/maquinas",consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> crearMaquina(@Valid @RequestBody  Maquina maquina, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return servicioAdministrador.badRequestErrorFields(bindingResult.getFieldErrors());
        }
        Maquina newMaquina=servicioAdministrador.crearMaquina(maquina);
        return new ResponseEntity<>(newMaquina,HttpStatus.CREATED);
    }



    //De prueba
    @GetMapping(path = "/tarifas", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<?> darTarifas()
    {
        return servicioAdministrador.darTarifas();
    }

}
