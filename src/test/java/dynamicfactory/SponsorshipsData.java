package dynamicfactory;

import java.util.Locale;

import com.github.javafaker.Faker;

public class SponsorshipsData {
	public static Faker faker = new Faker(new Locale("pt", "BR"));
	
	//Escolhe tipos de banners aleatórios
		public static String randomType() {    	
	        String Types[] = {"events", "sport", "home", "venue", "fraternity"};
	    	int number = faker.random().nextInt(Types.length);
	    	String randomType = Types[number];
	    	
	            return randomType;
	            }
		}
