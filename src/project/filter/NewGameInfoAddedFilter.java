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

import static project.util.SessionParameterConstantUtil.GAME_INFO;

@WebFilter("/developer-company")
public class NewGameInfoAddedFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isInfoAdded(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/add-game");
        }
    }

    private boolean isInfoAdded(ServletRequest servletRequest) {
        return ((HttpServletRequest) servletRequest).getSession().getAttribute(GAME_INFO) != null;
    }
}
