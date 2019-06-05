package id.sapasampah;

public class Balance {
    private String date, time, amount, total;

    public Balance() {};

    public Balance(String date, String time, String amount, String total) {
        this. date = date;
        this.time = time;
        this.amount = amount;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }

    public String getTotal() {
        return total;
    }
}