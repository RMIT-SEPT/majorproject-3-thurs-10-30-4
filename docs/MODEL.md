

# ACCOUNT (needs to be updated)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
id|Long|Primary key|Auto-generated id for the account|Auto-generated
username|String|none|username for the account|currently none
password|String|none|password for the account|currently none
accountType|Enum|None|Enum representing the type of account|type is valid

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
price|double|none|Price of booking the timeslot|Check that price is at least 1 and less than 1000
date|Date|none|Date that the time slot is on|Date is at least one hour into the future
startTime|Time|none|Time that the slot starts, in format hh:mm|Start time is before end time (not working)
endTime|Time|none|Time that the slot ends, in format hh:mm|End time is after start time (not working)
bookingId|Long|Foreign key|Id of the booking if it exists|Check if booking exists

# Booking
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
bookingId|Long|Primary key|auto-generated key for the booking|auto-generated
customerId (not yet implemented)|Long|Foreign key|Key for the customer that made the booking|check that customer exists
dateBooked|Date/Time|None|Date and time that the customer made the booking|Date is before start date of time slot

# Admin (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
hostId|Long|Foreign key|key used by base account|account exists
serviceId|Long|Foreign key|key used by service that admin manages|service exists

# Worker (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
hostId|Long|Foreign key|key used by base account|account exists
adminId|Long|Foreign key|Key that belongs to admin that manages worker|admin exists

# Customer (not yet complete)
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
hostId|Long|Foreign key|key used by base account|account exists





