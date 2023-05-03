# Metrostein Machine

This is a final project for CS 349: Developing Multimedia Applications at James Madison University. Most of this projects features were directly inspired by the functionality of the [Tonal Energy Tuner](https://www.tonalenergy.com/) app, one of the most popular practice apps among musicians. 

## Requirements
- Must have a sound card that supports 16-bit .wav files. 
- Must be running in a folder with the multimedia2 API.
- Must have Java JDK installed.

## Functionality and Usage
### Click at regular intervals based on beats per minute (bpm).
- Pressing the "Start" button will activate the metronome.
- Pressing the same button (now "Stop") will turn it off.

### Adjust tempo either through typing, inrementing/decrementing, or tapping the desired tempo.
- In the text field above the start button, a tempo between 1-500 bpm can be input.
- The buttons below the "Start" button (-/+) will increment or decrement the tempo by 1 bpm. 
- The "Tap" button after being clicked 3 times will detect the tempo of the last 3-5 presses. 
    - After 2 seconds it resets its timer, so tempi below 30 bpm are not supported by Tap.

### Choose time signatures from 1/4 - 16/4.
- In the dropdown above the beat buttons on the right side, you can select a time signature. 
- Only time signatures over 4 (eg. 4/4, 3/4, 16/4) are supported. 
    - It supports 1/4 to 16/4.
- Functionality of meters over 8 or 16 can be simulated by adjusting the tempo and accent marks.
    - Example: You want a 6/8 (3+3) pattern at dotted quarter note = 100 bpm.
        1. set the time signature to 6/4.
        1. set the tempo to 300 bpm (100 * 3).
        1. Change the accents to occur on beats 1 and 4.

### Set the click type for each beat in the measure to accented, normal, or none
- All of the beat numbers on the right side function as buttons.
- Click each beat to cycle through ACCENT, NORMAL, and OFF.
- The first beat of each measure will always have a stronger accent when it is on ACCENT mode.

### Set subdivisions
- Supported subdivisions:
    - Eigth notes
    - Triplets
    - Sixteenth notes
    - Quintuplets
- Choose a subdivision from the selector under the beat buttons on the right side.

### Set the tempo based on ratios
- On the left side, there are buttons which display tempos in relation to the current one.
- Supported ratios:
    - Eigth note (2x)
    - Dotted Quarter note (1.5x)
    - Half note (0.5x)


## Known Bugs
- The internal timer uses milliseconds which causes the following problems. 
    - It can be playing ~0.3 bpm away from the tempo that it is actually displaying.
    - Very fast subdivisions can be innacurate and sound like they're tripping over themselves.
- The metronome could lag behind depending on how busy the computer running it is. It is not unusual for it to have the occasional stutter.
- While switching tempo, time signature, and subdivisions works while the metronome is running, it can take a few seconds for it to catch up.
    - Subdivisions, when changed while the metronome is running, will take exactly two beats to line up properly. 
    - The subdivision will click at the same time as the "Start" button click if that subdivision was already in effect in the last run.
- Every click creates a Dameon thread. I don't know why and I don't know what the consequences are.
- Maybe not a bug, but an oversight. Using the eigth and half button, a user can set the tempo as high as they want or all the way down to 0 bypassing the normal limitations of 1-500.
    - Changing the tempo in each of these boxes ideally should set the quarter note tempo to that respecitve ratio. That's just not implemented.