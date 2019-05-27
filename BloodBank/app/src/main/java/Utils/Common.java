package Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.User;

public class Common {


    public static final String USER_INFORMATION = "UserInformation";
    public static final String USER_UID_SAVE_KEY = "SaveUid";
    public static final String TOKENS ="Tokens" ;
    public static final String LOCATION ="Locations" ;
    public static final String PUBLIC_LOCATION = "PublicLocation";
    public static User loggedUser;
    public static User trackingUser;

    public static Date convertTimeStampToData(long time)
    {
        return new Date(new Timestamp(time).getTime());

    }

    public static String getDateFormatted(Date date) {

        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date).toString();
    }
}
