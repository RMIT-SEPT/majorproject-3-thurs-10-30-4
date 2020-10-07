url|method|Input|Output|description
---|---|---|---|---
/api/Account|POST|Account fields in JSON format|Account created or error|Creates a new account
/api/Account/saveadmin|POST|Account fields in JSON format|Admin created or error|Creates a new admin account, temp
/api/Account/saveworker/{adminId}|POST|Account fields in JSON format, admin id in path|Worker created or error|Creates a new worker account
/api/Account/getworkersbyadmin/{adminId}|GET|admin id as path variable|All workers the admin has registered, or error|Retrieves all workers managed by admin
/api/service/save/{adminId}|POST|Service fields in JSON format|Service created or error|Creates a new service
/api/service/getall|GET|None|All registered services in JSON format|Retrieves all registered services
/api/service/get|GET|id (append ?id=(ID) to end of url)|Service that id belongs to|Gets a service by its id then returns it
/api/service/delete/{serviceId}|DELETE|id of service as path variable|Confirmation that service and associated timeslots were found and deleted, or error|Deletes service and associated timeslots from backend
/api/service/findbyadmin/{adminId}|GET|id of admin as path variable|Service if admin is valid, else error message|Gets the service that an admin manages
/api/service/getavailableworkers/{serviceId}|GET|id of service as path variable|Workers that admin of service manages, or error message if service not found|Retrieves all workers that an admin can assign to a service
/api/timeslot/save/{serviceId}/{workerId}|POST|timeslot fields in JSON format, Id of service and id of worker as path variables|Timeslot created or error|Creates a new timeslot for a service
/api/timeslot/getbyservice/{serviceId}|GET|id of service as path variable|All timeslots that belong to that service, or null if no service found|Gets all timeslots that relate to a service
/api/timeslot/getbyid/{timeslotId}|GET|id of timeslot as path variable|Timeslot that belongs to that id, if it exists|Gets a timeslot from its id
/api/timeslot/getbyworkerid/{workerId}|GET|id of worker as path variable|Timeslots that have been assigned to worker, or error message|Gets timeslots that are assigned to worker
/api/timeslot/getbyadmin/{adminId}|GET|id of admin as path variable|Timeslots that belong to service that admin manages, or error message|Retrieves all timeslots for service that admin manages
/api/timeslot/delete/{timeslotId}|DELETE|id of timeslot as path variable|Confirmation that timeslot was found and deleted, or error|Deletes timeslot from backend
/api/booking/save|POST|id of timeslot as timeslotId, id of customer as customerId, in json)|Timeslot including booking or error message|Creates a booking for a timeslot
/api/booking/getbyid/{bookingId}|GET|id of booking as path variable|Timeslot that booking belongs to or error message|Gets the timeslot for a booking (including the booking)
/api/booking/getbycustomer/{customerId}|GET|id of customer as path variable|Timeslots that customer has booked or error message|Gets all bookings for a customer
/api/booking/delete/{bookingId}|DELETE|id of booking as path variable|True if deleted, or error message if not|Deletes a booking
