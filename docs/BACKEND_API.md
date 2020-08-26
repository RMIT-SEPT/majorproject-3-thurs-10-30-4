url|method|Input|Output|description
---|---|---|---|---
/api/Account|POST|Account fields in JSON format|Account created or error|Creates a new account
/api/service/save|POST|Service fields in JSON format|Service created or error|Creates a new service
/api/service/getall|GET|None|All registered services in JSON format|Retrieves all registered services
/api/service/get|GET|id (append ?id=(ID) to end of url)|Service that id belongs to|Gets a service by its id then returns it
/api/service/delete/{serviceId} (not yet implemented)|(undecided)|id of service as path variable|Confirmation that service and associated timeslots were found and deleted, or error|Deletes service and associated timeslots from backend
/api/timeslot/save/{serviceId}|POST|timeslot fields in JSON format, Id of service as path variable|Timeslot created or error|Creates a new timeslot for a service
/api/timeslot/getbyservice/{serviceId} (not yet implemented)|GET|id of service as path variable|All timeslots that belong to that service, if service can be found|Gets all timeslots that relate to a service
/api/timeslot/getbyid/{timeslotId} (not yet implemented)|GET|id of timeslot as path variable|Timeslot that belongs to that id, if it exists|Gets a timeslot from its id
/api/timeslot/delete/{timeslotId} (not yet implemented)|(undecided)|id of timeslot as path variable|Confirmation that timeslot was found and deleted, or error|Deletes timeslot from backend