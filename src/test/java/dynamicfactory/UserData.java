package dynamicfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.github.javafaker.Faker;

import model.User;

public class UserData {
	public static Faker faker = new Faker(new Locale("pt", "BR"));
	public static User alteracao = new User();
	public static Integer quantidade = 1;
	
	//Escolhe 1 Admin Section aleatório
	public static List<Integer> randomAdminSections() {
		List<Integer> randomAdminSectionIds = new ArrayList<>();{
			for(int i=0; i < quantidade; i++) {
				Integer Sections[] = {1, 23, 24, 25, 26};
				int number = faker.random().nextInt(Sections.length);
				randomAdminSectionIds.add(Sections[number]);
			}
		}
		return randomAdminSectionIds;
	}
	
	//Escolhe um Subjetcs aleatório
	public static List<Integer> randomSubjects(){
		List<Integer> randomSubjectsIds = new ArrayList<>(); {
			for(int i=0; i < quantidade; i++) {
				Integer id = faker.random().nextInt(1, 10);
				randomSubjectsIds.add(id);
			}
		}
		return randomSubjectsIds;
	}
	
	public static List<Integer> Master(){
		List<Integer> Master = new ArrayList<>();{
			Master.add(1);
		}
		return Master;
	}
	
	public static List<Integer> Marketing(){
		List<Integer> Marketing = new ArrayList<>();{
			Marketing.add(23);
		}
		return Marketing;
	}
	
	public static List<Integer> Eventos(){
		List<Integer> Eventos = new ArrayList<>();{
			Eventos.add(24);
		}
		return Eventos;
	}
	
	public static List<Integer> Ouvidoria(){
		List<Integer> Ouvidoria = new ArrayList<>();{
			Ouvidoria.add(25);
		}
		return Ouvidoria;
	}
	
	public static List<Integer> Esportiva(){
		List<Integer> Esportiva = new ArrayList<>();{
			Esportiva.add(26);
		}
		return Esportiva;
	}
	
	

}
