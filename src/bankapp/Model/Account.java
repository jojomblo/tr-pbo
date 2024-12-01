package bankapp.Model;

public class Account {
    private int id;
    private int userId;
    private String nomorRekening;
    private double saldo;

    public Account(int id, int userId, String nomorRekening, double saldo) {
        this.id = id;
        this.userId = userId;
        this.nomorRekening = nomorRekening;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
