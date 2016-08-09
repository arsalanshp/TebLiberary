package library.tebyan.com.teblibrary.classes.Login;

public class LoginDB {

    public static final String TABLE = "LoginDB";
    public static final String USERNAME_FIELD = "Username";
    public static final String PASSWORD_FIELD = "Password";
    public static final String NETWORKUSERID_FIELD = "NetworkUserId";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE + " ("
            + USERNAME_FIELD + " text primary key," + PASSWORD_FIELD
            + " text not null," + NETWORKUSERID_FIELD + " integer null);";
    public static final String TABLE_CLEAR = "DELETE FROM " + TABLE;
    public static final String TABLE_SELECT = "SELECT " + USERNAME_FIELD + ","
            + PASSWORD_FIELD + "," + NETWORKUSERID_FIELD + " FROM " + TABLE;
}
