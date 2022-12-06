package Server;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String USERS_ID = "userID";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";

    public static final String ADMIN_TABLE = "admin";
    public static final String ADMIN_ID = "adminID";
    public static final String ADMIN_STATUS = "status";

    public static final String CLIENT_TABLE = "client";
    public static final String CLIENT_ID = "clientID";
    public static final String CLIENT_NAME = "name";
    public static final String CLIENT_SEX = "sex";
    public static final String CLIENT_BIRTHDATE = "birthDate";

    public static final String TYPESERVICE_TABLE = "typeofservice";
    public static final String TS_ID = "typeServiceID";
    public static final String TS_NAME = "name";

    public static final String SERVICE_TABLE = "services";
    public static final String SERVICE_ID = "serviceID";
    public static final String SERVICE_TYPEID = "typeOfserviceID";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_PRICE = "price";

    public static final String MASTER_TABLE = "masters";
    public static final String MASTER_ID = "masterID";
    public static final String MASTER_TYPEID = "serviceTypeID";
    public static final String MASTER_NAME = "name";
    public static final String MASTER_EXPERIENCE = "experience";

    public static final String APPOINTMENT_TABLE = "appointments";
    public static final String APPOINTMENT_CLIENT = "clientID";
    public static final String APPOINTMENT_APPOINTMENTS = "appointments";
    public static final String APPOINTMENT_MASTER = "masterID";
    public static final String APPOINTMENT_DATE = "date";
    public static final String APPOINTMENT_TIME = "time";
    public static final String APPOINTMENT_PRICE = "price";
    public static final String APPOINTMENT_ID = "ID";

    public static final String A_TABLE = "agesales";
    public static final String A_IDUD = "idud";
    public static final String A_SALE = "sale";

    public static final String TICKET_TABLE = "ticket";
    public static final String TICKET_IDUS = "idus";
    public static final String TICKET_FULLPRICE = "generalPrice";
}