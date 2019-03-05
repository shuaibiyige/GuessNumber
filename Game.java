import java.util.Scanner;
import java.lang.Math;

/**
 * A Game class for a game program. human player and computer player guess the hidden number.
 * Use gameStart() to start the game.
 *
 * @author  CE LI
 * @version April 2018
 */
public class Game
{
    private Player player1;           //player1 is person;
    private Player player2;           //player2 is computer;
    private RandomNumber hidden;
    
    /**
     * Create a default settings for the game.
     */
    public Game()
    {
        player1 = new Player();
        player2 = new Player();
        hidden = new RandomNumber();
    }
    
    public int hiddenNumber()
    {
        hidden.setRandomNumber(1,100);
        return hidden.getRandomNumber();
    }
    
    public void gameStart()
    {
        String name = "";
        boolean nameValid = false;
        int round = 1;
        int score1 = 0;         //person's score in each round;
        int score2 = 0;         //computer's score in each round;
        int totalScore1 = 0;             //person's totalScore;
        int totalScore2 = 0;             //computer's totalScore;
        RandomNumber first = new RandomNumber();
        RandomNumber computerAbandon = new RandomNumber();
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to Gue55ing Game");
        System.out.println("Please enter a name length between 1 and 8");
        while (nameValid == false)
        {
            name = console.nextLine();
            nameValid = player1.isChar(name);
        }
        System.out.println("Hello, " + name.trim());
        player1.setName(name);
        player2.setName("The computer");
        System.out.println("==============================================");
        for (; round < 5; round++)
        {
            System.out.println("This is round " + round + " out 4");
            System.out.println("==============================================");
            whoFirst(first);
            int whoGuess = first.getRandomNumber();
            int attempts = 0;
            int attempt = 1;
            int lowerLimit = 1;
            int upperLimit = 100;
            int guess = 0;
            System.out.println(" will start first");
            presentation(attempts);
            setHiddenNumber();
            int abandon = abandonIndicator();
            player1.setPlayerGuess(0);
            player2.setPlayerGuess(0);
            attempts++;
            for (; attempts < 7; attempts++)
            {
                if (whoGuess % 2 == 0)    //computer's turn
                {
                    if (computerAbandon.getRandomNumber() == abandon)           //if the computer abandons;
                        attempts = 10;
                    else
                    {
                        player2Guess(guess, attempts, lowerLimit, upperLimit);
                        if (guess > hidden.getRandomNumber())
                            upperLimit = guess - 1;
                        if (guess < hidden.getRandomNumber())
                            lowerLimit = guess + 1;
                        if (player2.getPlayerGuess() == hidden.getRandomNumber())
                            attempts = 10;
                    }
                }
                else       //person's turn;
                {
                    System.out.println("Please enter your guess");
                    if (player2.getPlayerGuess() < hidden.getRandomNumber())
                        lowerLimit = personGuessRange(upperLimit, lowerLimit);
                    else
                        upperLimit = personGuessRange(upperLimit, lowerLimit);
                    String guessCheck = console.nextLine();
                    while (isValid(guessCheck) == false)
                        guessCheck = console.nextLine();
                    guess = Integer.valueOf(guessCheck);
                    if (guess == 999)     //abandon the round;
                    {
                        attempts = 10;
                    }
                    else if ((guess < lowerLimit || guess > upperLimit) && guess >= 1 && guess <= 100)
                    {
                        System.out.println("your guess isn't in the correct range, you don't have another chance");
                        presentation(attempts);
                        if (guess < lowerLimit)
                            guess = lowerLimit - 1;
                        if (guess > upperLimit)
                            guess = upperLimit + 1;
                    }
                    else
                        attempts = player1Guess(guess, attempts);
                }
                whoGuess++;
                attempt++;
            }
            System.out.println("round " + round + " is over!");
            System.out.println("==============================================");
            score1 = resultSummary(guess, computerAbandon.getRandomNumber(), 999, abandon, attempt, score1, totalScore1, player1, player2);       //person
            totalScore1 = totalScore1 + score1;
            player1.setScore(totalScore1);
            score2 = resultSummary(computerAbandon.getRandomNumber(), guess, abandon, 999, attempt, score2, totalScore2, player2, player1);       //computer
            totalScore2 = totalScore2 + score2;
            player2.setScore(totalScore2);
            System.out.println("hidden number is " + hidden.getRandomNumber());
            System.out.println("Press <ENTER> to continue");
            console.nextLine();
        }
        System.out.println(player2.getName() + " scored " + totalScore2);
        System.out.println(player1.getName() + " scored " + totalScore1);
        result(totalScore1, totalScore2);
    }
    
    public void presentation(int attempts)
    {
        Scanner console = new Scanner(System.in);
        System.out.println(attempts + " out of 6 attempts so far");
        System.out.println("Press <ENTER> to continue");
        console.nextLine();
        System.out.println("==============================================");
    }
    
    public int resultSummary(int player1Enter, int player2Enter, int player1Abandon, int player2Abandon, int attempt, int score, int totalScore, Player playerX, Player playerY)
    {
        if (player1Enter == player1Abandon) 
        {
            System.out.println(playerX.getName() + " abandon the round");
            System.out.println(playerX.getName() + " scored 0");
            System.out.println(playerY.getName() + " scored 0");
            return score = 0;
        }
        else
        {
            if (player2Enter == player2Abandon)
                return score = 0;
            else
            {
                if (playerX.getPlayerGuess() == hidden.getRandomNumber())
                {
                    score = caculateScore(attempt);
                    System.out.println(playerX.getName() + " got the correct answer");
                    System.out.println(playerX.getName() + " scored " + score);
                    return score;
                }
                else
                {
                    if (playerY.getPlayerGuess() == hidden.getRandomNumber())
                    {
                        System.out.println(playerX.getName() + " scored 0");
                        return score = 0;
                    }
                    else
                    {
                        return score = caculateScore(playerX);
                    }
                }
            }
        }
    }
    
