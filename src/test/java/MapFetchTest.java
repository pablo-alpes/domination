import model.Continent;
import model.ContinentImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import service.MapFetchService;


import static org.junit.Assert.assertEquals;

@SpringBootConfiguration
public class MapFetchTest {
    //TODO-- Dependency Injection to Implement
    // @Autowired
    //public MapFetchService mapFetchService;

    @Test
    @DisplayName("Checks file is a valid map")
    public void GivenMapWhenReadThenChecksFileIntegrity() throws FileNotFoundException {
        //ARRANGE
        //String mapFile = constants.PATH + "brazil/brasil.map";
        String filename = "spain/spain.map";
        int checks = 0;

        //ACT
        checks = MapFetchService.integrityCheck(filename);
        //ASSERT
        assertEquals(checks,3);

    }
    @Test
    @DisplayName("Map correctly 2 continents from  2 line parsed")
    public void GivenMapWhenContinentFoundThenMapContinentObjectList() {
        //Example1 "Galicia 2 verde"
        //Example2 "South 2 yellow"
        //ARRANGE
        String continentLine = "Galicia 2 verde";
        //ACT
        ContinentImpl continent = MapFetchService.populateContinents(continentLine);
        //ASSERT
        assertEquals(continent.getContinentName(), "Galicia");
        assertEquals(continent.getContinentArmy(), 2);

        continentLine = "South 3 yellow";

        //ACT
        continent = MapFetchService.populateContinents(continentLine);
        //ASSERT
        assertEquals(continent.getContinentName(), "South");
        assertEquals(continent.getContinentArmy(), 3);
    }

    //@Test
    //@DisplayName("Map correctly a continent from the map file")

    @Test
    //@DisplayName("Fetches map from file map and does the mapping towards the object Continent and Country model")
    public void GivenMapWhenReadThenFetchMapToObjectModel() {
        /*
         1. The file will be parsed sequentially : continents, countries and borders.
         2. Each section has its own logic, then it will be needed to be decoded accordingly to then be instantiated in the corresponding object
        */
        //ARRANGE
    }


}
