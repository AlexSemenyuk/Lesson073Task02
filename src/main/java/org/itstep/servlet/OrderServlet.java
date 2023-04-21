package org.itstep.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.itstep.data.Product;
import org.itstep.data.User;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class OrderServlet extends BaseServlet {
    public static String TEMPLATE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 1. Создаем TEMPLATE
        ServletContext servletContext = config.getServletContext();
        try (InputStream in = servletContext.getResourceAsStream("/WEB-INF/template/order.html");
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
        // 1. Вывод страницы
        // 1.1 Получение данных из DB
        List<Product> products = shopDao.findAllProducts();
        if (products != null) {
            System.out.println("Order products");
            for (Product product : products) {
                System.out.println(product.toString());
            }
        }
        // 1.2 Сформированный заказ

        Map<String, Float> orderProducts = (Map<String, Float>) session.getAttribute("orderProducts");
        if (orderProducts != null) {
            System.out.println("Order orderProducts");
            for (String key : orderProducts.keySet()) {
                System.out.println(key + ": " + orderProducts.get(key));
            }
        }

        if (orderProducts != null && products != null) {
            // 1.3 Формирование строки из всех данных таблицы (продукт, кол-во, цена, стоимость)
            StringBuilder line = new StringBuilder("");
            float totalCostOfProducts = 0;
            float priceTMP = 0;
            String lineTMP = "";
            float totalCostTMP = 0;
            for (String key : orderProducts.keySet()) {
                for (Product product : products) {
                    if (key.equals(product.getName())) {
                        priceTMP = product.getPrice();
                        break;
                    }
                }
                totalCostTMP = priceTMP * orderProducts.get(key);

                lineTMP = "<tr>" +
                          "<td class='mean1'>" + key + "</td>" +
                          "<td class='mean1'>" + orderProducts.get(key) + "</td>" +
                          "<td class='mean'>" + priceTMP + "</td>" +
                          "<td class='mean'>" + ((float) (Math.round(totalCostTMP * 10))) / 10 + "</td>" +
                          "</tr>";
                System.out.println("lineTMP = " + lineTMP);
                line.append(lineTMP);
                totalCostOfProducts += totalCostTMP;
            }
            // 1.3 Вывод страницы
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            PrintWriter writer = resp.getWriter();
//            writer.println(TEMPLATE);
            writer.printf(TEMPLATE, line.toString(), ((float) (Math.round(totalCostOfProducts * 10))) / 10 + "");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Можно добавить изменения в DB (уменьшение по кол-ву) и формирование отдельной таблицы order в DB
        resp.sendRedirect("signup");
    }

}
