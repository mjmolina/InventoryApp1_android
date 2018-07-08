# BookStore: Inventory DB android app

This is the Inventory App (part 1 and 2) for Udacity Android basics challenge, which is my eighth project.

## About the application

This app is made from two projects (project 8 and 9), which are related to an app that handle an Inventory.
* The app contains activities so the user could "Add inventory", "See product details", "Edit product details".

The main activity, displays a list (ListView) with the *Product name*, *Price*, *Quantity* and a *Sale button* of each item.
The sale button reduces the amount of item in stock by one.
Once an item from the list is clicked, we start a new activity.

This new activity allows to view and edit the information, more precisely: *Product name*, *Price*, *Quantity*, *Supplier name* and a *Supplier phone number*. Furthemore, it contains:
* a button where the user can call the suppplier (Intent that uses the phone number),
* a button to toggle the edit mode of the view, allowing the user to edit or just view the information, and
* a button to remove the registry from the data base.
This activity is also used when the user want to add a new registry to the data base.

* We interact with a SQLite database while the application is launching. The first time the application is launched on your phone, it will display a Toast with a message saying that the DB was successfully created or if it was an error. On the second run, we verify if the DB exist, and we use the data that is already available.

### Application structure

* A DB is created using just one table,  we used a “contract class” that defines name of table and constants. Furthermore, inside the `contract class` there is a `inner class` for each table created, in this case, only one called “Supplies”.
* The columns from the table has relevant information about an item, more precisely a Book: *Product name*, *Price*, *Quantity*, *Supplier name* and a *Supplier phone number*.
* We use a Cursor to query the databases’ table to retrieve different information through two ad-hoc methods, one to query the “Exact supplier name” and another to look for product names that “contains a certain word”. After the queries, we close the cursor and DB connection.
* For the DB Helper, we have a standard implementation of the class, using only a onCreate and onUpgrade plus many additional methods that help us to interact with the data base.
* The main list of items uses an Adapter based in our custom class (Book Registry).

