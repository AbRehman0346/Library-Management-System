package General;

public class DoubleClickDetector {
    private long lastClickTime = 0;
    private int delayBetweenClicks = 400; // default 250ms
    
   
    public void setDelayBetweenClicks(int delayInMilliseconds) {
        this.delayBetweenClicks = delayInMilliseconds;
    }
    
   
    public boolean detectDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isDoubleClick = (currentTime - lastClickTime) < delayBetweenClicks;
        lastClickTime = currentTime;
        return isDoubleClick;
    }
}
