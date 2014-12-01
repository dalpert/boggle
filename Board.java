//Daniel Alpert

import java.util.*;
import java.io.*;
import java.awt.Color;
import static java.lang.System.*;
import java.util.ArrayList;

public class Board {
  
  public static final int MAX = 4;
  private Cube[][] board;
  private TreeSet<String> dictionary;
  private TreeSet<String> guessedWords;
  private int score = 0;
  private int bonus = 0;
  private String message = "";
  private Color color = Color.GREEN;
  
  public Board() throws IOException
  {
    guessedWords = new TreeSet<String>();
    dictionary = new TreeSet<String>();
    board = new Cube[MAX][MAX];
    //create();
  }
  
  public Cube[][] getBoard() 
  {
    return board;
  }
  
  public void add(int x, int y, Cube c)
  {
    board[x][y] = c;
  }
  
  public Cube get(int x, int y) 
  {
    return board[x][y];
  }
  
  public String toString() 
  {
    String concat = "";
    for (int i = 0; i < MAX; i++) {
      for (int j = 0; j < MAX; j++) {
        concat += board[i][j]+", ";
      }
    }
    return concat;
  }
  
  public void printBoard()
  {
    for (int i = 0; i < MAX; i++) {
      System.out.println();
      for (int j = 0; j < MAX; j++) {
        System.out.print(board[i][j].getTopLetter()+" ");
      }
    }
  }
  
  public Cube[][] shake() 
  {
    Cube temp;
    for (int i = 0; i < 100; i++) {
      int r1 = (int) (Math.random() * MAX); 
      int r2 = (int) (Math.random() * MAX);
      int r3 = (int) (Math.random() * MAX);
      int r4 = (int) (Math.random() * MAX);
      temp = board[r1][r2];
      board[r1][r2] = board[r3][r4];
      board[r3][r4] = temp;
    }
    return board;
  }
  
  public boolean isOnBoard(String word) 
  {    
    word = containsQ(word);
    //Makes word uppercase
    word = word.toUpperCase();
    //Iterates through the matrix (board)
    for (int x = 0; x < MAX; x++) {
      for (int y = 0; y < MAX; y++) {
        //If the the first letter of word is on the board...
        if (board[x][y].getTopLetter() == word.charAt(0)) {
          //...And if the length of the word is 1 then return true
          if (word.length() == 1) 
          {
            return true;
          }
          //Otherwise...
          else 
          {
            int[][] temp = new int[MAX][MAX];
            temp[x][y] = 1;
            //Word's first letter is cut off
            boolean result = letterConnects(word.substring(1), new Location(x,y), temp);
            if (result)
              return true;
          }
        }
      } 
    }
    return false;
  }
  
  public boolean letterConnects(String word, Location loc, int[][] temp) 
  {
    int[][] copy = new int[MAX][MAX];
    copyMatrix(temp, copy);
    boolean result = true;
    ArrayList<Location> surroundingSpots = getValidAdjacentLocations(loc, temp);
    for (Location a: surroundingSpots) {
      copyMatrix(copy, temp);
      if (board[a.getRow()][a.getCol()].getTopLetter() == word.charAt(0)) {
        if(word.length() == 1)
          return true;
        temp[a.getRow()][a.getCol()] = 1;
        result = letterConnects(word.substring(1), a, temp);
        if (result == true)
          return true;
      }
    }
    return false;
  }
  
  public void copyMatrix(int[][] from, int[][] to)
  {
    for (int i = 0; i < MAX; i++) {
      for (int j = 0; j < MAX; j++) {
        to[i][j] = from[i][j];
      }
    }
  }
  
