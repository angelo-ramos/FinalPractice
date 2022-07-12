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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String action = request.getParameter("action")==null?"lista":request.getParameter("action");
        switch (action){
            case "lista" -> {
                String mensaje = request.getParameter("error")==null?"":request.getParameter("error");
                request.setAttribute("error",mensaje);
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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        LoginDao loginDao = new LoginDao();
        EstudianteBean estudiante;
        String username = request.getParameter("usuario");
        String password = request.getParameter("contrasena");
        boolean isteleco = loginDao.validarTeleco(username);
        if (!isteleco){
            response.sendRedirect(request.getContextPath() + "/?error=noEsTeleco");
        }else{
            estudiante = loginDao.validarContrasenia(username, password);
            if(estudiante==null){
                response.sendRedirect(request.getContextPath() + "/?error=credencialesIncorr");
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
