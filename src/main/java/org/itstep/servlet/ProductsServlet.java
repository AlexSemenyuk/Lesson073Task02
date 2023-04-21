package org.itstep.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.itstep.data.Product;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProductsServlet extends BaseServlet {
    public static String TEMPLATE;
    private static List<Product> products = new CopyOnWriteArrayList<>();
    private static String path = "\\WEB-INF\\images\\";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 1. Создаем TEMPLATE
        ServletContext servletContext = config.getServletContext();
        try (InputStream in = servletContext.getResourceAsStream("/WEB-INF/template/products.html");
             BufferedReader rdr = new BufferedReader(new InputStreamReader(in))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = rdr.readLine()) != null) {
                stringBuilder.append(line);
            }
            TEMPLATE = stringBuilder.toString();
            System.out.println(TEMPLATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("orderProducts") != null) {
            resp.sendRedirect("order");      // Переход к продуктам в случае удачной sign up
            return;
        }

        // 1. Вывод страницы
        // 1.1 Считывание products с DB
        products = shopDao.findAllProducts();

        // 1.2 Вывод страницы
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");


        PrintWriter writer = resp.getWriter();
        // 1.3 Определение path
        ServletContext servletContext = req.getServletContext();
        URL url = servletContext.getResource(path);    // Получили абсолют. путь с приставкой
        System.out.println("url = " + url);
        String pathWrite = url.toString().substring(6);    // Получили абсолют. путь
        System.out.println("pathWrite = " + pathWrite);

        String productsString = "<ul class='product-con'>" +
                                products.stream().map(p ->
                                                "<li class='product-item'>" + p.toStringForProducts() + "</li>")
                                        .collect(Collectors.joining())
                                + "</ul>";
//        System.out.println("productsString = " + productsString);
        writer.printf(TEMPLATE, productsString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String apricotsString = req.getParameter("apricots");
        String bananasString = req.getParameter("bananas");
        String cucumbersString = req.getParameter("cucumbers");
        String peachesString = req.getParameter("peaches");
        String pineappleString = req.getParameter("pineapple");
        String potatoString = req.getParameter("potato");
        String strawberryString = req.getParameter("strawberry");
        String tomatoesString = req.getParameter("tomatoes");


        Map<String, Float> orderProducts = new HashMap<>();
        session.setAttribute("orderProducts", orderProducts);
        if (apricotsString != null && !apricotsString.isBlank()) {
            orderProducts.put("apricots", Float.parseFloat(apricotsString));
            System.out.println("apricotsString = " + apricotsString);

        }
        if (bananasString != null && !bananasString.isBlank()) {
            orderProducts.put("bananas", Float.parseFloat(apricotsString));
            System.out.println("bananasString = " + bananasString);

        }
        if (cucumbersString != null && !cucumbersString.isBlank()) {
            orderProducts.put("cucumbers", Float.parseFloat(cucumbersString));
            System.out.println("cucumbersString = " + cucumbersString);

        }
        if (peachesString != null && !peachesString.isBlank()) {
            orderProducts.put("peaches", Float.parseFloat(peachesString));
            System.out.println("peachesString = " + peachesString);
        }
        if (pineappleString != null && !pineappleString.isBlank()) {
            orderProducts.put("pineapple", Float.parseFloat(pineappleString));
            System.out.println("pineappleString = " + pineappleString);
        }
        if (potatoString != null && !potatoString.isBlank()) {
            orderProducts.put("potato", Float.parseFloat(potatoString));
            System.out.println("potatoString = " + potatoString);
        }
        if (strawberryString != null && !strawberryString.isBlank()) {
            orderProducts.put("strawberry", Float.parseFloat(strawberryString));
            System.out.println("strawberryString = " + strawberryString);
        }
        if (tomatoesString != null && !tomatoesString.isBlank()) {
            orderProducts.put("tomatoes", Float.parseFloat(tomatoesString));
            System.out.println("tomatoesString = " + tomatoesString);
        }


        if (orderProducts != null) {
            for (String key : orderProducts.keySet()) {
                System.out.println(key + ": " + orderProducts.get(key));
            }
            session.setAttribute("orderProducts", orderProducts);
            resp.sendRedirect("order");
        }

//        resp.sendRedirect("/Lesson073Task02/product");
    }


}
