package project.servlet;

import project.dto.storagedto.ChangeStorageNumberDto;
import project.entity.enumonly.GamePlatform;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.SessionParameterConstantUtil.STORAGE_CHANGE_PARAMETER;

@WebServlet("/set-number")
public class SetNumberToStorageServlet extends HttpServlet {

    private static final String GAME_ID_PARAMETER = "game_id";
    private static final String PLATFORM_PARAMETER = "platform";
    private static final String PAGE_NAME = "set-number";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangeStorageNumberDto storageChange = ChangeStorageNumberDto.builder()
                .gameId(Long.valueOf(req.getParameter(GAME_ID_PARAMETER)))
                .platform(GamePlatform.valueOf(req.getParameter(PLATFORM_PARAMETER)))
                .build();
        req.getSession().setAttribute(STORAGE_CHANGE_PARAMETER, storageChange);

        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
