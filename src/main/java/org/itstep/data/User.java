package org.itstep.data;

public class User {
    private int id;
    private String login;
    private String password;
    private float amountMoney;

    public User(int id, String login, String password, float amountMoney) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.amountMoney = amountMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(float amountMoney) {
        this.amountMoney = amountMoney;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", amountMoney=" + amountMoney +
               '}';
    }
}
