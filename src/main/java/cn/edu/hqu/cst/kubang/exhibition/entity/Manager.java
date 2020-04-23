package cn.edu.hqu.cst.kubang.exhibition.entity;

import org.springframework.stereotype.Component;

@Component
public class Manager {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
