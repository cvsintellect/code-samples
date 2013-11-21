package com.cvsintellect.servlet.constants;

public interface PageConstants {
	// page URL
	public static String ERROR_URL = "/user/page/error";
	public static String SIGNUP_URL = "/user/signup";
	public static String SIGNIN_URL = "/user/signin";
	public static String LOGOUT_URL = "/user/logout";

	public static String PROFILE_URL = "/user/view/profile";

	public static String SETUP_LOGIN_GOOGLE_URL_USER = "/user/setuplogin/google";
	public static String AUTHENTICATE_GOOGLE_URL_USER = "/user/authenticate/google";
	public static String SETUP_LOGIN_FACEBOOK_URL_USER = "/user/setuplogin/facebook";
	public static String AUTHENTICATE_FACEBOOK_URL_USER = "/user/authenticate/facebook";
	public static String SETUP_LOGIN_LINKEDIN_URL_USER = "/user/setuplogin/linkedin";
	public static String AUTHENTICATE_LINKEDIN_URL_USER = "/user/authenticate/linkedin";
	public static String SETUP_LOGIN_YAHOO_URL_USER = "/user/setuplogin/yahoo";
	public static String AUTHENTICATE_YAHOO_URL_USER = "/user/authenticate/yahoo";
	public static String SETUP_LOGIN_MSN_URL_USER = "/user/setuplogin/msn";
	public static String AUTHENTICATE_MSN_URL_USER = "/user/authenticate/msn";

	public static String ADD_POSITION_URL = "/user/add/position";
	public static String EDIT_POSITION_URL = "/user/edit/position";
	public static String MOVE_POSITION_URL = "/user/move/position";
	public static String DELETE_POSITION_URL = "/user/delete/position";

	// JSP file paths
	public static String SIGNUP_PAGE = "/jsp/main/home/sign-up.jsp";
	public static String SIGNIN_PAGE = "/jsp/main/home/sign-in.jsp";

	public static String WELCOME_PAGE = "/jsp/Welcome.jsp";
	public static String PROFILE_PAGE = "/jsp/UserProfile.jsp";
}
