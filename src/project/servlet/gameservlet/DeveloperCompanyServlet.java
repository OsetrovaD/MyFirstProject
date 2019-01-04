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

import static project.util.CommonRequestParameterConstantUtil.NAME_PARAMETER;
import static project.util.MappingForAdminConst.NEW_GAME_DEVELOPER_COMPANY_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SUBGENRE_PAGE;
import static project.util.SessionParameterConstantUtil.DEVELOPER_COMPANY;

@WebServlet("/developer-company")
public class DeveloperCompanyServlet extends HttpServlet {

    private static final String DEFAULT = "default";
    private static final String COMPANIES_PARAMETER = "companies";
    private static final String PAGE_NAME = "company";
    private static final String DEV_COMPANY_ID_PARAMETER = "dev_company";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DeveloperCompany> companies = DeveloperCompanyService.getInstance().getAll();
        req.setAttribute(COMPANIES_PARAMETER, companies);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {;
        DeveloperCompany company = null;
        boolean result;

        if (areFieldsEmpty(req) || areBothFieldsFilled(req)) {
            result = false;
        } else if (StringUtil.isNotEmpty(req.getParameter(NAME_PARAMETER))) {
            company = DeveloperCompany.builder()
                    .name(req.getParameter(NAME_PARAMETER))
                    .build();
            result = true;
        } else {
            company = DeveloperCompany.builder()
                    .id(Integer.valueOf(req.getParameter(DEV_COMPANY_ID_PARAMETER)))
                    .build();
            result = true;
        }

        if (result) {
            if (req.getSession().getAttribute(DEVELOPER_COMPANY) != null) {
                req.getSession().removeAttribute(DEVELOPER_COMPANY);
            }
            req.getSession().setAttribute(DEVELOPER_COMPANY, company);
            resp.sendRedirect(NEW_GAME_SUBGENRE_PAGE);
        } else {
            resp.sendRedirect(NEW_GAME_DEVELOPER_COMPANY_PAGE);
        }
    }

    private boolean areFieldsEmpty(HttpServletRequest req) {
        return DEFAULT.equals(req.getParameter(DEV_COMPANY_ID_PARAMETER)) && StringUtil.isEmpty(req.getParameter(NAME_PARAMETER));
    }

    private boolean areBothFieldsFilled(HttpServletRequest req) {
        return !DEFAULT.equals(req.getParameter(DEV_COMPANY_ID_PARAMETER)) && StringUtil.isNotEmpty(req.getParameter(NAME_PARAMETER));
    }
}
