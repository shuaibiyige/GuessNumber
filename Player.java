import java.lang.String;

/**
 * A player class for storing player's information.
 *
 * @author  CE LI
 * @version April 2018
 */
public class Player
{
    private String name;
    private int score;
    private int guesses;

    /**
     * Create a default settings for the player.
     */
    public Player()
    {
        name = "";
        score = 0;
        guesses = 0;
    }
    
    /**
     * Create a non-default constructor to store player's name, except the leading and trailing space.
     */
    public Player(String setName, int setScore, int setGuess)
    {
        name = setName.trim();
        score = setScore;
        guesses = setGuess;
    }
    
    /**
     * Return the name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the guess number.
     */
    public int getPlayerGuess()
    {
        return guesses;
    }
    
    /**
     * Return the score.
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * Create the a of the player.
     */
    public void setName(String setName)
    {
        name = setName.trim();
    }
        
    /**
     * Player enters a guess number.
     */
    public void setPlayerGuess(int guessNumber)
    {
        guesses = guessNumber;
    }
    
    /**
     * Store the score of the player.
     */
    public void setScore(int scores)
    {
        score = scores;
    }
    
    public boolean isChar(String newName)
    {
        String isName = newName.trim();
        if (isName.length() == 0 || isName.length() > 8)
        {
            System.out.println("your name should not be more than 8 characters or empty");
            return false;
        }
        else
        {
            for (int position = 0; position < isName.length(); position++)
            {
                char character = isName.charAt(position);
                if (character < 'a' || character > 'z')
                {    
                    System.out.println("only character is allowed");
                    return false;
                }
            }
            return true;
        }
    }
}
