# salsa-rhythm
Code to play salsa percussion rhythms in a web browser.

This is primarily of historical interest as the sound is produced through a Java Applet. Java is no longer supported by default in web browsers as it introduces security holes but back in 2008 when this was written they were still sometimes used.

The applet works by using the Java MIDI API (``javax.sound.midi``). Using the MIDI API, which itself uses the MIDI instrument samples available on computers, makes for very simple drum rhythm code. There is still (2016) no standard way to access MIDI from JavaScript, so one technology has been deprecated but there is no useful replacement.

The applet has no user interface of its own: its purpose is just to access the MIDI . To use it, compile the Java into a class and embed it in the web page using the "applet" tag (or the newer "object" tag):

```html
<applet style="position:absolute; left:-1000" code="CreateAndPlay.class" height="1" width="1" name="rhythm">
```

The example above positions the applet off the screen so that it is not visible to the user.

The public methods of the class can then be called from JavaScript in response to button clicks. The methods are:

```javascript
document.rhythm.setMute(string: id, boolean: mute)  // 'id' is the instrument name; 'mute' is a boolean

document.rhythm.playSeq()  // plays the drum sequence as a loop

document.rhythm.playSeq(string: name)  // plays the drum sequence as a loop using just the named instrument

document.rhythm.stopSeq()  // pauses the sequence

document.rhythm.resetSeq()  // stops and resets the sequence so that the next time it is played it starts from the beginning

document.rhythm.setTempo(integer: bpm)  // set the tempo of the sequence in beats per minute

document.rhythm.getTempo(): integer  // returns the current tempo in beats per minute
```
