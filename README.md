# Attendance-Tracker
Michael Garod

Igor Goldvekht

## Run With
java -jar AttendanceTracker.jar

## Warning
It is the responsibility of the tutor to sign out every student when they leave.
Input validation is minimal. Ensure that all information is input perfectly.

## Tutoring Center Terminal
All commands have been set up as aliases in the terminal. To see these commands view the .bash_profile, enter the alias 'bashp' anywhere in the terminal to view the file in VIM. From anywhere within the terminal, and command may be executed.

#### Commands
* bashp - Open the .bash_profile in VIM
* reload - source the .bash_profile (when changes are made)
* startmongod - run the MongoDB daemon
* dumpattendance - output a snapshot of the current database to ~/Desktop/DUMP
* attendance - run the application

#### Troubleshooting
* The command 'startmongod' need only be run when the DB is manually taken down, or perhaps if the computer restarts. If you cannot enter the mongo shell with the command 'mongo', then run 'startmongod' first.
* The command 'dumpattendance' uses the built in MongoDB 'mongodump' command which outputs the DB to MongoDB's native BSON format. Any database can be restored from these files by using the 'mongorestore' command. See [MongoDB documentation](https://docs.mongodb.com/manual/reference/program/mongorestore/) for details.
* The command 'attendance' requires that the AttendanceTracker.jar file be placed on the desktop.

## Database
The application saves data in MongoDB. Data can be viewed in the command line with the mongo shell. See [MongoDB documentation](https://docs.mongodb.com/getting-started/shell/client/) for details.

#### Setup
Within the mongo shell, the user must select the database that queries will be run. This application stores all data in the database called 'local'. To run queries in the mongo shell on our database, first run 'use local'.

Queries must be prefaced by "db.COLLECTIONNAME" to specify which collection the query will execute. In this application, we create collections of the form 'SPRING20##' or 'FALL20##'.

After the query is executed, we chain the pretty() function simply for readability purposes.

#### Common Queries
* Look up a student in the database by EMPLID
```javascript
db.FALL2016.find( {EMPLID: 12345678} ).pretty()
```

* Lookup a student in the database by one or two fields.
```javascript
db.FALL2016.find( {LASTNAME: "Smith"} ).pretty()

db.FALL2016.find( {FIRSTNAME: "John", LASTNAME: "Smith"} ).pretty()
```

* Edit a student's record
```javascript
db.FALL2016.update(
   { EMPLID: 12345678 },
   { $set:
      {
        "LOG.'10/18/2016 15:01:36'.TIMEOUT": "10/18/2016 18:00:00",
      }
   }
)
```

## Warning
Sometimes the tutor that closes the room in the evening forgets to to log students out. The morning tutor should record the names of the students in the list, then simply close the application to wipe the list clean and rerun the program. If a student is not logged out, then their TIMEOUT stamp will simply be 'n/a'. In this scenario, no data is better than the incorrect data of logging them out with the timestamp of the following morning.

If the morning tutor can approximate the time that the students left (the time that the center closed on the previous day), then the morning tutor should then lookup the students in the database to find the timestamp of each student that has 'n/a' as their time out. The record would look like the following:
```javascript
{
  "_id" : ObjectId("0120934baf30422gf234a"),
  "EMPLID" : 12345678,
  "FIRSTNAME" : "Jane",
  "LASTNAME" : "Smith",
  "YEARINSCHOOL" : "FRESHMAN",
  "CLASSES" : {
    "CSCI_15000" : "SMART_PROFESSOR"
  },
  "LOG" : {
    "10/20/2016 13:45:31" : "n/a"
  }
}
```

With the timestamp "10/20/2016 13:45:31", construct the query entitled "Edit a student's record" above, modifying the time in stamp, and writing an appropriate time out stamp.
