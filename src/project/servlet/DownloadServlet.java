package project.servlet;

import project.service.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    private static final String PLATFORM_PRICE = "\tПлатформа: %s; Цена: %s";
    private static final String CONTENT_TYPE = "application/octet-stream";
    private static final String HEADER_CONTENT = "Content-Disposition";
    private static final String FILENAME = "attachment; filename=\"pricelist.txt\"";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setHeader(HEADER_CONTENT, FILENAME);

        String gamesWithPrices = GameService.getInstance().getWithPrices().stream()
                .map(gameWithPrice -> String.valueOf(gameWithPrice.getName()
                                                    + System.lineSeparator()
                                                    + gameWithPrice.getPrices().entrySet().stream()
                                                            .map(entry -> String.format(PLATFORM_PRICE, entry.getKey().getName(), entry.getValue()))
                                                            .collect(Collectors.joining(System.lineSeparator()))))
                .collect(Collectors.joining(System.lineSeparator()));
        resp.getWriter().write(gamesWithPrices);
    }
}
