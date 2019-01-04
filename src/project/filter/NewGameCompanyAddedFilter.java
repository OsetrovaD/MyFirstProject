package project.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.SessionParameterConstantUtil.DEVELOPER_COMPANY;

@WebFilter("/new-game-subgenre")
public class NewGameCompanyAddedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isInfoAdded(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/developer-company");
        }
    }

    private boolean isInfoAdded(ServletRequest servletRequest) {
        return ((HttpServletRequest) servletRequest).getSession().getAttribute(DEVELOPER_COMPANY) != null;
    }
}
