public class Time {

    private int min;
    private int hour;

    Time(int hour, int min) throws WrongTimeException {
            if ((hour >= 0 && hour < 24) && (min >= 0 && min < 60)) {
                this.hour = hour;
                this.min = min;
            }else throw new WrongTimeException("Невірний формат часу!");
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public static Time timeSumm(Time t1, Time t2) throws WrongTimeException {
        int hour = t1.getHour() + t2.getHour();
        int min = t1.getMin() + t2.getMin();
        if (min > 59) {
            hour++;
            min -= 60;
        }
        if (hour > 23) hour -= 24;
        return new Time(hour, min);
    }

    public static int toInt(Time time) {
        return time.getHour() * 60 + time.getMin();
    }


    public static Time toTime(int intTime) throws WrongTimeException {
        return new Time(intTime / 60, intTime % 60);
    }

    @Override
    public String toString() {
        String min = "0";
        if (this.getMin()<10) min+=this.getMin();
        else min = String.valueOf(this.getMin());
        return this.getHour() + ":" + min;
    }
}
