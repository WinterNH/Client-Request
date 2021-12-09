This program reads data from a file, to register ticket requests from clients. It uses methods to determine whether the requests find a match. Then it writes to file the email to be sent to clients depending on whether their requests found a match.
@author Brenden Galli and Derek Nguyen

	ticketRQ.txt File example input:
	5
	sampleemail@tdsb.on.ca
	regular
	sampleemail2@tdsb.on.ca
	regular
	sampleemail2@tdsb.on.ca
	regular
	sampleemail@tdsb.on.ca
	regular
	sampleemail@tdsb.on.ca
	elevated


emails.txt File example output:

	<EMAIL>>
	sampleemail@tdsb.on.ca
	Dear valued customer,
	Request for regular ticket successful. Please visit secure payment system to finalize purchase of regular ticket.
	Request for elevated ticket successful. Please visit secure payment system to finalize purchase of elevated ticket.
	Request for VIP ticket successful. Please visit secure payment system to finalize purchase of VIP ticket.
	<<END EMAIL>>
	<<EMAIL>>
	sampleemail2@tdsb.on.ca
	Dear valued customer,
	Request for regular ticket successful. Please visit secure payment system to finalize purchase of regular ticket.
	<<END EMAIL>>
	<<EMAIL>>
	sampleemail3@tdsb.on.ca
	Dear valued customer,
	Request for elevated ticket successful. Please visit secure payment system to finalize purchase of elevated ticket.
	<<END EMAIL>>