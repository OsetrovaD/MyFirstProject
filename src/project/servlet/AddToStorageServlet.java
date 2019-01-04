package project.servlet;

import project.dto.gamedto.NamePlatformsDto;
import project.dto.storagedto.ChangeStorageNumberDto;
import project.service.GameService;
import project.service.StorageService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.CommonRequestParameterConstantUtil.GAMES_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.NUMBER_PARAMETER;
import static project.util.ConstantUtil.SET_NUMBER_FOR_STORAGE;
import static project.util.ConstantUtil.SUCCESS_PAGE;
import static project.util.SessionParameterConstantUtil.STORAGE_CHANGE_PARAMETER;
import static project.util.StorageUpdateConstantUtil.ADD_TO_STORAGE;

@WebServlet("/add-to-storage")
public class AddToStorageServlet extends HttpServlet {

    private static final String PAGE_NAME = "add-to-storage";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NamePlatformsDto> games = GameService.getInstance().getAllWithPlatforms();
        req.setAttribute(GAMES_PARAMETER, games);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter(NUMBER_PARAMETER);

        if (Short.valueOf(number) > 0) {
            ChangeStorageNumberDto storageChange = (ChangeStorageNumberDto) req.getSession().getAttribute(STORAGE_CHANGE_PARAMETER);
            if (StorageService.getInstance().update(storageChange, Short.valueOf(number), ADD_TO_STORAGE)) {
                req.getSession().removeAttribute(STORAGE_CHANGE_PARAMETER);
                resp.sendRedirect(SUCCESS_PAGE);
            } else {
                resp.sendRedirect(SET_NUMBER_FOR_STORAGE);
            }
        } else {
            resp.sendRedirect(SET_NUMBER_FOR_STORAGE);
        }
    }
}
