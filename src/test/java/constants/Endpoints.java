package constants;

public class Endpoints {
	
	// Endpoint Authentication
	public static final String AUTHENTICATION = "/api/v1/authentication";
	public static final String PASSWORD_RESET = "/api/v1/password-reset";
	public static final String PASSWORD_RESET_CHECK = "/api/v1/password-reset/check";
	
	//Endpoint Users
	public static final String USERS = "/api/v1/users";
	public static final String USER_ID = USERS + "/" + "{id}";
	public static final String USER_ID_SECTIONS = "/api/v1/users/{id}/sections";
	public static final String CREATE_USER_EMAIL_FIRST_ACCESS = "/api/v1/users/{id}/first-access";
	public static final String CHECK_USER_FIRSTACCESS = "/api/v1/users/first-access";
	public static final String INSERT_FIRSTACCESS_TOKEN = "/api/v1/users/first-access/check";
	public static final String CHANGE_PASSWORD = "/api/v1/users/password";
	public static final String UPDATE_USER = "/api/v1/users/{id}";
	public static final String DELETE_USER = "/api/v1/users/{id}";

	//Endpoint EventSpaceReservation
	public static final String EVENT_RESERVATION = "/api/v1/reservations";
	public static final String EVENT_RESERVATION_ID = "/api/v1/reservations/{id}";

	
	//Endpoint HomeImages
	public static final String HOME_IMAGES = "/api/v1/home-images";
	public static final String HOME_IMAGES_ID = HOME_IMAGES + "/" + "{id}";
	
	//Endpoint Sponsorships
	public static final String SPONSORSHIPS = "/api/v1/sponsorships";
	public static final String SPONSORSHIP_ID = "/api/v1/sponsorships/{id}";
	
	//Endpoint AdminSections
	public static final String ADMIN_SECTIONS = "/api/v1/sections";
	public static final String ADMIN_SECTIONS_ID = "/api/v1/sections/{id}";
	


}
