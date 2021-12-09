/**This class for User objects that contains Ticket object requests
* @author Brenden Galli and Derek Nguyen
* @version 8
*/
import java.util.*;

public class User
{
  //instance variables
  private String emailAddress;
  private ArrayList<Ticket> ticketList;

  //variables to indicate if user has already requested a specific Ticket type
  private boolean regular = false;
  private boolean elevated = false;
  private boolean VIP = false;
   
  /**This method is the constructor for User objects
  *
  * @param userEmail - the email address of the user
  * @param firstRequest - the type of the User's first Ticket request
  */
  public User(String userEmail, String firstRequest)
  {
    this.emailAddress = userEmail;
    ticketList = new ArrayList<Ticket>();
    ticketList.add(new Ticket(firstRequest));

    //if else statements to indicate the type that has been chosen
    if(firstRequest.equals("regular"))
    {
      regular = true;
    }
    else if(firstRequest.equals("elevated"))
    {
      elevated = true;
    }
    else if(firstRequest.equals("VIP"))
    {
      VIP = true;
    }
    else
    {
    throw new IllegalArgumentException("Type of ticket must be \"regular\", \"elevated\", or \"VIP\"");
    }
  }

  /**This method adds a Ticket object to the Ticket array list and indicates that a 
  * Ticket of that type has been added. If a Ticket of the specified type has already
  * been added, no Ticket object will be added
  *
  *@param ticketType - the type of the Ticket to be added
  */
  public void addTicket(String ticketType)
  {
    if(ticketType.equals("regular") && !regular)
    {
      regular = true;
      ticketList.add(new Ticket(ticketType));
    }
    else if(ticketType.equals("elevated") && !elevated)
    {
      elevated = true;
      ticketList.add(new Ticket(ticketType));      
    }
    else if(ticketType.equals("VIP") && !VIP)
    {
      VIP = true;
      ticketList.add(new Ticket(ticketType));
    }
    else if(!ticketType.equals("regular") && !ticketType.equals("elevated") && !ticketType.equals("VIP"))
    {
      throw new IllegalArgumentException("Type of ticket must be \"regular\", \"elevated\", or \"VIP\"");
    }
  }

  /**This method returns the email address of the User
  * 
  * @return the email address of the user
  */
  public String getEmailAddress()
  {
    return this.emailAddress;
  }

  /**This method returns the User object's list of Ticket objects
  */
  public ArrayList<Ticket> getTicketList()
  {
    return this.ticketList;
  }

  /**This method returns the address and text of the email to be sent to the User.
  * The text in the email is determined by if the Tickets found matches
  *
  * @return the email address and text in the email as one String across multiple lines
  */
  public String getEmailText()
  {
    //line every email starts with
    String email = "\nDear valued customer,\n";
    
    //iterate through ticket list
    for(int i = 0; i < ticketList.size(); i++)
    {
      //Add message depending on whether the current ticket found a match or not
      if(ticketList.get(i).getMatch())
      {
        email += String.format("Request for %s ticket successful. Please visit secure payment system to finalize purchase of %s ticket.%n", ticketList.get(i).getType(), ticketList.get(i).getType());
      }
      else
      {
         email += String.format("Sorry, your request for %s  ticket was unsuccessful.%n", ticketList.get(i).getType());
      }
    }

    return String.format("%s%s", this.emailAddress, email);
  }
}