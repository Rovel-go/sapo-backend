package com.casaPrincesas.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet de ejemplo para registro de usuarios.
 * Adaptado a Jakarta Servlet API (Spring Boot 3 / Tomcat 10+).
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Configurar respuesta
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Validación básica
        if (nombre == null || email == null || password == null ||
                nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            out.println("<html><body>");
            out.println("<h2>Error: Todos los campos son obligatorios.</h2>");
            out.println("</body></html>");
            return;
        }

        // Aquí podrías integrar lógica con tu capa de servicio/repositorio
        // Ejemplo: guardar usuario en base de datos con JPA

        out.println("<html><body>");
        out.println("<h2>Registro exitoso</h2>");
        out.println("<p>Bienvenido, " + nombre + "!</p>");
        out.println("<p>Email registrado: " + email + "</p>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar formulario simple en GET
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Formulario de Registro</h2>");
        out.println("<form method='post' action='registro'>");
        out.println("Nombre: <input type='text' name='nombre'/><br/>");
        out.println("Email: <input type='email' name='email'/><br/>");
        out.println("Contraseña: <input type='password' name='password'/><br/>");
        out.println("<input type='submit' value='Registrar'/>");
        out.println("</form>");
        out.println("</body></html>");
    }
}








