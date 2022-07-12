package com.example.finalpractice.Filters;

import com.example.finalpractice.Beans.EstudianteBean;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EstudianteFilter",servletNames = {"PrincipalServlet"})
public class EstudianteFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        EstudianteBean estudiante = (EstudianteBean) req.getSession().getAttribute("estudianteSession");
        if(estudiante != null && !estudiante.getCodigoPUCP().equals("")){
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            resp.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        }else{
            resp.sendRedirect(req.getContextPath());
        }
    }
}
