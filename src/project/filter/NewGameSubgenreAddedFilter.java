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

import static project.util.SessionParameterConstantUtil.SUBGENRES;

@WebFilter("/new-game-platforms")
public class NewGameSubgenreAddedFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isInfoAdded(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/new-game-subgenre");
        }
    }

    private boolean isInfoAdded(ServletRequest servletRequest) {
        return ((HttpServletRequest) servletRequest).getSession().getAttribute(SUBGENRES) != null;
    }
}
