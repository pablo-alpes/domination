package service;

import constants.constants;
import model.ContinentImpl;
import model.CountryImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapFetchService {
    public static void translatorToBoard(String filename) throws FileNotFoundException {
        String mapFile = constants.PATH + filename;
        Scanner scan = new Scanner(new File(mapFile));

        try {
            while (scan.hasNext()) {
                String line = scan.nextLine();
                //TODO --Determine when to stop and when to switch cases

                //Scenarios Mapping
                switch (line) {
                    case "continents":
                        populateContinents(line);
                        return;
                    case "countries":
                        return;
                    case "borders":
                        return;
                    default:
                        return;
                }
            }
        } catch (
                NoSuchElementException e) {
            return;
        }
    }

    public static int integrityCheck(String filename) throws FileNotFoundException {
        String mapFile = constants.PATH + filename;
        Scanner scan = new Scanner(new File(mapFile));

        //A valid map file needs to have the following headers
        String[] parameters = {"[continents]", "[countries]", "[borders]"};
        int checks = 0;

        try {
            while (scan.hasNext()) {
                String line = scan.nextLine();
                for (String param : parameters) {
                    if (param.equals(line)) checks++;
                }
            }
        } catch (NoSuchElementException e) {
            return 0;
        }
        return checks;
    }

    public static ContinentImpl populateContinents(String continentLine) {
        //Example1 "Galicia 2 verde"
        //Example2 "South 2 yellow"
        //String int string


        //Split parameters from file to populate continent
        //source: https://stackoverflow.com/questions/29447582/java-searching-for-words-in-strings-separated-by-spaces
        String delimiter = " ";
        String[] parameters = continentLine.split(delimiter);

        //creating and populating object
        ContinentImpl continent = new ContinentImpl(); //TODO -- Fix call to interface to Impl
        continent.setContinentName(parameters[0]);
        continent.setContinentArmy(Integer.parseInt(parameters[1]));
        continent.setOwnership("");
        continent.setContinentId(0); //TODO -- Where to storage all the continents for the current game List<Continent>

        return continent;
    }


    public static CountryImpl populateCountries(String countryLine) {
        //1 La-Coruna 1 59 39
        //2 Lugo 1 98 42
        //int Str int int int

        //Split parameters from file to populate continent
        //source: https://stackoverflow.com/questions/29447582/java-searching-for-words-in-strings-separated-by-spaces
        String delimiter = " ";
        String[] parameters = countryLine.split(delimiter);

        //creating and populating object
        CountryImpl country = new CountryImpl(); //TODO -- Fix call to interface to Impl
        country.setCountryName(parameters[1]);
        country.setCountryId(Integer.parseInt(parameters[0]));
        country.setContinentId(Integer.parseInt(parameters[2]));
        country.setOwnership("");
        country.setArmies(0);
         //TODO -- Where to storage all the continents for the current game List<Continent>

        return country;
    }

    public static CountryImpl populateBorders(String countryLine) {
        // 1 2 3
        //int* int int

        //Split parameters from file to populate continent
        //source: https://stackoverflow.com/questions/29447582/java-searching-for-words-in-strings-separated-by-spaces
        String delimiter = " ";
        String[] parameters = countryLine.split(delimiter);

        //creating and populating object
        CountryImpl country = new CountryImpl(); //TODO -- Fix call to interface to Impl
        //https://stackoverflow.com/questions/52509610/convert-array-of-strings-to-list-of-integers
        List<Integer> borders = new ArrayList<Integer>();
        borders = Arrays.stream(parameters)    // stream of String
                .map(Integer::valueOf) // stream of Integer
                .collect(Collectors.toList());
        country.setBorders(borders);
        //TODO -- Where to storage all the continents for the current game List<Continent>

        return country;
    }

}