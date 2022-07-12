package com.example.finalpractice.Servlets;

import com.example.finalpractice.Beans.EstudianteBean;
import com.example.finalpractice.Beans.ViajeBean;
import com.example.finalpractice.Daos.LoginDao;
import com.example.finalpractice.Daos.PrincipalDao;
import com.mysql.cj.log.Log;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "PrincipalServlet", value = "/PrincipalServlet")
public class PrincipalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LoginDao loginDao = new LoginDao();
        HttpSession session = request.getSession();
        EstudianteBean estud = (EstudianteBean) session.getAttribute("estudianteSession");
        String action = request.getParameter("a") == null? "listar" : request.getParameter("a");
        PrincipalDao principalDao = new PrincipalDao();
        switch (action){
            case "listar" -> {
                estud.setStatus(loginDao.obtenerStatus(estud));
                request.setAttribute("listaViajes",principalDao.listarViajes(estud.getCodigoPUCP()));
                RequestDispatcher view = request.getRequestDispatcher("principal.jsp");
                view.forward(request, response);
            }
            case "crear" -> {
                session.setAttribute("estudiante", estud);
                RequestDispatcher view = request.getRequestDispatcher("crearViaje.jsp");
                view.forward(request, response);
            }
            case "editar" -> {
                String idViaje = request.getParameter("id");
                ViajeBean viajeBean = principalDao.buscarViaje(idViaje);
                if(viajeBean != null){
                    session.setAttribute("estudiante", estud);
                    request.setAttribute("viajeBean",viajeBean);
                    RequestDispatcher view = request.getRequestDispatcher("editarViaje.jsp");
                    view.forward(request, response);
                }else{
                    response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        EstudianteBean estud = (EstudianteBean) session.getAttribute("estudianteSession");
        String action = request.getParameter("a") == null? "listar" : request.getParameter("a");
        PrincipalDao principalDao = new PrincipalDao();
        switch (action){
            case "buscar" -> {
                String nameCiudad = request.getParameter("ciudad");
                request.setAttribute("listaViajes",principalDao.listarViajesFiltrada(estud.getCodigoPUCP(),nameCiudad)); //luego cambiar con session

                RequestDispatcher view = request.getRequestDispatcher("principal.jsp");
                view.forward(request, response);
            }
            case "guardar" -> {
                String idViaje = "";
                String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
                for (int i = 0; i < 8; i++ ) {
                    idViaje += nums[(int) Math.round(Math.random() * 9)];
                }

                String fechaViaje = request.getParameter("fechaViaje");
                //Fecha reserva es la actual
                LocalDate fechaReserva = LocalDate.now();
                String origen = request.getParameter("origen");
                String destino = request.getParameter("destino");
                String seguroRec = request.getParameter("seguro");
                String seguro = "";
                if(seguroRec.contains("_")){
                    String palabras[] = seguroRec.split("_");
                    seguro = palabras[0]+" "+palabras[1];
                }else{
                    seguro=seguroRec;
                }
                String numBoletos = request.getParameter("numBoletos");
                String costoTotal = request.getParameter("costoTotal");

                principalDao.crearViaje(idViaje,fechaViaje,fechaReserva.toString(),origen,destino,seguro,numBoletos,costoTotal);

                principalDao.crearViajeEnEstudiante(estud.getCodigoPUCP(),idViaje); //luego cambiar con session

                response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
            }
            case "actualizar" -> {
                String idViaje = request.getParameter("idViaje");
                String fechaViaje = request.getParameter("fechaViaje");
                String fechaReserva = request.getParameter("fechaReserva");
                String origen = request.getParameter("origen");
                String destino = request.getParameter("destino");
                String seguroRec = "";
                if(request.getParameter("seguro1") != null){
                    seguroRec = request.getParameter("seguro1"); //si es que lo edita el usuario
                }else {
                    seguroRec = request.getParameter("seguro2");  //el original
                }
                String seguro = "";
                if(seguroRec.contains("_")){
                    String palabras[] = seguroRec.split("_");
                    seguro = palabras[0]+" "+palabras[1];
                }else{
                    seguro=seguroRec;
                }
                String numBoletos = request.getParameter("numBoletos");
                String costoTotal = request.getParameter("costoTotal");
                principalDao.editarViaje(idViaje,fechaViaje,fechaReserva,origen,destino,seguro,numBoletos,costoTotal);
                response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
            }
            case "borrar" -> {
                LoginDao loginDao = new LoginDao();
                String idViaje = request.getParameter("id");
                String contrasen = request.getParameter("password");
                if(loginDao.validarContrasenia(estud.getCorreoPUCP(),contrasen)!=null){
                    principalDao.borrarPorEstudiante(idViaje);
                    principalDao.borrar(idViaje);
                    request.getSession().setAttribute("mensaje","exito");
                    response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
                }else{
                    request.getSession().setAttribute("mensaje", "error");
                    response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
                }
            }
        }
    }
}
