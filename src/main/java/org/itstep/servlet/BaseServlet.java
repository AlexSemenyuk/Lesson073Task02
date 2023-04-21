package org.itstep.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.itstep.dao.ShopDao;
import org.itstep.dao.impl.ShopDaoImpl;


public abstract class BaseServlet extends HttpServlet {
    protected ShopDao shopDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        String url = config.getServletContext().getInitParameter("url");
        System.out.println("url = " + url);
        String username = config.getServletContext().getInitParameter("username");
        System.out.println("username = " + username);
//        String password = config.getServletContext().getInitParameter("password");
        String password = "";
//        shopDao = new ShopDaoImpl(url, username, password);
        shopDao = new ShopDaoImpl("jdbc:mariadb://localhost/virtualshop", "root", "");
    }

}
