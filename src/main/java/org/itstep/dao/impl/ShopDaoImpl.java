package org.itstep.dao.impl;


import org.itstep.DbUtils;
import org.itstep.dao.ShopDao;
import org.itstep.data.Product;
import org.itstep.data.User;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShopDaoImpl implements ShopDao {
    private final DbUtils dbUtils;
    private final static String INSERT_USER = "INSERT INTO users(login, password, amount_money)" +
                                              " VALUES (?, ?, ?);";
    private final static String SELECT_PRODUCTS = "SELECT * FROM products";
    private final static String SELECT_USERS = "SELECT * FROM users";
    private final static String Update = "UPDATE tasks SET condition_id = %s WHERE id = %s;";
    private final static String Delete = "DELETE FROM tasks WHERE id = %s;";
    private final static String SelectById = "SELECT * FROM tasks WHERE id = %s;";
    private final static String SelectBySort = "SELECT * FROM tasks ORDER BY %s;";

    public ShopDaoImpl(String url, String username, String password) {
        dbUtils = DbUtils.getInstance();
        dbUtils.init(url, username, password);
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = new CopyOnWriteArrayList<>();
        try {
            Optional<Connection> optionalConnection = dbUtils.getConnection();
            optionalConnection.ifPresent(connection -> {
                try {
                    var stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(SELECT_PRODUCTS);
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        float price = rs.getFloat("price");
                        float stockQuantity = rs.getFloat("stock_quantity");
                        products.add(new Product(id, name, price, stockQuantity));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new CopyOnWriteArrayList<>();
        try {
            Optional<Connection> optionalConnection = dbUtils.getConnection();
            optionalConnection.ifPresent(connection -> {
                try {
                    var stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(SELECT_USERS);
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String login = rs.getString("login");
                        String password = rs.getString("password");
                        float amountMoney = rs.getFloat("amount_money");
                        users.add(new User(id, login, password, amountMoney));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) {
        List<User> users = findAllUsers();
        System.out.println("users = " + users);
        User user = null;
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                user = u;
            }
        }
        return user;

    }

    @Override
    public void saveUserToDB(String login, String password) {
        try {
            Optional<Connection> optionalConnection = dbUtils.getConnection();
            optionalConnection.ifPresent(connection -> {
                try {
                    var stmt = connection.prepareStatement(INSERT_USER);
                    stmt.setString(1, login);
                    stmt.setString(2, password);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


//
//    @Override
//    public void saveTaskToDB(String name, String description, String cat, String date, String prior) throws ParseException {
//        Category category;
//        int categoryId;
//        switch (cat) {
//            case "House" -> categoryId = 1;
//            case "Work" -> categoryId = 2;
//            case "Fitness" -> categoryId = 3;
//            case "Shopping" -> categoryId = 4;
//            default -> categoryId = 1;
//        }
//
//        java.sql.Date deadline = Date.valueOf(date);
//
//        Priority priority;
//        int priorityId;
//        switch (prior) {
//            case "High" -> priorityId = 1;
//            case "Normal" -> priorityId = 2;
//            case "Low" -> priorityId = 3;
//            default -> priorityId = 2;
//        }
//
//        try {
//            Optional<Connection> optionalConnection = dbUtils.getConnection();
//            optionalConnection.ifPresent(connection -> {
//                try {
////                    var stmt = connection.createStatement();
////                    String insertQuery = INSERT.formatted(data.getLogin(), data.getPassword());
////                    System.out.println("Insert Query: " + insertQuery);
////                    stmt.executeQuery(insertQuery);
//                    var stmt = connection.prepareStatement(INSERT);
//                    stmt.setString(1, name);
//                    stmt.setString(2, description);
//                    stmt.setInt(3, categoryId);
//                    stmt.setDate(4, deadline);
//                    stmt.setInt(5, priorityId);
//                    stmt.setInt(6, Condition.ACTIVE.num());
//                    stmt.executeUpdate();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
////        return null;
//    }
//
//
//    @Override
//    public void changeCondition(String condition) {
//        String[] arr = condition.split(":");
//        int id = Integer.parseInt(arr[0]);
//        System.out.println("id = " + id);
//        String tmpCondition = arr[1];
//        System.out.println("tmpCondition = " + tmpCondition);
//        String updateTotal;
//        int conditionId;
//        switch (tmpCondition) {
//            case "Active" -> conditionId = 2;
//            case "Done" -> conditionId = 1;
//            default -> conditionId = 2;
//        }
//        updateTotal = Update.formatted(conditionId, id);
//        try {
//            Optional<Connection> optionalConnection = dbUtils.getConnection();
//            optionalConnection.ifPresent(connection -> {
//                try {
//                    Statement stmt = connection.createStatement();
//                    stmt.executeUpdate(updateTotal);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void deleteTask(Integer id) {
//        String deleteTotal = Delete.formatted(id);
//        try {
//            Optional<Connection> optionalConnection = dbUtils.getConnection();
//            optionalConnection.ifPresent(connection -> {
//                try {
//                    Statement stmt = connection.createStatement();
//                    stmt.executeUpdate(deleteTotal);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public List<Task> findAllBySort(String sort) {
//        List<Task> tasks = new ArrayList<>();
//        System.out.println("sort = " + sort);
//        // Определение метода сортировки
//        String columnBySort;
//        switch (sort) {
//            case "Category" -> columnBySort = "category_id";
//            case "Priority" -> columnBySort = "priority_id";
//            case "Deadline" -> columnBySort = "deadline";
//            default -> columnBySort = "id";
//        }
//        String selectTotal = SelectBySort.formatted(columnBySort);
//        System.out.println("selectTotal = " + selectTotal);
//        try {
//            Optional<Connection> optionalConnection = dbUtils.getConnection();
//            optionalConnection.ifPresent(connection -> {
//                try {
//                    Statement stmt = connection.createStatement();
//                    ResultSet resultSet = stmt.executeQuery(selectTotal);
//
//                    formTasks(tasks, resultSet);
//                } catch (SQLException | ParseException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return tasks;
//    }
//
//    private static void formTasks(List<Task> tasks, ResultSet resultSet) throws SQLException, ParseException {
//        Category category;
//        Priority priority;
//        Condition condition;
//        while (resultSet.next()) {
//            switch (resultSet.getInt("category_id")) {
//                case 1 -> category = Category.HOUSE;
//                case 2 -> category = Category.WORK;
//                case 3 -> category = Category.FITNESS;
//                case 4 -> category = Category.SHOPPING;
//                default -> category = Category.HOUSE;
//            }
//            switch (resultSet.getInt("priority_id")) {
//                case 1 -> priority = Priority.HIGH;
//                case 2 -> priority = Priority.NORMAL;
//                case 3 -> priority = Priority.LOW;
//                default -> priority = Priority.HIGH;
//            }
//            switch (resultSet.getInt("condition_id")) {
//                case 1 -> condition = Condition.ACTIVE;
//                case 2 -> condition = Condition.DONE;
//                default -> condition = Condition.ACTIVE;
//            }
//
//            tasks.add(new Task(
//                    resultSet.getInt("id"),
//                    resultSet.getString("name"),
//                    resultSet.getString("description"),
//                    category,
//                    resultSet.getDate("deadline"),
//                    priority,
//                    condition)
//            );
//        }
//    }

}
