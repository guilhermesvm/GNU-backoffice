package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.github.javafaker.Faker;

public class DataFaker {
	public static Faker faker = new Faker(new Locale("pt", "BR"));
	public static String nameFaker = faker.name().firstName() + " " + faker.name().lastName();
	public static String emailFaker = faker.internet().safeEmailAddress();
	public static String linkFaker = "www." + faker.internet().domainName() + ".com";
	
	public static List<Integer> randomAdminSections() {
		List<Integer> randomAdminSectionIds = new ArrayList<>();{
			for(int i=0; i<2; i++) {
				int id = faker.random().nextInt(1, 6);
				randomAdminSectionIds.add(id);
			}
		}
		return randomAdminSectionIds;
	}
	
	public static List<Integer> randomSubjects(){
		List<Integer> randomSubjectsIds = new ArrayList<>(); {
			for(int i=0; i < 2; i++) {
				Integer id = faker.random().nextInt(1, 10);
				randomSubjectsIds.add(id);
			}
		}
		return randomSubjectsIds;
	}
	
	public static String randomType() {    	
        String Types[] = {"events", "sport", "home", "venue", "fraternity"};
    	int number = faker.random().nextInt(Types.length);
    	String randomType = Types[number];

 
            System.out.println("String Aleatória: " + randomType);
            return randomType;
        }
}
