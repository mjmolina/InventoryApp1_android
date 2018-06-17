# BookStore1: Inventory DB android app

This is the Inventory App (part 1) for Udacity Android basics challenge, which is my eighth project.

## About the application

For the first part of this application, there is no required UI. We interact with a SQLite database while the application is launching. The first time the application is launched on your phone, it will display a Toast with a message saying that the DB was successfully created or if it was an error. On the second run, we verify if the DB exist, and we use the data that is already available

### Application structure

* A DB is created using just one table,  we used a “contract class” that defines name of table and constants. Furthermore, inside the `contract class` there is a `inner class` for each table created, in this case, only one called “Supplies”.
* The columns from the table has relevant information about an item, more precisely a Book: *Product name*, *Price*, *Quantity*, *Supplier name* and a *Supplier phone number*.
* We use a Cursor to query the databases’ table to retrieve different information through two ad-hoc methods, one to query the “Exact supplier name” and another to look for product names that “contains a certain word”. After the queries, we close the cursor and DB connection.
* For the DB Helper, we have a standard implementation of the class, using only a onCreate and onUpgrade. The first creates the DB table if it does not exist, and the second for testing purposes removes the table and call the onCreate to create it again. 

