package com.example.finalpractice.Servlets;

import com.example.finalpractice.Beans.EstudianteBean;
import com.example.finalpractice.Daos.LoginDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action")==null?"lista":request.getParameter("action");
        switch (action){
            case "lista" -> {
                RequestDispatcher view = request.getRequestDispatcher("iniciaSesion.jsp");
                view.forward(request, response);
            }
            case "logout" -> {
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect(request.getContextPath());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginDao loginDao = new LoginDao();
        EstudianteBean estudiante;
        String username = request.getParameter("usuario");
        String password = request.getParameter("contrasena");
        boolean isteleco = loginDao.validarTeleco(username);
        if (!isteleco){
            request.getSession().setAttribute("msg","Usted no pertenece a la gloriosa especialidad de Telecom");
            response.sendRedirect(request.getContextPath());
        }else{
            estudiante = loginDao.validarContrasenia(username, password);
            if(estudiante==null){
                request.getSession().setAttribute("msg","Error en usuario o contraseña");
                response.sendRedirect(request.getContextPath());
            }else{
                estudiante.setStatus(loginDao.obtenerStatus(estudiante));
                HttpSession session = request.getSession();
                session.setAttribute("estudianteSession", estudiante);
                session.setMaxInactiveInterval(60 * 10);
                response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
            }
        }
    }
}
