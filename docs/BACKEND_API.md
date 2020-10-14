url|method|Login Status|Input|Output|description
---|---|---|---|---|---
/api/Account|POST|Any|Account fields in JSON format|Account created or error|Creates a new account
/api/Account/Authenticate|POST|Any|JWT|Account or error|Provides Account object linked with JWT
/api/Account/Login|POST|Any|Username and password|JWT and Account|User logs in with username, password and recieved JWT and Account
/api/Account/saveadmin|POST|Any|Account fields in JSON format|Admin created or error|Creates a new admin account, temp
/api/Account/saveworker/{adminId}?token={token}|POST|As admin|Account fields in JSON format, admin id in path|Worker created or error|Creates a new worker account
/api/service/save/{adminId}?token={token}|POST|Same admin as id given|Service fields in JSON format|Service created or error|Creates a new service (1 per admin?)
/api/service/getall|GET|Any|None|All registered services in JSON format|Retrieves all registered services
/api/service/get|GET|Any|id (append ?id=(ID) to end of url)|Service that id belongs to|Gets a service by its id then returns it
/api/service/delete/{serviceId}|DELETE|As admin of service|id of service as path variable|Confirmation that service and associated timeslots were found and deleted, or error|Deletes service and associated timeslots from backend
/api/timeslot/save/{serviceId}/{workerId}|POST|Admin of service|timeslot fields in JSON format, Id of service and id of worker as path variables|Timeslot created or error|Creates a new timeslot for a service
/api/timeslot/getbyservice/{serviceId}|GET|Any|id of service as path variable|All timeslots that belong to that service, or null if no service found|Gets all timeslots that relate to a service
/api/timeslot/getbyid/{timeslotId}|GET|Any|id of timeslot as path variable|Timeslot that belongs to that id, if it exists|Gets a timeslot from its id
/api/timeslot/delete/{timeslotId}|DELETE|Admin of service|id of timeslot as path variable|Confirmation that timeslot was found and deleted, or error|Deletes timeslot from backend
/api/booking/save|POST|Same customer as in request body|id of timeslot as timeslotId, id of customer as customerId, in json)|Timeslot including booking or error message|Creates a booking for a timeslot
/api/booking/getbyid/{bookingId}|GET|Any|id of booking as path variable|Timeslot that booking belongs to or error message|Gets the timeslot for a booking (including the booking)
/api/booking/getbycustomer/{customerId}|GET|Any|id of customer as path variable|Timeslots that customer has booked or error message|Gets all bookings for a customer
/api/booking/delete/{bookingId}|DELETE|Customer of booking or admin of service|id of booking as path variable|True if deleted, or error message if not|Deletes a booking
