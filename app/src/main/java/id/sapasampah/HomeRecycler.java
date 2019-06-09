package id.sapasampah;

public class HomeRecycler {
    String date, weight, amount, time;
    Integer homePriority;

    public HomeRecycler() {

    }

    public HomeRecycler(String date, String weight, String amount, String time) {
        this.date = date;
        this.amount = amount;
        this.time = time;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public String getWeight() {
        return weight;
    }

    public String getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

}



