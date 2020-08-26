url|method|Input|Output|description
---|---|---|---|---
/api/Account|POST|Account fields in JSON format|Account created or error|Creates a new account
/api/service/save|POST|Service fields in JSON format|Service created or error|Creates a new service
/api/service/getall|GET|None|All registered services in JSON format|Retrieves all registered services
/api/service/get|GET|id (append ?id=(ID) to end of url)|Service that id belongs to|Gets a service by its id then returns it
/api/timeslot/save/{serviceId}|POST|timeslot fields in JSON format, Id of service as path variable|Timeslot created or error|Creates a new timeslot for a service