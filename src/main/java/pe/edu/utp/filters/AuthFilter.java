package pe.edu.utp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("*.html")
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

        if(exceptionUrl.contains(path)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = req.getSession();
        String cargo = (String) session.getAttribute("cargo");

        if( cargo == null || cargo.isBlank() ){
            resp.sendRedirect(req.getContextPath()+"/login");
        }else if( !cargo.equals("admin")  ){
            resp.sendRedirect(req.getContextPath()+"/"+cargo);
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
