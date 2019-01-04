package project.filter;

import project.dto.userdto.UserDataForSession;
import project.entity.enumonly.Role;

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

import static project.util.MappingForAdminConst.ADD_NEW_GAME_PAGE;
import static project.util.MappingForAdminConst.ADD_TO_STORAGE_PAGE;
import static project.util.MappingForAdminConst.ALL_ORDERS_PAGE;
import static project.util.MappingForAdminConst.ALL_USERS_PAGE;
import static project.util.MappingForAdminConst.CHANGE_ORDER_DATA;
import static project.util.MappingForAdminConst.CHANGE_USERS_ROLE_PAGE;
import static project.util.MappingForAdminConst.CHOOSE_ORDERS_BY_PERIOD;
import static project.util.MappingForAdminConst.NEW_GAME_DEVELOPER_COMPANY_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_PLATFORMS_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SCREENSHOTS_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SUBGENRE_PAGE;
import static project.util.MappingForAdminConst.ORDER_SEARCH_RESULT;
import static project.util.MappingForAdminConst.SET_NUMBER_TO_STORAGE_PAGE;

@WebFilter(urlPatterns =
        {
                ALL_USERS_PAGE,
                ADD_NEW_GAME_PAGE,
                CHANGE_USERS_ROLE_PAGE,
                NEW_GAME_DEVELOPER_COMPANY_PAGE,
                NEW_GAME_SUBGENRE_PAGE,
                NEW_GAME_PLATFORMS_PAGE,
                NEW_GAME_SCREENSHOTS_PAGE,
                CHOOSE_ORDERS_BY_PERIOD,
                CHANGE_ORDER_DATA,
                ORDER_SEARCH_RESULT,
                SET_NUMBER_TO_STORAGE_PAGE,
                ADD_TO_STORAGE_PAGE,
                ALL_ORDERS_PAGE
        })
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isUserAdmin(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/user");
        }
    }

    public boolean isUserAdmin(ServletRequest servletRequest) {
        UserDataForSession user = (UserDataForSession) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return Objects.nonNull(user) && user.getRole() == Role.ADMIN;
    }
}
