package com.example.finalpractice.Filters;

import com.example.finalpractice.Beans.EstudianteBean;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",servletNames = {"LoginServlet"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        EstudianteBean estudiante = (EstudianteBean) req.getSession().getAttribute("estudianteSession");
        String salir = req.getParameter("action");

        if(salir == null){
            salir = "";
        }

        if(estudiante == null || estudiante.getCodigoPUCP().equals("") || salir.equals("logout")){
            chain.doFilter(request, response);
        }else{
            resp.sendRedirect(req.getContextPath()+"/PrincipalServlet");
        }
    }
}
