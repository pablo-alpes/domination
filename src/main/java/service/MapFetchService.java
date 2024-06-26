package service;

import constants.constants;
import model.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapFetchService {
    private static final BoardImpl board = new BoardImpl();

    /**
     Main assumptions for the method:
     1. The file will be parsed sequentially : continents, countries and borders.
     2. Each section of the file has its own logic structure, then it will be needed to be decoded accordingly to then be instantiated in the corresponding object
     3. Countries will keep the borders information with them
     */
    public static BoardImpl translatorToBoard(String filename) throws FileNotFoundException {


        String mapFile = constants.PATH + filename;
        Scanner scan = new Scanner(new File(mapFile));
        String header = "";

        try {
            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.isEmpty()) continue; //we skip once the line is blank within the header

                //Scenarios Mapping
                //Flags entered in the file section header and we move onto the 1st line with info for the header


                switch (line) {
                    case "[continents]":
                        header = "[continents]";
                        line = scan.nextLine();
                        break;
                    case "[countries]":
                        header = "[countries]";
                        line = scan.nextLine();
                        break;
                    case "[borders]":
                        header = "[borders]";
                        line = scan.nextLine();
                        break;
                    default:
                        break;
                }
                //reads corresponding line for the header is transversed and adds to the Board the corresponding object
                switch (header) {
                    case "[continents]":
                        board.addContinent(populateContinents(line));
                        break;
                    case "[countries]":
                        board.addCountry(populateCountries(line));
                        break;
                    case "[borders]":
                        populateBorders(line); //needs to only add the borders info, not all++
                        break;
                }
            }
        } catch (NoSuchElementException e) { return null; }

        return board; //returns back the Board game for the map loaded
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
        int continentId;

        //creating and populating object
        ContinentImpl continent = new ContinentImpl(); //TODO -- Fix call to interface to Impl
        continent.setContinentName(parameters[0]);
        continent.setContinentArmy(Integer.parseInt(parameters[1]));
        continent.setOwnership("");
        try {
            continentId = board.getContinents().size() + 1;
        }
        catch(NullPointerException e) {
            continentId = 1;
        }
        continent.setContinentId(continentId); //Storage of continents in Board List<Continents> and adding the size as the Id
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
        country.setArmies(0); // Storage of countries is in populated in the board List<countries>

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
        (board.getCountries().get(Integer.parseInt(parameters[0])-1)).setBorders(borders); //passing by the borders to the countryId into the list

        return country;
    }

}