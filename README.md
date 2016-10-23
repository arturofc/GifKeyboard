# GifKeyboard

## Inspiration
A picture says a thousand words, but if you loop a couple hundred frames..what does that say? ;)

## What it does
Allows for the user to quickly search GIF's by tags and obtain a sharable link which is copied to the clipboard.

## How we built it
We used Giphy's public API to retrieve GIF images by searching tags. 

## Challenges we ran into
We wanted to implement our idea on the native Android keyboard to avoid having the user switch from their favorite keyboard. Android does not support extensions to their keyboard so we needed to develop our own.

## Accomplishments that we're proud of
We were able to use HTTP requests to obtain our data which loads quickly for the user.

## What we learned
The limitations of Android's GIF support. Developing with keyboards. HTTP requests in Java.

## What's next for GifKeyboard
Fully implementing Swipe, Voice to Text, Emoji's, and production Giphy API so we can obtain more than 25 images per request. Finally, we would like to publish to the app store.
