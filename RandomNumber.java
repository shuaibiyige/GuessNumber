import java.util.Random;

/**
 * A random number class for generating a random number.
 *
 * @author  CE LI
 * @version April 2018
 */
public class RandomNumber
{
    private int randomNumber;
    
    /**
     * Initialize the random number.
     */
    public RandomNumber()
    {
        randomNumber = 0;
    }
    
    /**
     * Return the random number.
     */
    public int getRandomNumber()
    {
        return randomNumber;
    }
    
    /**
     * Gerenate a random number.
     */
    public void setRandomNumber(int min,int max)
    {
        Random random = new Random();
        randomNumber = random.nextInt(max - min + 1) + min;
    }
}