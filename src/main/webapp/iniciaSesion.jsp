<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="error" scope="request" type="java.lang.String"/>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Iniciar sesion</title>
        <link rel="stylesheet" href="signup.css"/>
        <!-- referenciando bootstrap-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <style>
            .text-center {
                text-align: center;
            }
            .etiUser {
                color: #ffffff;
            }
            .etiUser2{
                color: #b40505;
            }
        </style>
    </head>
    <body background="fondo.jpg">
        <!-- Login-->
        <form id="signup" method="POST" action="<%=request.getContextPath()%>/">
            <div class="text-center">
                <img src="logo.png" width="200"/></a>
            </div>
            <br/>
            <div class="card-header">
                <div class="text-center">
                    <h3 class="etiUser">Bienvenido Televiajero</h3>
                </div>
            </div>
            <input type="email" name="usuario" placeholder="Usuario (Correo PUCP)" required="">
            <input type="password" name="contrasena" placeholder="Contraseña" required="">
            <% if(error.equals("credencialesIncorr")) {%>
            <div class="text-danger mb-2">Error en usuario o contraseña</div>
            <%}else if(error.equals("noEsTeleco")){%>
            <div class="text-danger mb-2">Usted no pertenece a la gloriosa especialidad de Telecom</div>
            <%}else{%>
            <br/>
            <br/>
            <%}%>
            <div class="text-center">
                <input type="submit" class="btn btn-outline-primary" value="Ingresar"/>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center">
                    <div class="etiUser2">
                        <a href="<%=request.getContextPath()%>/UsuarioServlet"> <u>Soy nuevo y quiero registrarme</u></a>
                    </div>
                </div>
            </div>
        </form>

    </body>
</html>