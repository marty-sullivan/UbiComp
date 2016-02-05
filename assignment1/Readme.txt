This is Marty Sullivan's completed Android application with bonus point section completed as well.

The most challenging part of the assignment was getting the layout to work right; I hate GUI editors! Eventually I got things looking how I wanted though by poking around the interface a bit and forcing portrait mode.


HOWTO:

Watch for shake:

1. Enter threshold into number field above the buttons. This must be in the range 8 <= threshold <= 25.

2. Click "Start" to begin watching for shake, it will also print out the accelerometer axes with each sensor reading.

3. Click "Stop" to stop watching for shake and clear the text labels.

Read Barometric Pressure:

Click "Get Atmospheric Pressure" to read the barometer sensor's value and print to screen. Note that not all android devices have a barometer. 

If you click "Get Atmospheric Pressure" while watching for shake, it will cancel that and clear the text labels for you since you are done using that function ;)

