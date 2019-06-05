package id.sapasampah;

public class HomeRecycler {
    String homeDate, homeWeight, homeAmount, homeTime;
    Integer homePriority;

    public HomeRecycler() {

    }

    public HomeRecycler(String date, String weight, String amount, String time) {
        this.homeDate = date;
        this.homeAmount = amount;
        this.homeTime = time;
        this.homeWeight = weight;
    }

    public String getHomeDate() {
        return homeDate;
    }

    public String getHomeWeight() {
        return homeWeight;
    }

    public String getHomeAmount() {
        return homeAmount;
    }

    public String getHomeTime() {
        return homeTime;
    }

}



