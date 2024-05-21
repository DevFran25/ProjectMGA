package pe.edu.utp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        String cargo = (String) session.getAttribute("cargo");

        if( cargo != null){
            if(cargo.equals("admin")){
                resp.sendRedirect(req.getContextPath()+"/");
            }else{
                resp.sendRedirect(req.getContextPath()+"/"+cargo);
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

}
