package com.example.finalpractice.Servlets;

import com.example.finalpractice.Beans.EstudianteBean;
import com.example.finalpractice.Daos.EstudianteDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UsuarioServlet", value = "/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String mensaje = request.getParameter("mensaje")==null?"":request.getParameter("mensaje");
        request.setAttribute("mensaje",mensaje);
        RequestDispatcher view = request.getRequestDispatcher("nuevoUsuario.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        EstudianteBean estudiante = new EstudianteBean();
        EstudianteDao estudianteDao = new EstudianteDao();
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action")==null?"":request.getParameter("action");
        switch (action){
            case "guardar":
                try {
                    String nombre = request.getParameter("nombre")==null?"": request.getParameter("nombre");
                    String apellido = request.getParameter("apellido")==null?"": request.getParameter("apellido");
                    int edad = Integer.parseInt(request.getParameter("edad").equals("")?"0":request.getParameter("edad"));
                    String codigo = request.getParameter("codigo").equals("")?"":request.getParameter("codigo");
                    String especialidad = request.getParameter("especialidad")==null?"": request.getParameter("especialidad");
                    String correo = request.getParameter("correo")==null?"": request.getParameter("correo");
                    String contrasenia = request.getParameter("contrasenia")==null?"": request.getParameter("contrasenia");
                    String confirmPassw = request.getParameter("confirmPassw")==null?"": request.getParameter("confirmPassw");
                    if(nombre.equals("") || apellido.equals("") || edad==0 || codigo.equals("") || especialidad.equals("")){
                        response.sendRedirect(request.getContextPath()+"/UsuarioServlet?mensaje=nulos");
                    } else if(!validaCorreo(correo)){
                        response.sendRedirect(request.getContextPath()+"/UsuarioServlet?mensaje=correo");
                    } else if (!contrasCumpleCondiciones(contrasenia)){
                        response.sendRedirect(request.getContextPath()+ "/UsuarioServlet?mensaje=noCumpleCondiciones");
                    } else if (!contrasenia.equals(confirmPassw)){
                        response.sendRedirect(request.getContextPath()+ "/UsuarioServlet?mensaje=noCoinciden");
                    } else{
                        estudianteDao.crearEstudiante(nombre, apellido, edad, codigo, especialidad, correo, contrasenia);
                        response.sendRedirect(request.getContextPath()+"/");
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                break;
        }
    }
    public boolean validaCorreo(String correo){
        EstudianteDao estudianteDao = new EstudianteDao();
        boolean isValido=true;
        String usuario = correo.split("@")[0];
        for (int i = 0; i < usuario.length(); i++) {
            if (!Character.isDigit(usuario.charAt(i))) {
                isValido = false;
            }
        }
        if(estudianteDao.existeUsuario(correo)){
            isValido = false;
        }
        return isValido;
    }
    public boolean contrasCumpleCondiciones(String contrasenia){
        char[] caractEspeciales = {'#','@','.','_',':',';','+','-','*','$','%'};
        boolean hasMayusc = false;
        boolean hasNum = false;
        boolean hasCharEspec = false;
        for (int i=0; i < contrasenia.length(); i++ ) {
            char c = contrasenia.charAt(i);
            if(Character.isUpperCase(c))
                hasMayusc=true;
            else if(Character.isDigit(c))
                hasNum=true;
            else {
                for(int j=0; j<caractEspeciales.length; j++) {
                    if(caractEspeciales[j] == c) {
                        hasCharEspec=true;
                    }
                }
            }
        }
        return (hasMayusc && hasCharEspec && hasNum);
    }
}

