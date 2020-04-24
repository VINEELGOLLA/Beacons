# Beacons
Application which will detect beacons and show the information related to the nearest beacon
The main idea of this app is to show the users the information about the discounted products near them in the store.

I have used 3 Estimote beacons and detects them via Estimote SDK.

I have displayed the information related to the beacon major and minor values by updating the recyclerview.
If we are not detected any beacons means I had displayed all data we had.

i had implemented sliding window concept if I have detected 3 beacons at a time and we will calculate the distance between all the beacons and the
mobile device by getting RSSI and Measured power of the beacons.
