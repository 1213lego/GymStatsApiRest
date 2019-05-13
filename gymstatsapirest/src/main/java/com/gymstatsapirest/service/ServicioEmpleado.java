package com.gymstatsapirest.service;
import com.gymstatsapirest.exception.RecursoNoEncontradoException;
import com.gymstatsapirest.model.Cliente;
import com.gymstatsapirest.model.Suscripcione;
import com.gymstatsapirest.model.Tarifa;
import com.gymstatsapirest.model.Usuario;
import com.gymstatsapirest.repository.ClienteRepository;
import com.gymstatsapirest.repository.SuscripcionesRepository;
import com.gymstatsapirest.repository.TarifaRepository;
import com.gymstatsapirest.repository.UsuarioRepository;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioEmpleado
{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private SuscripcionesRepository suscripcionesRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TarifaRepository tarifaRepository;

    public Optional<Usuario> darUsuario(Integer documento)
    {
        return usuarioRepository.findById(documento);
    }

    public Utils getUtils() {
        return utils;
    }

    public ResponseEntity registrarSuscripcionDiaria(Cliente cliente)
    {
        Optional<Cliente> usuario=clienteRepository.findById(cliente.getDocumento());
        if(!usuario.isPresent())
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            try
            {
                Suscripcione nuevaSuscripcion= new Suscripcione();
                nuevaSuscripcion.setCliente(usuario.get());
                nuevaSuscripcion.setFechaInicio(new Timestamp(System.currentTimeMillis()));
                String pattern = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date fechaFin = simpleDateFormat.parse(utils.getAnioActual()+"-"+utils.getMesActual()+"-"+utils.getDiaActual() + " 23:59:59");
                System.out.println(utils.getAnioActual()+"-"+utils.getMesActual()+"-"+utils.getDiaActual() + " 23:59:59");
                nuevaSuscripcion.setFechaFin(fechaFin);
                nuevaSuscripcion.setEstadoSuscripcion(utils.getEstadoSuscripcionVigente());
                nuevaSuscripcion.setTarifa(utils.getTarifaDiaria());
                suscripcionesRepository.save(nuevaSuscripcion);
                return new ResponseEntity(HttpStatus.OK);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public ResponseEntity registrarSuscripcion(Suscripcione suscripcion)
    {
        Cliente cliente=clienteRepository.findById(suscripcion.getCliente().getDocumento())
                .orElseThrow(()->new RecursoNoEncontradoException("cliente","documento",suscripcion.getCliente().getDocumento()+""));

        Tarifa tarifa=tarifaRepository.findById(suscripcion.getTarifa().getIdTarifa())
                .orElseThrow(()->new RecursoNoEncontradoException("tarifa","idTarifa",suscripcion.getTarifa().getIdTarifa()));
        Suscripcione nuevaSuscripcion= new Suscripcione();
        nuevaSuscripcion.setCliente(cliente);
        nuevaSuscripcion.setFechaInicio(new Timestamp(System.currentTimeMillis()));
        nuevaSuscripcion.setTarifa(tarifa);
        nuevaSuscripcion.setEstadoSuscripcion(utils.getEstadoSuscripcionVigente());
        Date date=new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, tarifa.getDuracionDias());
        nuevaSuscripcion.setFechaFin(calendar.getTime());
        suscripcionesRepository.save(nuevaSuscripcion);
        return new ResponseEntity(HttpStatus.OK);
    }
    public List<Usuario> listaClientes(int page, int size)
    {
        return usuarioRepository.listarUsuarios(PageRequest.of(page,size),utils.getTipoUsuarioCliente());
    }
    public List<Usuario> listarEmpleados(int page,int size)
    {
        return usuarioRepository.listarUsuarios(PageRequest.of(page,size),utils.getTipoUsuarioEmpleado());
    }
}
