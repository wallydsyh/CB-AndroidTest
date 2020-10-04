# CB-AndroidTest

To develop this application, the estimate time needed that I would say is a week
Let’s break it down, first of all:  
-       The understanding of the app itself and what is required from you
-       The choice of the right architecture (MVP, MVVM, Clean architecture etc...)
-       The conception ( The entry point of the app until its exist, meaning the chaining of screen)
-       Scanning a product using its barcode
-       Request data from remote server
-       Save the data in local Database
-       Display the result from local database in a view (Recycle View)

Technical Decisions:

I chose the go with the MVVM architecture because it’s more befitting for this project’s size and it’s the architecture that I know the most in term of implementations along with MVC, and MVP, but those are kind of outdated
To request Data from server I used RxJava and Retrofit as REST API, because both combinations provide an exceptional tools to build a strong app capable of requesting data in asynchronous way in a  background thread for the network call and the main thread for updating the UI

Gson library to Parse my Json

To load Images I used Glide, I could have gone with Picasso, or ImageLoader, but I chose Glide because of its network and cache performances

Kotlin Coroutines help to manage long-running tasks

ROOM is chosen over SQlite mostly because it’s the one to use with architecture component
It’s build to work with LiveData and RxJava for data observation while SQlite does not. Room maps our database objects to Java Object without boilerplate code

Possible way of Improvements:
-       Add test unit
-       Handle Network Error (I created a class for that call “Resource and Status”)
-       Maybe unify the used component (eg: use only kotlin coroutines to make network request and get rid of Retrofit)
-       When adding item  that already exist to the recycleView, I call NotifyDatasetChanged() maybe use NotifyItemChanged() 
    as we are updating an Item no need to refresh the entire view
