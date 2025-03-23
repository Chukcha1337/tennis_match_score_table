package com.chuckcha.filters;

import com.chuckcha.exceptions.ExceptionHandler;
import com.chuckcha.util.JspHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlerFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(req, resp, chain);
        } catch (Throwable throwable) {
            ExceptionHandler.handleExceptions(req, throwable);
            req.getRequestDispatcher(JspHelper.getPath("error")).forward(req, resp);
        }
    }
}