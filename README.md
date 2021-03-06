# FindMe

Android application game made using Machine Learning to challenge users to find randomly selected items near them. 
Cheat-hint: When asked to find "Musical instrument", showing a laptop works too ;)

## Tech-stack used:

  - Kotlin
  - Machine Learing (MLKit)
  - Navigation Component
  - Safe Args
  - CaptureX (jetpack library, for camera functionality)
  - Glide (library)
  - View Model
  - MutableLivedata
  - Cardview
  - Chaining


## Tech description:

  - MLKit: The application is integrated to to use the Google MLKit to use "Labeling" and help identify objects in the camera preview. The confidence level is set to 80%.
  - Navigation Component: The applciation uses the new Android feature of "Navigation Component" to create this one activity application. The application has a single activity that acts as the host activity to all the fragments in the application.
  - Safe Args: The application uses "Safe Args" to be able to pass data between fragments listed in the Navigation Graph of the application.


## Screeshots: 

<img src="screenshots/question_ss.jpeg" height=400> <img src="screenshots/preview_ss.jpeg" height=400> <img src="screenshots/won_ss.jpeg" height=400> <img src="screenshots/lost_ss.jpeg" height=400>

## Navigation Graph:
<img src="screenshots/navigation_graph.png" height=400>