    public int personGuessRange(int upperLimit, int lowerLimit)
    {
        if (player2.getPlayerGuess() < hidden.getRandomNumber())
        {    
            System.out.println("It must be a number between " + (player2.getPlayerGuess() + 1) + " and " + upperLimit + " inclusive");
            return lowerLimit = player2.getPlayerGuess() + 1;
        }
        else
        {
            System.out.println("It must be a number between " + lowerLimit + " and " + (player2.getPlayerGuess() - 1) + " inclusive");
            return upperLimit = player2.getPlayerGuess() - 1;
        }
    }
    
    public int player1Guess(int guess, int attempts)
    {
        Scanner console = new Scanner(System.in);
        System.out.println("you guessed the number " + guess);
        player1.setPlayerGuess(guess);
        System.out.println("==============================================");
        personCompare();
        System.out.println("");
        System.out.println(attempts + " out of 6 attempts so far");
        System.out.println("Press <ENTER> to continue");
        console.nextLine();
        if (guess == hidden.getRandomNumber())
           return attempts = 10;
        return attempts;
    }
    
    public void player2Guess(int guess, int attempts, int lowerLimit, int upperLimit)
    {
        Scanner console = new Scanner(System.in);
        System.out.println("The computer will now guess");
        if (guess > hidden.getRandomNumber())
        {    
            upperLimit = guess - 1;
            System.out.println("The computer will pick a number from " + lowerLimit + " to " + upperLimit + " inclusive");
            computerEnter(lowerLimit, upperLimit);
        }
        if (guess < hidden.getRandomNumber())
        {    
            lowerLimit = guess + 1;
            System.out.println("The computer will pick a number from " + lowerLimit + " to " + upperLimit + " inclusive");
            computerEnter(lowerLimit, upperLimit);
        }
        System.out.println("Press <ENTER> to continue");
        console.nextLine();
        System.out.println("==============================================");
        System.out.println("The computer guessed the number " + player2.getPlayerGuess());
        System.out.println("==============================================");
        computerCompare();
        System.out.println(attempts + " out of 6 attempts so far");
        System.out.println("Press <ENTER> to continue");
        console.nextLine();
    }
    
    public void result(int totalScore1, int totalScore2)
    {
        if (totalScore1 > totalScore2)
            System.out.println(player1.getName() + " win!");
        else if (totalScore1 == totalScore2)
            System.out.println("no winner!");
        else
            System.out.println(player2.getName() + " win!");
    }
    
    public int caculateScore(Player player)
    {
        int score = 0;
        if (Math.abs((hidden.getRandomNumber() - player.getPlayerGuess())) >= 10)
        {
            System.out.println(player.getName() + " scored 0");
            return score = 0;
        }
        else
        {
             score = 10 - Math.abs((hidden.getRandomNumber() - player.getPlayerGuess()));
             System.out.println(player.getName() + " scored " + score);
             return score;
        } 
    }
    
    public int caculateScore(int attempts)
    {
        int score = 0;
        switch (attempts)                    
        {
            case 2: score = 20; break;
            case 3: score = 15; break;
            case 4: score = 11; break;
            case 5: score = 8; break;
            case 6: score = 6; break;
            case 7: score = 5; break;
        }
        return score;
    }
    
    public void whoFirst(RandomNumber first)
    {
        first.setRandomNumber(1,2);
        if (first.getRandomNumber() == 2)
            System.out.print("The computer");
        else  
            System.out.print(player1.getName());
    }
    
    public boolean isValid(String guess)
    {
        int position = 0;
        if (guess.length() == 0)
        {
            System.out.println("you need to enter a number, please enter again");
            return false;
        }
        else
        {
            while (position < guess.length())
            {
                char character = guess.charAt(position);
                if (Character.isDigit(character) == false)
                {
                    System.out.println("you should enter a number, please enter again");
                    return false;
                }
                else
                    position++;
            }
            if (Integer.valueOf(guess) < 1 || Integer.valueOf(guess) > 100 && Integer.valueOf(guess) != 999)
            {
                System.out.println("your guess should not be lower than 1 or higher than 100, please enter again");
                return false;
            }
            else
                return true;
        }
    }
    
    public int abandonIndicator()
    {
        RandomNumber abandon = new RandomNumber();
        abandon.setRandomNumber(1,20);
        return abandon.getRandomNumber();
    }
    
    public void setHiddenNumber()
    {
        hidden.setRandomNumber(1,100);
    }
    
    public void computerEnter(int lowerLimit, int upperLimit)
    {
        RandomNumber enterRandom = new RandomNumber();
        enterRandom.setRandomNumber(lowerLimit, upperLimit);
        player2.setPlayerGuess(enterRandom.getRandomNumber());
    }
    
    public void personCompare()
    {
       player1.getPlayerGuess();
       if (player1.getPlayerGuess() > hidden.getRandomNumber())
            System.out.println("the real number is lower");
       if (player1.getPlayerGuess() < hidden.getRandomNumber())
            System.out.println("the real number is higher");
       if (player1.getPlayerGuess() == hidden.getRandomNumber())
            System.out.println("you are correct!");
    }
    
    public void computerCompare()
    {
        if (player2.getPlayerGuess() > hidden.getRandomNumber())
            System.out.println("the real number is lower");
        if (player2.getPlayerGuess() < hidden.getRandomNumber())
            System.out.println("the real number is higher");
        if (player2.getPlayerGuess() == hidden.getRandomNumber())
            System.out.println("computer is correct!");
    }
}