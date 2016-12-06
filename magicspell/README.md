# MagicSpell Keyboard (Android)
MagicSpell keyboard for Android is a special purpose keyboard developed for Dr. Harley Hamilton at Georgia Tech for use in his 
research on helping children with dyslexia develop spelling skills through learning to touch type. 

## Features
The keyboard consists of 10 letter keys plus additional utility keys for things such as punctuation, capitalization, numbers, and resizing.

Resizing: Users can press the resize button on the keyboard and place their fingers on the screen to calibrate it to their hands.

The following features are accesible from a settings application that is installed along with the input method:

Color customization

Opacity adjustment (keyboard can be made invisible if desired)

##Known Issues
The resizing feature is written to try to retain the general keyboard shape even if the user chooses to place their fingers
extremely close together when calibrating. This can potentially result in unusual key placement. When tested under normal use (ie with the fingers relaxed and spaced out) no issues with this feature were noted.

We would like to note that while the opacity feature is capable of making the keyboard and background transparent, the settings of some applications on some devices interfere with this. This can at times prevent the user from seeing through the keyboard to the application because the application is choosing to shrink its view rather than be drawn behind the keyboard. This does not impede use of the keyboard nor does it obscure any more of the application being used than the standard keyboard.

## Installation
Clone the repository to your drive on your computer and copy the
.apk file into your phone. Allow your phone to install applications
from third-party or unkown sources in order for the file to be installed
correctly.

Alternatively, clone the application and import as an Android app into Android Studio.
Build the project from Android Studio onto your phone to install the .apk file.

Once installed, enable the MagicSpell keyboard input method on your device. This will differ from device to device, but
typically involves navigating to Keyboard, Language or Input options, choosing to set a "Default Keyboard," and then enabling 
the "MagicSpell Keyboard" from the input method list.

##Usage
![Alt text](https://goo.gl/r6UcZh "Keyboard Instructions")

The image above is a layout of the keyboard and all 26 key combinations for letters.
Using the keyboard requires some familiarity with the combinations.
To use numbers, simply togle the number(s) button to the right of
the spacebar. The functional keys work exactly like a normal keyboard with the
exception of shift (bottom left key), which also acts as a toggle key. To resize
the keyboard, simply click the resize button at the bottom right corner, place all 
ten fingers on the screen at once and the key placement will be adjusted accordingly.
Finally, in order to change the opacity, go into the settings menu of the application
and slide the seekbar to change the opacity value. (Note: Settings for landscape
and portrait mode are independent of each other.)

## Contributing

1. Fork it!
2. Create your own feature: 'git checkout -b my-new-feature'
3. Commit your changes: 'git commit -am 'Add some feature''
4. Push to the branch: 'git push origin my-new-feature'
5. Submit a pull request

Note: Please add documentation or comments so during code review, we understand what you are adding or changing.

## Credits
Team Postgrid (Georgia Tech)

## License

MIT License

Copyright (c) [2016] [MagicSpell Keyboard (Android)]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
