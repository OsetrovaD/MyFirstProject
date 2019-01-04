package project.servlet.userservlet;

import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

import static project.util.InterestingFactsConstantUtil.FACTS;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private static final Random RANDOM  = new Random();
    private static final String INTERESTING_FACT_PARAMETER = "interestingFact";
    private static final String PAGE_NAME = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int index = RANDOM.nextInt(FACTS.size());
        req.setAttribute(INTERESTING_FACT_PARAMETER, FACTS.get(index));
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
