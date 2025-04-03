package pages;
import org.apache.commons.lang3.RandomStringUtils;


import net.bytebuddy.utility.RandomString;
import org.apache.commons.text.WordUtils;

import java.util.Random;

public class StringHelper {
    public static String generateRandomString(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String capitalizeFirstLetters(String text){
       return WordUtils.capitalizeFully(text);
    }
}
