package com.example.finalpractice.Beans;

import com.example.finalpractice.DTO.StatusDTO;

public class EstudianteBean extends StatusDTO {
    private String codigoPUCP;
    private String nombre;
    private String apellido;
    private int edad;
    private String correoPUCP;
    private String especialidad;
    private String contrasenia;

    public String getCodigoPUCP() {
        return codigoPUCP;
    }

    public void setCodigoPUCP(String codigoPUCP) {
        this.codigoPUCP = codigoPUCP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreoPUCP() {
        return correoPUCP;
    }

    public void setCorreoPUCP(String correoPUCP) {
        this.correoPUCP = correoPUCP;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
