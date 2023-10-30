package app.entities;

public class User {
    private int id;
    private String name;
    private String password;
    private String role;
    private double balance;

    public User(int id, String name, String password, String role, double balance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    public User(String name){
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole() {
        return role;
    }

    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double newBalance){
        balance = newBalance;
    }

    public void subtractFromBalance(double valueToRemove){
        balance -= valueToRemove;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='"+ role +"\''"+
                '}';
    }
}
