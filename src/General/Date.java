/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package General;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author Abdul Rehman
 */
public class Date {
    public Calendar addDaysToCurrentDate(int days){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.DATE, days);
        return cal;
    }
}
