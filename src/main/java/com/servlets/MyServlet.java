package com.servlets;

import com.beans.HazelCastBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: bnayar
 * Date: 11/22/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyServlet extends HttpServlet {

    private static ApplicationContext appCtx = null;

    HazelCastBean hazelCastBean;

    public HazelCastBean getHazelCastBean() {
        return hazelCastBean;
    }

    public void setHazelCastBean(HazelCastBean hazelCastBean) {
        this.hazelCastBean = hazelCastBean;
    }

    @Override
    public void init() throws ServletException {
        appCtx = new ClassPathXmlApplicationContext(new String[]{
                "hazelcastBeanSpringContext.xml"
        });
        hazelCastBean = (HazelCastBean) appCtx.getBean("hazelCastBean");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = StringUtils.upperCase(req.getParameter("Action"));
        if (StringUtils.equalsIgnoreCase("PUBLISH", action)) {
            String topicName = req.getParameter("topicName");
            String message = req.getParameter("message");
            getHazelCastBean().publishMessage(topicName, message);
            generateOutPut("Published.....", resp);
        } else if (StringUtils.equalsIgnoreCase("ADD", action)) {
            String mapName = req.getParameter("mapName");
            String mapKey = req.getParameter("mapKey");
            String mapValue = req.getParameter("mapValue");
            getHazelCastBean().addToMap(mapName, mapKey, mapValue);
            generateOutPut("Added", resp);
        } else if (StringUtils.equalsIgnoreCase("GET", action)) {
            String mapName = req.getParameter("mapName");
            String mapKey = req.getParameter("mapKey");
            String message = getHazelCastBean().getMap(mapName, mapKey);
            generateOutPut(message, resp);
        }
    }

    private void generateOutPut(String message, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>");
            out.println(message);
            out.println("</h1>");
            out.println("</body>");
            out.println("</html>");
            response.setContentType("text/html");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
