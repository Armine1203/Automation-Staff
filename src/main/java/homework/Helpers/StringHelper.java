package homework.Helpers;
import org.apache.commons.lang3.RandomStringUtils;


import org.apache.commons.text.WordUtils;

public class StringHelper {
    public static String generateRandomString(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String capitalizeFirstLetters(String text){
       return WordUtils.capitalizeFully(text);
    }
}
