package project.servlet.gameservlet;

import project.entity.DeveloperCompany;
import project.service.DeveloperCompanyService;
import project.util.JspPathUtil;
import project.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.MappingForAdminConst.NEW_GAME_DEVELOPER_COMPANY_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SUBGENRE_PAGE;

@WebServlet("/developer-company")
public class DeveloperCompanyServlet extends HttpServlet {

    private static final String DEFAULT = "default";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DeveloperCompany> companies = DeveloperCompanyService.getInstance().getAll();
        req.setAttribute("companies", companies);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("company")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {;
        DeveloperCompany company = null;
        boolean result;

        if (areFieldsEmpty(req) || areBothFieldsFilled(req)) {
            result = false;
        } else if (StringUtil.isNotEmpty(req.getParameter("name"))) {
            company = DeveloperCompany.builder()
                    .id(null)
                    .name(req.getParameter("name"))
                    .build();
            result = true;
        } else {
            company = DeveloperCompany.builder()
                    .id(Integer.valueOf(req.getParameter("dev_company")))
                    .name(null)
                    .build();
            result = true;
        }

        if (result) {
            req.getSession().setAttribute("developerCompany", company);
            resp.sendRedirect(NEW_GAME_SUBGENRE_PAGE);
        } else {
            resp.sendRedirect(NEW_GAME_DEVELOPER_COMPANY_PAGE);
        }
    }

    private boolean areFieldsEmpty(HttpServletRequest req) {
        return DEFAULT.equals(req.getParameter("dev_company")) && StringUtil.isEmpty(req.getParameter("name"));
    }

    private boolean areBothFieldsFilled(HttpServletRequest req) {
        return !DEFAULT.equals(req.getParameter("dev_company")) && StringUtil.isNotEmpty(req.getParameter("name"));
    }
}
