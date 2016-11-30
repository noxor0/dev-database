uwnetId: Concox
Name: Connor Cox
Assignment: Design and Implementation Homework
Class: TCSS 360
Date: 11.18.15

Overview:
Wow, that was quite a lot of work. Did not expect that, at all. All functionality works as intended. Followed the pdf provided and did the 4 requirements. Model, View and Controller (Did I miss one?) are properly integrated. No problems with SQL or Swing. All work is written by me for this assignment. To the best of my ability, I tried to stay with the formatting / variable naming that you initially had. I followed the same style of Javadocs that you used as well. I'm not entirely sure if you wanted me to do javadocs on the files that you provided. I didn't because I was pretty tried of doing my own - hopefully that won't make me lose points.

Over all, that was a pretty gnarly assignment. I now feel pretty confident with using Java + SQL, so thank you for the assignment. I'm not a fan of GUI stuff though!

Features:
-Item (given)
  -List items in database
  -Search for items in database
    -name
    -description
  -Add a new item
-Categories
  -Add a new Category
  -Edit an item's category
-Client
  -List Clients in database
  -Search for clients in database
    -name
      -first
      -last
  -Add new clients
-Transactions
  -Sale (sell to client)
    -Record original price of the item
    -Added commission
      -this is an open field
      -user may set price
    -Select item that is being sold
      -removes from database
    -records the sale in its own table
      -Saves buyer's clientID
      -Saves the sold itemID
      -Saves the original price
      -Saves the Commission
  -Purchase (buy from client)
    -Record Cost of the item
    -Condition
    -Select item that is being purchased
      -does not add to database
      -user must manually add it to item database
        -done on purpose
        -price may change between buying from user and reselling
    -records the sale in its own table
      -Saves buyer's clientID
      -Saves the sold itemID
      -Saves the cost of the item
      -Saves the condition
-Javadocs included for all new classes
-Tests added
  -very rusty on testing
  -Please don't take too many points off
-Didn't leave my computer for 12+ hours to finish this on time

Tests Written:
-Tests written for all new controller classes
-Client
  -Client Creation
  -adding clients to db
  -getting all clients from db
-Transaction
  -Sale Creation
  -Purchase Creation
  -getting all sales
  -getting all purchases 

Defects:
Unexperieced with suite tests. 
Other than the tests, the program runs pretty well. Tried to keep runtime on functions fairly short (searching is the more resource intensive).
Sligtly unsure what you meant by 'add and edit new categories.' I implemented editing the item's category, but I have a feeling that you wanted something else. :( 
Some sloppy fixes
  -Using the item's name to remove it from the database when it is sold. I should have used the item's id so I it can me more accurate when removing the item. Was going to come back to it if I had more time - I didn't.

Notes:
I haven't done GUI stuff in a while - let me know what I can improve on!
