# domination
* map source comes from Domination: https://domination.sourceforge.io/getmaps.shtml
  * map structure:
    *       [continents]
    North-America 6 yellow <~~ name of the continent (try keep them in that order if you are doing a map of the "real world" as then the missions work. The number is the army value, and the color does totally nothing
  * <b> Order of continent matters-  since its followed after for the country - continent link </b>
     
      *     [countries]
      * 1 Alaska 1 44 83 <~~ the first number is the number of the country (they do have to be in order) the next one is what continent it belongs to.
      * the other two numbers are the x and y co-ordinates of the circle that will be put on that country
    *     [borders]
   * 1 2 3 38 <~~ the first number is the number of the country (need to be in order here as well I think) and the other numbers that follow are what countries that country is adjacent too  
   *  source: https://domination.sourceforge.io/makemaps.shtml
