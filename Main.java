/** This program reads data from a file, to register ticket requests from
* clients. It uses methods to determine whether the requests find 
* a match. Then it writes to file the email to be sent to clients
* depending on whether their requests found a match.
* @author Brenden Galli and Derek Nguyen
* @version 8
*/
import java.io.*;
import java.util.*;

/** Main method that calls methods to read from file, run lotteries
* and then write to file.
*
*/
public class Main 
{

  public static void main(String[] args)
  {
    ArrayList<User> clientList = new ArrayList<User>(); 
  
    //attempt to read from file. Attempts to catch specific errors.
    try
    {
      readRequests(clientList);
    }
    catch(NoSuchElementException e)
    {
      System.out.println("File to be read is empty or number of requests does note match number specified at beginning of file.");
    }
    catch(IllegalArgumentException e)
    {
      System.out.println("Invalid request type or number of requests specified at beginning of file in invalid.");
    }
    catch(Exception e)
    {
      System.out.println("Error reading file.");
    }
  
    //call lottery methods
    regularLottery(clientList);
    elevatedLottery(clientList);
    VIPLottery(clientList);

    //attempt to write emails to file
    try
    {
      writeEmails(clientList);
    }
    catch(Exception e)
    {
      System.out.println("error writing to file");
    }
  }

  /** Reads user input data from file and checks for any requests for the same 
  * ticket by a duplicate user
  * 
  * Knowledge to write method from this video: 
  * https://www.youtube.com/watch?v=3RNYUKxAgmw
  *
  * @param userList - the User array list to use
  */
  public static void readRequests(ArrayList<User> userList) throws Exception
  {
    //declaration section
    File file = new File("ticketRQ.txt");
    Scanner in = new Scanner(file);
    int numRequests;
    boolean existingEmail;
    String email;
    String blankTest;

    //set numRequests to first line in file which should be an integer.
    numRequests = Integer.parseInt(in.nextLine()); 

    //runs once for the number of requests specified in first line
    for(int i = 0; i < numRequests; i++)
    {
      existingEmail = false; //set to false at beginning of each iteration
 
      // Calls the bSkip method to search for empty blank lines
      blankTest = in.nextLine();
      if (blankTest.isEmpty()){
        blankTest = bSkip(blankTest,in);
      }      
      email = blankTest;           


      //iterates through list to check if email has already been assigned to a User
      for(int j = 0; j < userList.size(); j++)
      {
        //if the email already exists, call addTicket method to add Ticket object to existing user and sets existingEmail to true;
        if(email.equals(userList.get(j).getEmailAddress()))
        { 
          userList.get(j).addTicket(in.nextLine());
          existingEmail = true;
        }
      }
      
      //if the email does not yet exist, add a new User object to the array list
      if(!existingEmail)
      {
        blankTest = in.nextLine();
        if (blankTest.isEmpty()){
        blankTest = bSkip(blankTest,in);        
      }

        userList.add(new User(email, blankTest));
      }
    }
    in.close();
  }

  /** Prints lottery results to a new file to be used for output. 
  * Lottery results printed from the ArrayList 
  *
  * Knowledge on how to write from file is from this video: 
  * https://www.youtube.com/watch?v=k3K9KHPYZFc
  *
  * @param userList - the array list to use
  */
  public static void writeEmails(ArrayList<User> userList) throws IOException
  {
    //Creates new File object and objects to print to the file. If no file with specified name exists, new txt file will be created.
    File file = new File("emails.txt");
    FileWriter fw = new FileWriter(file);
    PrintWriter pw = new PrintWriter(fw);

    //for each client, print email text, and indicators for beginning and ending of email
    for(int i = 0; i < userList.size(); i++)
    {
      pw.println("<<EMAIL>>");
      pw.print(userList.get(i).getEmailText());
      pw.println("<<END EMAIL>>");
    }
    pw.close();
  }

  /** 
  * Runs lottery for regular ticket instances if there are more    
  * than 10 requests. Otherwise all tickets find a match.         
  *
  *@param userList - the array list to run lottery for
  */
  public static void regularLottery(ArrayList<User> userList)
  {
    int[] matchKey;

    if(Ticket.getRegularCount() > 10)
    {
      //create random number array to compare to id of requests
      matchKey = randomArray(10, Ticket.getRegularCount());

      //determine which requests get a match
      determineMatches(userList, matchKey, "regular");
    }
    else
    {
      allMatchTrue(userList, "regular");
    }
  }

