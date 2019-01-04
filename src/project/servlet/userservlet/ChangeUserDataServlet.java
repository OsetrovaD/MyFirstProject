package project.servlet.userservlet;

import project.dto.userdto.UserDataDto;
import project.dto.userdto.UserDataForSession;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static project.util.CommonRequestParameterConstantUtil.EMAIL_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.FIRST_NAME_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.LAST_NAME_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.PHONE_NUMBER_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.TEXT_AREA_PARAMETER;
import static project.util.ConstantUtil.CHANGE_DATA_PAGE;
import static project.util.ConstantUtil.SUCCESS_PAGE;
import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;
import static project.util.UserChangeDataRequestUtil.CHANGE_ALL;

@WebServlet("/change-data")
public class ChangeUserDataServlet extends HttpServlet {

    private static final String PAGE_NAME = "change-data";
    private static final String CHANGE_PARAMETER = "change";
    private static final String DATA_CHANGES_PARAMETER = "dataChanges";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataForSession user = (UserDataForSession) req.getSession().getAttribute(USER_FOR_SESSION_PARAMETER);
        String change = req.getParameter(CHANGE_PARAMETER);
        boolean result;

        if (CHANGE_ALL.equals(change)) {
            UserDataDto changes = UserDataDto.builder()
                    .firstName(req.getParameter(FIRST_NAME_PARAMETER))
                    .lastName(req.getParameter(LAST_NAME_PARAMETER))
                    .email(req.getParameter(EMAIL_PARAMETER))
                    .phoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER))
                    .address(req.getParameter(TEXT_AREA_PARAMETER))
                    .build();
            result = UserService.getInstance().update(changes, user.getId());
        } else {
            String dataChanges = req.getParameter(DATA_CHANGES_PARAMETER);
            result = UserService.getInstance().updateData(user.getId(), change, dataChanges);
        }

        if (result) {
            resp.sendRedirect(SUCCESS_PAGE);
        } else {
            resp.sendRedirect(CHANGE_DATA_PAGE);
        }
    }
}