  public ArrayList<Location> getValidAdjacentLocations(Location loc, int [][] temp)
  {
    ArrayList<Location> locs = new ArrayList<Location>();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        Location newLoc = new Location (loc.getRow() + x, loc.getCol() + y);
        if (newLoc.getRow() >= 0 && newLoc.getRow() < MAX && newLoc.getCol() >= 0 && newLoc.getCol() < MAX) {
          if (!(newLoc.getRow() == loc.getRow() && newLoc.getCol() == loc.getCol()))
            if(temp[newLoc.getRow()][newLoc.getCol()] == 0)
            locs.add(newLoc);
        }
      }
    }
    return locs;
  }
  
  public String containsQ (String word)
  {
    if (word.contains("qu"))
    {
      int index = word.indexOf('q') + 1;
      word = word.substring(0,index) + word.substring(index+1, word.length());
    }
    return word;
  }
  
  public void readDictionary()
  {
    Scanner scan = null;
    try
    {
      scan = new Scanner(new File("wordsEn.txt"));
    }
    catch (FileNotFoundException e)
    {
      System.out.println("Not found");
    }
    while (scan.hasNext())
    {
      dictionary.add(scan.next());
    }
  }
  
  public boolean isInDictionary(String word)
  {
    if (word.contains("quu") && !word.equals("squushing"))
    {
      return false;
    }
    if (dictionary.contains(word))
    {
      return true;
    }
    return false;
  }
  
  public void addWord (String word)
  {
    guessedWords.add(word);
  }
  
  public boolean hasBeenGuessed(String word)
  {
    if (guessedWords.contains(word)) {
      return true;
    }
    return false;
  }
  
  public void processBoard(String word) {  
    //printBoard();
    //System.out.println();
      if (isOnBoard(word) && isInDictionary(word) && hasBeenGuessed(word) == false) {
        if (word.length() < 3) {
          //System.out.println("Too short: no points");
          message = ": too short";
          bonus = 0;
          color = Color.RED;
        }
        if (word.length() >= 3 && word.length() <= 4) {
          score++;
          bonus = 1;
          message = "";
          color = Color.GREEN;
        }
        if (word.length() == 5) {
          score = score + 2;
          bonus = 2;
          message = "";
          color = Color.GREEN;
        }
        if (word.length() == 6) {
          score = score + 3;
          bonus = 3;
          message = "";
          color = Color.GREEN;
        }
        if (word.length() == 7) {
          score = score + 5;
          bonus = 5;
          message = "";
          color = Color.GREEN;
        }
        if (word.length() >= 8) {
          score = score + 11;
          bonus = 11;
          message = "";
          color = Color.GREEN;
        }
      }
      if (isOnBoard(word) == false) {
        //System.out.println("Not on board: no points");
        message = ": not on board";
        bonus = 0;
        color = Color.RED;
      }
      if (isInDictionary(word) == false) {
        //System.out.println("Not a word: no points");
        message = ": not a word";
        bonus = 0;
        color = Color.RED;
      }
      if (hasBeenGuessed(word)) {
        //System.out.println("Already been guessed: no points");
        message = ": already guessed";
        bonus = 0;
        color = Color.RED;
      }
      //System.out.println(score);
      addWord(word);
    }
  
  public int getScore() 
  {
    return score;
  }
  
  public int getBonus()
  {
    return bonus;
  }
  
  public String getMessage()
  {
    return message;
  }
  
  public Color getColor()
  {
    return color;
  }
  
 
   public static void main (String[] args) throws IOException {
    Cube[] dice = new Cube[16];
    
    Cube c1 = new Cube("AAEEGN");
    Cube c2 = new Cube("ELRTTY");
    Cube c3 = new Cube("AOOTTW");
    Cube c4 = new Cube("ABBJOO");
    Cube c5 = new Cube("EHRTVW");
    Cube c6 = new Cube("CIMOTV");
    Cube c7 = new Cube("DISTTY");
    Cube c8 = new Cube("EIOSST");
    Cube c9 = new Cube("DELRVY");
    Cube c10 = new Cube("ACHOPS");
    Cube c11 = new Cube("HIMNQU");
    Cube c12 = new Cube("EEINSU");
    Cube c13 = new Cube("EEGHNW");
    Cube c14 = new Cube("AFFKPS");
    Cube c15 = new Cube("HLNNRZ");
    Cube c16 = new Cube("DEILRX");
    dice[0] = c1;
    dice[1] = c2;
    dice[2] = c3;
    dice[3] = c4;
    dice[4] = c5;
    dice[5] = c6;
    dice[6] = c7;
    dice[7] = c8;
    dice[8] = c9;
    dice[9] = c10;
    dice[10] = c11;
    dice[11] = c12;
    dice[12] = c13;
    dice[13] = c14;
    dice[14] = c15;
    dice[15] = c16;
    
    Board board = new Board();
    
    int numCube = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        board.add(i, j, dice[numCube]);
        numCube++;
      }
    }
    
    board.shake();
    
    board.readDictionary();
    
    //board.printBoard();
    
       DisplayBoard graphics = new DisplayBoard(board);
       Interactions window = new Interactions(board);
       BoggleTimer timer = new BoggleTimer(window);
       timer.start();
  }
  
}