  /** Runs lottery for elevated ticket instances if there are more than 5 requests.
  * Otherwise all tickets find a match. 
  *
  * @param userList - the array list to run lottery for
  */
  public static void elevatedLottery(ArrayList<User> userList)
  {
    int[] matchKey;

    if(Ticket.getElevatedCount() > 5)
    {
      //create random number array to compare to id of requests
      matchKey = randomArray(5, Ticket.getElevatedCount());

      //determine which requests get a match
      determineMatches(userList, matchKey, "elevated");
    }
    else
    {
      allMatchTrue(userList, "elevated");
    }
  }

  /** Runs lottery for vip ticket instances if there are more than 2 requests.
  * Otherwise all tickets find a match. 
  *
  * @param userList - the array list to run lottery for
  */
  public static void VIPLottery(ArrayList<User> userList)
  {
    int[] matchKey;

    if(Ticket.getVIPCount() > 2)
    {
      // Random number array to be used to assign corresponding userList index as winner
      matchKey = randomArray(2, Ticket.getVIPCount());

      //determine which requests get a match
      determineMatches(userList, matchKey, "VIP");
    }
    else
    {
      allMatchTrue(userList, "VIP");
    }
  }

  /** Creates an array of certain size that contains randomly generates unique integers in
  * specified range.
  *
  * @param size - the size of the array
  * @param range - the range that the integers can be in
  * @return random array of integers
  */
  public static int[] randomArray(int size, int range)
  {
    //declaration section
    boolean run;
    boolean unique = true;
    int[] randArray = new int[size];

    randArray[0] = (int)(Math.random() * range); //sets index 0 to random int

    //runs until index is full
    for(int i = 1; i < randArray.length; i++)
    {
      //loop to make sure of no duplicate integers
      do
      {
        randArray[i] = (int)(Math.random() * range); //set current index to random number

        //iterates through each of previous indices
        for(int j = 0; j < i; j++)
        {
          //if the integer already exists in array, set unique to false
          if(randArray[i] == randArray[j])
          {
            unique = false;
          }

        }

        //if statement to determine if loop should run again
        if(unique)
        {
          run = false;
        }
        else
        {
          run = true;
        }

        unique = true; //set unique back to true
      }
      while (run);
    }
    return randArray;
  }

  /**
  * Runs through User list to set Ticket objects' instance variable match to true if a 
  * match is found. Should be run if number of requests for a certain type exceeds
  * the max number of ticket matches. Only for a single ticket type.
  *
  * @param userList - the list that contains the Tickets to find matches for
  * @param matchKey - the array that holds the key values
  * @param ticketType - the ticket type to find matches for
  */
  public static void determineMatches(ArrayList<User> userList, int[] matchKey, String ticketType)
  {
    /*in totality, the nested for loops check all the values in the key with all
    the ids of the Tickets of specified type to find matches*/

    //iterate through entire User list
    for(int i = 0; i < userList.size(); i++)
    {
      //iterate through each value in match key
      for(int j = 0; j < matchKey.length; j++)
      {
        //iterate though each Ticket list of each user
        for(int k = 0; k < userList.get(i).getTicketList().size();k++)
        {
          //if the ticketType in method parameter matches the Ticket object's type
          if(userList.get(i).getTicketList().get(k).getType().equals(ticketType))
          {
            //find if the Ticket object's id matches the current key value
            userList.get(i).getTicketList().get(k).findMatch(matchKey[j]);
          }
        }
      }
    }
  }

  /** This method sets all the Ticket objects' match variable to true. Should be run
  * if number of requests for a certain type is less than the max number of ticket matches.
  * Only for a single ticket type.
  *
  * @param userList - the list of Users to set Ticket objects' match variable to true
  * @param ticketType - type of ticket to set match to true for
  */
  public static void allMatchTrue(ArrayList<User> userList, String ticketType)
  {
    //iterates through User list
    for(int i = 0; i < userList.size(); i++)
    {
      //iterates through Ticket list of current User
      for(int j = 0; j < userList.get(i).getTicketList().size(); j++)
      {
        // If type of current Ticket object is equal to ticket type specified in parameter
        if(userList.get(i).getTicketList().get(j).getType().equals(ticketType))
        {
          //set match variable to true
          userList.get(i).getTicketList().get(j).setMatch(true);
        }
      }
    }  
  }

/** This method iterates and skips over blank lines within 
* the input file, closing upon 
* reaching that of which is not a blank line
*
* @param bTest - the current nextLine() 
* @param read - the scanner variable
* @return the string value of the next non-blank line 
*/
  public static String bSkip(String bTest, Scanner read)
  {
    while (bTest.isEmpty())
    {
      bTest = read.nextLine();
    }
    return bTest;
  }
}