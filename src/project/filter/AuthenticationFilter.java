package project.filter;

import project.dto.userdto.UserDataForSession;
import project.util.MappingForAdminConst;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isLoginPage(servletRequest) || isUserAuthorized(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

    private boolean isUserAuthorized(ServletRequest servletRequest) {
        UserDataForSession user = (UserDataForSession) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return Objects.nonNull(user);
    }

    public boolean isLoginPage(ServletRequest servletRequest) {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        return MappingForAdminConst.LOGIN_PAGE.equals(requestURI);
    }
}
