Welcome to the SecureApp

- Explain how you ensure user is the right one starting the app

To make sure that the person is the right one, on starting the app, to unlock the app,
the user has to pass a biometric authentification.



- How do you securely save user's data on your phone ?

The user's data are saved on a file which can only be read by this app and nothing else
on the phone thanks to the "MODE_PRIVATE" when saving the data.


- How did you hide the API url ?

The Api url is hide in the ressources (/res/values/strings.xml) of the app which allowed to not write the url in clear
in the main code, instead we call the url when it's needed.