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

import static project.util.ConstantUtil.CHANGE_LOCALE;
import static project.util.ConstantUtil.REGISTER_PAGE;
import static project.util.ConstantUtil.START_PAGE;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isStartPage(servletRequest) || isChangeLocaleQuery(servletRequest) || isRegisterPage(servletRequest) || isLoginPage(servletRequest) || isUserAuthorized(servletRequest)) {
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

    public boolean isStartPage(ServletRequest servletRequest) {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        return START_PAGE.equals(requestURI);
    }

    public boolean isRegisterPage(ServletRequest servletRequest) {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        return REGISTER_PAGE.equals(requestURI);
    }

    public boolean isChangeLocaleQuery(ServletRequest servletRequest) {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        return CHANGE_LOCALE.equals(requestURI);
    }
}
