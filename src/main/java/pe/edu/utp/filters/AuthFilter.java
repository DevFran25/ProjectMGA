package pe.edu.utp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.utp.util.ErrorLog;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//@WebFilter("/*")
public class AuthFilter implements Filter {


    private Set<String> exceptionUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        exceptionUrl = new HashSet<>();
        exceptionUrl.add("/register-nuevo.html");
        exceptionUrl.add("/register.html");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        //System.out.println("Ruta Interceptada: " + path);
        ErrorLog.log("Ruta interceptada: " + path, ErrorLog.Level.INFO);

        HttpSession session = req.getSession();
        String cargo = (String) session.getAttribute("cargo");



        filterChain.doFilter(servletRequest, servletResponse);
    }
}
