<%@ page import="com.example.finalpractice.Beans.ViajeBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="estudiante" scope="session" type="com.example.finalpractice.Beans.EstudianteBean"/>
<html>
    <head>
        <title>TeleViajes</title>
        <!--<link rel="shortcut icon" href="/" type="image/x-icon">-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="estilosPrincipal.css" rel="stylesheet" type="text/css">
        <style>
            <% if(estudiante.getStatus().equals("Normal")){%>
            nav {
                background: blue;
            }
            <%} else if (estudiante.getStatus().equals("Silver")){%>
            nav {
                background: #e3e4e5;
            }
            <%} else if (estudiante.getStatus().equals("Gold")){%>
            nav {
                background: goldenrod;
            }
            <%} else if (estudiante.getStatus().equals("Platinum")){%>
            nav {
                background: black;
            }
            <%}%>
            nav {
                height: 80px;
                width: 100%;
            }
            .lista {
                width: 50%;
                background-color: white;
                margin-top: 10px;
                margin-bottom: 50px;
                padding-bottom: 30px;
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
            <label class="nombre"><span><%=estudiante.getNombre() + " " + estudiante.getApellido()%></span></label>
            <label class="status"><span><%=estudiante.getStatus()%></span></label>
            <li><a href="<%=request.getContextPath()%>/?action=logout">Cerrar Sesión</a></li>
        </ul>
    </nav>
    <section>
        <div class="container lista">
            <h1 class="mb-3">Añadir un nuevo viaje</h1>
            <%LocalDate todaysDate = LocalDate.now();
                LocalDate tommorrosDate = todaysDate.plusDays(1);
                String tomorrowToStr = tommorrosDate.toString();
                String todayToStr = todaysDate.toString();
            %>
            <form method="POST" action="<%=request.getContextPath()%>/PrincipalServlet?a=guardar">
                <div class="mb-3">
                    <label for="fechaViaje" class="form-label">Fecha de viaje</label>
                    <input type="date" class="form-control" name="fechaViaje" id="fechaViaje" min="<%=tomorrowToStr%>" required>
                </div>
                <div class="mb-3">
                    <label for="fechaReserva" class="form-label">Fecha de reserva</label>
                    <input type="date" class="form-control" name="fechaReserva" id="fechaReserva" value="<%=todayToStr%>" disabled>
                </div>
                <div class="mb-3">
                    <label for="origen" class="form-label">Ciudad Origen</label>
                    <input type="text" class="form-control" name="origen" id="origen" required>
                </div>
                <div class="mb-3">
                    <label for="destino" class="form-label">Ciudad Destino</label>
                    <input type="text" class="form-control" name="destino" id="destino" required>
                </div>
                <div class="form-group">
                    <!--Lista fija-->
                    <label for="seguro" class="form-label">Empresa de seguro</label>
                    <select class="form-select" name="seguro" id="seguro" required>
                        <option selected disabled value="">Seleccionar empresa de seguro</option>
                        <option value="Rimac">Rimac</option>
                        <option value="Pacifico">Pacifico</option>
                        <option value="La_Positiva">La positiva</option>
                        <option value="Seguro_Internacional">Seguro Internacional</option>
                        <option value="Otro">Otro</option>
                    </select>
                </div>
                <div class="mb-3" style="margin-top: 7px; margin-bottom: 7px">
                    <label for="numBoletos" class="form-label">Numero de boletos</label>
                    <input type="number" class="form-control" name="numBoletos" id="numBoletos" min="1" step="1" required>
                </div>
                <div class="mb-3" style="margin-top: 7px; margin-bottom: 7px">
                    <label for="costoTotal" class="form-label">Costo total</label>
                    <input type="number" class="form-control" name="costoTotal" id="costoTotal" min="0.1" step="0.1" required>
                </div>
                <a href="<%=request.getContextPath()%>/PrincipalServlet" class="btn btn-danger">Regresar</a>
                <button type="submit" class="btn btn-primary">Añadir viaje</button>  <! --tipo "submit" es para que se envie el formulario, importante-->
            </form>
        </div>
    </section>
    </body>
</html>

