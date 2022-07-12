<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="estilosCrearUser.css">
    <title>Nuevo Usuario</title>
</head>
<body>

<form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?action=guardar">
    <%if(mensaje.equals("nulos")){%>
    <div class="alert alert-danger" role="alert">
        ¡Datos incompletos!
    </div>
    <%}%>
    <h3>Registrar datos del estudiante</h3>
    <label>Nombre:</label>
    <input type="text" name="nombre" pattern="[a-zA-Z]+" oninvalid="setCustomValidity('Por favor solo ingrese un nombre')" onchange="try{setCustomValidity('')}catch(e){}" placeholder="Ingrese su nombre" style="width: 70%;">
    <BR>
    <label>Apellido:</label>
    <input type="text" name="apellido" pattern="[a-zA-Z]+" oninvalid="setCustomValidity('Por favor solo ingrese un apellido')" onchange="try{setCustomValidity('')}catch(e){}" placeholder="Ingrese su apellido" style="width: 70%;">
    <BR>
    <label>Edad:</label>
    <input type="number" name="edad" placeholder="Ingrese su edad" min="18" max="29"/>
    <label>Código PUCP:</label>
    <input type="text" name="codigo" pattern="[0-9]+" oninvalid="setCustomValidity('Por favor ingrese 8 dígitos')" onchange="try{setCustomValidity('')}catch(e){}" minlength="8" maxlength="8" placeholder="Ingrese su código PUCP">
    <label>Especialidad:</label>
    <input type="text" name="especialidad" placeholder="Ingrese su especialidad" style="width: 70%;">
    <BR>
    <label>Correo PUCP:</label>
    <input type="email" name="correo"  placeholder="Ingrese su correo PUCP" style="width: 70%;">
    <%if(mensaje.equals("correo")){%>
    <div class="alert alert-warning" role="alert">
        El correo debe ser numérico
    </div>
    <%}else{%>
    <BR>
    <%}%>
    <label>Cree una contraseña:</label>
    <input type="password" name="contrasenia" placeholder="Ingrese una contraseña">
    <%if(mensaje.equals("noCumpleCondiciones")){%>
    <div class="alert alert-warning" role="alert">
        La contraseña debe tener una mayúscula, un dígito y un caracter especial
    </div>
    <%}else{%>
    <BR>
    <%}%>
    <label>Confirme su contraseña:</label>
    <input type="password" name="confirmPassw" placeholder="Repita la contraseña">
    <%if(mensaje.equals("noCoinciden")){%>
    <div class="alert alert-warning" role="alert">
        Las contraseñas no coinciden
    </div>
    <%}%>
    <tr>
        <input type="submit" value="Guardar"/>
        <a style="border-radius: 20px;" class="btn btn-danger col-6"  href="<%=request.getContextPath()%>/" class="btn btn-danger">Regresar</a>
    </tr>
</form>
</body>
</html>