package study.springboot.security.token.support.utils;

import lombok.extern.slf4j.Slf4j;
import study.springboot.security.token.support.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;

@Slf4j
public class WebUtils {

    /**
     *
     */
    public static String getBodyText(HttpServletRequest request) {
        InputStream is = getBodyStream(request);
        return null;
    }

    /**
     *
     */
    public static InputStream getBodyStream(HttpServletRequest request) {
        InputStream is = null;
        try {
            is = request.getInputStream();
        } catch (Exception ex) {
            log.error("", ex);
        }
        return is;
    }

    /**
     * 发送状态码
     */
    public static void sendError(HttpServletResponse response, int statusCode) {
        sendError(response, statusCode, null);
    }

    public static void sendError(HttpServletResponse response, int statusCode, String msg) {
        try {
            response.sendError(statusCode, msg);
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    /**
     * 输出
     */
    public static void write(HttpServletResponse response, Result result) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json; charset=UTF-8");
            writer = response.getWriter();
            String text = JsonUtils.toJson(result);
            writer.write(text);
        } catch (Exception ex) {
            log.error("", ex);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
