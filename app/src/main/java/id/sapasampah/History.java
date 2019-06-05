package id.sapasampah;

public class History {
    private String date, time, amount;

    public History() {
        //Empty constructor
    }

    public History(String date, String time, String amount){
        this.date = date;
        this.time = time;
        this.amount = amount;
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
}
