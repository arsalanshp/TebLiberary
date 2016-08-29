package library.tebyan.com.teblibrary.classes;

public class WebserviceUrl {
    public static String SITE_URL = "https://library.tebyan.net/fa/api/";
    public static String GET_COLLECTIONS = SITE_URL+"GetCollections?PageSize=5&";
    public static String GET_METADATA_LIST = SITE_URL+"GetMetadataByCollection?PageSize=5&";
    public static String SEARCH = SITE_URL+"AdvanceSearch_v3?";
    public static String ADD_FAVORITE = SITE_URL+"AddForRead?ID=";
    public static String UN_FAVORITE = SITE_URL+"DeleteForRead?ID=";

    public static String GET_FAVORITE_LIST = SITE_URL+"GetAllForRead";

    //yahyapour
    public static String forgetPass = "http://www.tebyan.net/newindex.aspx?pid=9";
    public static String Get_BOOK_DETAILS = SITE_URL+"GetMetadataDetails";
    public static String INSERT_COMMENT = SITE_URL+"InsertComment?";
    public static String GET_COMMENT = SITE_URL+"GetComments?";
    public static String CHANGE_PASSWORD = "https://www.tebyan.net/WebServices/SocialNetwork_v2/MobileApi.asmx/ChangePasswordMobileApi";

    //Login & Registration
    public static String LoginForMobile = "https://library.tebyan.net/fa/Account/GetToken?username=";
    public static String Login = "http://tebyan.net/WebServices/SocialNetwork_v2/mobileapi.asmx/Login_V2";
    public static String regServicesBaseUrl = "https://tebyan.net/WebServices/Membership/Membership.asmx";
    public static String servicesBaseUrl = "http://tebyan.net/WebServices/SocialNetwork_v2";
    public static String uploadServerUri = "http://tebyan.net/Registeration/SocialNetwork_v2/Common/MultiUpload.aspx?Mode=";

}