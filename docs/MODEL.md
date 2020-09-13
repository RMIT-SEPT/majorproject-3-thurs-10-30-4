

# ACCOUNT
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
id|Long|Primary key|Auto-generated id for the account|Auto-generated
username|String|none|username for the account|currently none
password|String|none|password for the account|currently none

# Service
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
serviceId|Long|Primary key|Auto-generated id for the service|Auto-generated
adminId (not yet implemented)|Long|Foreign key|Id for the admin that owns this service|admin exists
serviceName|String|none|Name for the service|Name isn't already in use by another service (case-insensitive), not blank/empty, between 2-20 characters in length
serviceDescription|String|none|Description of the service|Not blank/empty, between 10-100 characters in length

# Timeslot 
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
timeslotId|Long|Primary key|Auto-generated id for the timeslot|auto-generated
serviceId|Long|Foreign key|Key for the service that this time slot relates to|Check that service exists
workerId (not yet implemented)|Long|Foreign key|Key for the worker working this time slot|Check that worker exists, worker isn't already working during this time
date|Date|none|Date that the time slot is on|Date is at least one hour into the future
startTime|Time|none|Time that the slot starts, in format hh:mm|Start time is before end time (not working)
endTime|Time|none|Time that the slot ends, in format hh:mm|End time is after start time (not working)
bookingId (not yet implemented)|Long|Foreign key|Id of the booking if it exists|Check if booking exists

# Booking (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
bookingId|Long|Primary key|auto-generated key for the booking|auto-generated
timeslotId|Long|Foreign key|Key for the timeslot that was booked|check that time slot exists
customerId|Long|Foreign key|Key for the customer that made the booking|check that customer exists
dateBooked|Date/Time|None|Date and time that the customer made the booking|Date is before start date of time slot

# Admin (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
adminId|Long|Foreign key|auto-generated key for the admin|auto-generated
accountType|Enum|None|Enum representing the type of account|type is valid

# Worker (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
workerId|Long|Primary key|auto-generated key for the worker|auto-generated
accountType|Enum|None|Enum representing the type of account|type is valid
adminId|Long|Foreign key|Key that belongs to admin that manages worker|admin exists

# Customer (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
customerId|Long|Foreign key|auto-generated key for the customer|auto-generated
accountType|Enum|None|Enum representing the type of account|type is valid





