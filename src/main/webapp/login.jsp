<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <title>Login de Usuario</title>
</head>
<body>
    <h2>Formulario de Login</h2>
    <form action="login" method="post">
        <label>Email: <input type="email" name="email"/></label><br/>
        <label>Contraseña: <input type="password" name="password"/></label><br/>
        <button type="submit">Ingresar</button>
    </form>

    <p>${mensaje}</p>
</body>
</html>

