package constants;

import static dynamicfactory.UserData.*;
import static dynamicfactory.SponsorshipsData.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import com.github.javafaker.Faker;


public class DataFaker {
	public static Faker faker = new Faker(new Locale("pt", "BR"));
	
	//Valid User Credentials
	public static String fakerName = faker.name().firstName() + " " + faker.name().lastName();
	public static String fakerEmail = faker.internet().safeEmailAddress();
	public static boolean fakerActive = faker.random().nextBoolean();
	public static List<Integer> fakerAdminSectionIds = randomAdminSections();
	public static List<Integer> fakerSubjectsIds = randomSubjects();
	public static Integer fakerId = faker.random().nextInt(1, 200);
	
	//Valid Image Credentials
	public static String linkFaker = "www." + faker.internet().domainName();
	public static String typeFaker = randomType();
	
	//Invalid User Name
	public static final String InvalidFakerNameEmoji = faker.name().firstName() + "😎" + faker.name().lastName();
	public static final String InvalidFakerNameSpecial = "!"+ faker.name().firstName() + "Ж" + faker.name().lastName();
	public static final String InvalidFakerNameNumber = 1 + faker.name().firstName() + " "+ faker.name().lastName();	
	
	//AdminSection 
	public static String fakerAdminSection = faker.lorem().word();
	public static List<String> fakerAdminRole = new ArrayList<>(Arrays.asList(fakerAdminSection));
	

}
