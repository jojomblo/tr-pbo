package bankapp.Model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private int userId;
    private String nomorRekening;
    private BigDecimal saldo;

    public Account() {}

    public Account(int id, int userId, String nomorRekening, BigDecimal saldo) {
        this.id = id;
        this.userId = userId;
        this.nomorRekening = nomorRekening;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public void setNomorRekening(String nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
