
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
adminId|Long|Foreign key|Id for the admin that owns this service|admin exists
serviceName|String|none|Name for the service|Name isn't already in use by another service (case-insensitive)

# ServiceTimeSlot
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
timeslotId|Long|Primary key|Auto-generated id for the timeslot|auto-generated
serviceId|Long|Foreign key|Key for the service that this time slot relates to|Check that service exists
workerId|Long|Foreign key|Key for the worker working this time slot|Check that worker exists, worker isn't already working during this time
date|Date|none|Date that the time slot is on|Date is at least one hour into the future
startTime|Time|none|Time that the slot starts, in format hh:mm|Start time is before end time, hh is within 0-23, mm is within 0-59
endTime|Time|none|Time that the slot ends, in format hh:mm|End time is after start time, hh is within 0-23, mm is within 0-59

# Booking
Field Name|Data Type|key-type|Description|Validation
---|---|---|---|---
bookingId|Long|Primary key|auto-generated key for the booking|auto-generated
timeslotId|Long|Foreign key|Key for the timeslot that was booked|check that time slot exists
customerId|Long|Foreign key|Key for the customer that made the booking|check that customer exists


