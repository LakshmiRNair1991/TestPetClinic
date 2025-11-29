package spring.testPetClinic.dataProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataProviders {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Random random = new Random();

	private String getRandomLastName() {
		try {
			File file = Paths.get("src", "test", "resources", "TestData", "LastName.json").toFile();
			Map<String, List<String>> lastNameMap = mapper.readValue(file,
					new TypeReference<Map<String, List<String>>>() {
					});
			List<String> lastNames = lastNameMap.getOrDefault("lastNames", Collections.emptyList());
			if (lastNames.isEmpty()) {
				throw new RuntimeException("No last names found in LastName.json");
			}
			return lastNames.get(random.nextInt(lastNames.size()));
		} catch (Exception e) {

			throw new RuntimeException("Error reading LastName.json", e);
		}
	}
	
	private Map<String, String> getRandomPetRecord() {
		try {
			File file = Paths.get("src", "test", "resources", "TestData", "Pet.json").toFile();
			Map<String, List<Map<String, String>>> petMap = mapper.readValue(file,
					new TypeReference<Map<String, List<Map<String, String>>>>() {
					});
			List<Map<String, String>> pets = petMap.getOrDefault("pets", Collections.emptyList());
			if (pets.isEmpty()) {
				throw new RuntimeException("No pets found in Pet.json");
			}

			Map<String, String> pet = pets.get(random.nextInt(pets.size()));

			// Ensure required keys exist exactly: name, birthDate, type
			for (String key : Arrays.asList("name", "birthDate", "type")) {
				if (!pet.containsKey(key)) {
					throw new RuntimeException("Pet record missing key: " + key + " -> " + pet);
				}
			}
			return pet; // e.g., {name=Milo, birthDate=2012-03-15, type=dog}

		} catch (Exception e) {
			throw new RuntimeException("Error reading Pet.json", e);
		}
	}
	
	private Map<String, String> getRandomPetVisitRecord() {
		try {
			File file = Paths.get("src", "test", "resources", "TestData", "VisitDetails.json").toFile();
			Map<String, List<Map<String, String>>> visitMap = mapper.readValue(file,
					new TypeReference<Map<String, List<Map<String, String>>>>() {
					});
			List<Map<String, String>> visits = visitMap.getOrDefault("visits", Collections.emptyList());
			if (visits.isEmpty()) {
				throw new RuntimeException("No visits found in VisitDetails.json");
			}

			Map<String, String> visit = visits.get(random.nextInt(visits.size()));

			// Ensure required keys exist exactly: name, birthDate, type
			for (String key : Arrays.asList("date", "description")) {
				if (!visit.containsKey(key)) {
					throw new RuntimeException("Visit record missing key: " + key + " -> " + visit);
				}
			}
			return visit; 

		} catch (Exception e) {
			throw new RuntimeException("Error reading VisitDetails.json", e);
		}
	}

	@DataProvider(name = "lastname")
	public Object[][] lastNameProvider() {
		return new Object[][] { { getRandomLastName() } };
	}

	@DataProvider(name = "ownerDetailsProvider")
	public Object[][] ownerDetailsProvider() {
		try {

			File file = Paths.get("src", "test", "resources", "TestData", "OwnerDetails.json").toFile();
			Map<String, List<Map<String, String>>> detailsMap = mapper.readValue(file,
					new TypeReference<Map<String, List<Map<String, String>>>>() {
					});
			List<Map<String, String>> detailsList = detailsMap.get("details");

			// Randomly pick one entry from the list
			Map<String, String> randomDetail = detailsList.get(random.nextInt(detailsList.size()));

			return new Object[][] { { randomDetail } };

		} catch (Exception e) {
			throw new RuntimeException("Error reading OwnerDetails.json", e);
		}

	}

	

	@DataProvider(name = "lastNameWithRandomPetProvider")
	public Object[][] lastNameWithRandomPetProvider() {
		String lastName = getRandomLastName();
		Map<String, String> pet = getRandomPetRecord();
		return new Object[][] { { lastName, pet } }; // (String, Map<String,String>)
	}
	
	@DataProvider(name = "lastNameWithRandomPetVisitProvider")
	public Object[][] lastNameWithRandomPetVisitProvider() {
		String lastName = getRandomLastName();
		Map<String, String> visit = getRandomPetVisitRecord();
		return new Object[][] { { lastName, visit } }; // (String, Map<String,String>)
	}

}
