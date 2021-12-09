/** This class is for a Ticket object which can be entered into a lottery
* @author Brenden Galli and Derek Nguyen
* @version 8
*/

public class Ticket
{
  //instance variables
  private int id;
  private String type;
  private boolean match = false;

  //class variables common to all Ticket objects
  private static int regularCount = 0;
  private static int elevatedCount = 0;
  private static int VIPCount = 0;

  /**This method is the constructor for a Ticket object
  *
  *@param ticketType - the type of Ticket
  */
  public Ticket(String ticketType)
  {
    this.type = ticketType;

    //if else statements to increment total number of whatever type Ticket object is
    if(type.equals("regular"))
    {
      this.id = regularCount++;
    }
    else if(type.equals("elevated"))
    {
      this.id = elevatedCount++;      
    }
    else if(type.equals("VIP"))
    {
      this.id = VIPCount++;
    }
    else
    {
      throw new IllegalArgumentException("Type of ticket must be \"regular\", \"elevated\", or \"VIP\"");
    }
  }

  /**This method returns the total number of regular ticket requests
  *
  * @return the number of regular ticket requests
  */
  public static int getRegularCount()
  {
    return regularCount;
  }

  /**This method returns the total number of elevated ticket requests
  *
  * @return the number of elevated ticket requests
  */
  public static int getElevatedCount()
  {
    return elevatedCount;
  }

  /**This method returns the total number of VIP ticket requests
  *
  * @return the number of VIP ticket requests
  */
  public static int getVIPCount()
  {
    return VIPCount;
  }

  /**This method returns the type of the Ticket object
  *
  * @return the type of the Ticket object
  */
  public String getType()
  {
    return this.type;
  }

  /**This method returns the id of the Ticket object
  *
  * @return the id of the Ticket object
  */
  public int getId()
  {
    return this.id;
  }

  /**This method returns whether ot not the Ticket object found a match
  *
  * @return if the Ticket object found a match or not
  */
  public boolean getMatch()
  {
    return this.match;
  }

  /**This method checks the Ticket id against a number. If they are the same, the
  * match variable is set to true.
  *
  * @param winningNumber - the number to be checked
  */
  public void findMatch(int winningNumber)
  {
    if(this.id == winningNumber)
    {
      this.match = true;
    }
  }

  /**This method sets the match instance value to true or false
  *
  * @param matchFound - boolean value to set match to
  */
  public void setMatch(boolean matchFound)
  {
    this.match = true;
  }
}