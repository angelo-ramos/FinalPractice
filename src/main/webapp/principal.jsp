<%@ page import="com.example.finalpractice.Beans.ViajeBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="estudianteSession" scope="session" type="com.example.finalpractice.Beans.EstudianteBean"/>
<jsp:useBean id="listaViajes" scope="request" type="java.util.ArrayList<com.example.finalpractice.Beans.ViajeBean>" />
<html>
<head>
    <title>TeleViajes</title>
  <!--<link rel="shortcut icon" href="/" type="image/x-icon">-->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link href="estilosPrincipal.css" rel="stylesheet" type="text/css">
  <style>
    <% if(estudianteSession.getStatus().equals("Normal")){%>
    nav {
      background: blue;
    }
    <%} else if (estudianteSession.getStatus().equals("Silver")){%>
    nav {
    background: #e3e4e5;
    }
    <%} else if (estudianteSession.getStatus().equals("Gold")){%>
    nav {
    background: goldenrod;
    }
    <%} else if (estudianteSession.getStatus().equals("Platinum")){%>
    nav {
    background: black;
    }
    <%}%>
    nav{
      height: 80px;
      width: 100%;
    }
    .lista {
      background-color: white;
      margin-top: 50px;
      padding-left: 20px;
      padding-right: 20px;
    }
  </style>
</head>
<nav>
  <input type="checkbox" id="check">
  <label for="check"  class="checkbtn">
    <i class="fa fa-bars"></i>
  </label>
  <label class="logo"><span>Televiajes</span></label>
  <ul>
    <label class="nombre"><span><%=estudianteSession.getNombre() + " " + estudianteSession.getApellido()%></span></label>
    <label class="status"><span><%=estudianteSession.getStatus()%></span></label>
    <li><a href="<%=request.getContextPath()%>/?action=logout">Cerrar Sesión</a></li>
  </ul>
</nav>
<section>
<div class='container lista' style="padding-top:10px; padding-bottom: 10px">
  <%if (session.getAttribute("mensaje")!=null){%>
  <% if (session.getAttribute("mensaje").equals("error")){%>
  <div class="alert alert-danger" role="alert">Contraseña incorrecta, registro no eliminado</div>
  <% } else {%>
  <div class="alert alert-success" role="alert">Viaje eliminado exitosamente!</div>
  <%} %>
  <% session.removeAttribute("mensaje");}%>
  <h1 class='mb-3'>Lista de viajes</h1>

  <a href="<%=request.getContextPath()%>/PrincipalServlet?a=crear" class="btn btn-primary mb-4">Agregar nuevo viaje</a>

  <form method="post" class="row g-3" action="<%=request.getContextPath()%>/PrincipalServlet?a=buscar">
    <div class="col-auto">
      <p>Búsqueda por ciudad: </p>
    </div>
    <div class="col-auto">
      <input type="text" class="form-control" name="ciudad" id="ciudad" placeholder="Origen o Destino">
      <div class="invalid-feedback">
        In this case is not necessary
      </div>
    </div>
    <div class="col-auto">
      <button type="submit" class="btn btn-primary mb-3">Buscar</button>
    </div>
  </form>

  <table class="table">
    <thead>
      <tr>
        <th>Identificador</th>
        <th>Fecha reserva</th>
        <th>Fecha viaje</th>
        <th>Ciudad origen</th>
        <th>Ciudad destino</th>
        <th>Seguro</th>
        <th>Num boletos</th>
        <th>Costo total (S/.)</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <%int i = 1;
        for(ViajeBean viaje : listaViajes){ %>
      <tr>
        <td><%=viaje.getIdViaje()%></td>
        <td><%=viaje.getFechaReserva()%></td>
        <td><%=viaje.getFechaViaje()%></td>
        <td><%=viaje.getCiudadOrigen()%></td>
        <td><%=viaje.getCiudadDestino()%></td>
        <td><%=viaje.getSeguro()%></td>
        <td><%=viaje.getNumBoletos()%></td>
        <td><%=viaje.getCostoTotal()%></td>
        <td><a class="btn btn-primary" href="<%=request.getContextPath()%>/PrincipalServlet?a=editar&id=<%=viaje.getIdViaje()%>">Editar</a></td>
        <td><button type="button" data-bs-toggle="modal" data-bs-target="#Modal<%=i%>" class="btn btn-danger">Borrar</button></td>
      </tr>
      <%}%>
    </tbody>

  </table>
</div>
  <!-- Modal -->
  <%int j = 1;
    for(ViajeBean viaje2 : listaViajes){ %>
  <div class="modal fade" id="Modal<%=j%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Advertencia</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          Si esta seguro de eliminar ingrese contraseña
          <form method="post" class="row g-3" action="<%=request.getContextPath()%>/PrincipalServlet?a=borrar&id=<%=viaje2.getIdViaje()%>">
            <div class="col-auto">
              <BR>
              <input type="password" class="form-control" name="password" id="password" placeholder="Contraseña">
            </div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            <button type="submit" class="btn btn-danger">Borrar</button>
          </form>
        </div>
      </div>
    </div>
  </div>
  <%j++;}%>
</section>
  <!-- Bootstrap core JS-->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Core theme JS-->
  <script src="js/scripts.js"></script>
  <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
  <!-- * *                               SB Forms JS                               * *-->
  <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
  <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
  <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>
