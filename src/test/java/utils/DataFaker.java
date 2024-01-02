package utils;

import java.util.List;
import java.util.Locale;
import com.github.javafaker.Faker;
import static utils.FakerFunctions.*;

public class DataFaker {
	public static Faker faker = new Faker(new Locale("pt", "BR"));
	
	//Valid User Credentials
	public static String fakerName = faker.name().firstName() + " " + faker.name().lastName();
	public static String fakerEmail = faker.internet().safeEmailAddress();
	public static List<Integer> fakerAdminSectionIds = randomAdminSections();
	public static List<Integer> fakerSubjectsIds = randomSubjects();
	
	//Invalid User Credentials
	
	

	//Valid Image Credentials
	public static final String linkFaker = "www." + faker.internet().domainName();
	public static final String typeFaker = randomType();

}
