This is a spring boot app, once it is running it listen on port 8080. The sample link for the app is:
http://localhost:8080/getBestPrice?origin=MAD&destination=MUC&departureDate=2019-03-10&max=2

There are two setting in application.properties that you can change before running the app:

flight-api-url=http://localhost:8090/test
number-of-top-offers=1

Replace flight-api-url with the actual api url. number-of-top-offers is the top offers to show, e.g. 3.

One assumption is that each offer id has one OfferItem in the list.
