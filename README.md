# Bubble-Picker

[![License](http://img.shields.io/badge/license-MIT-green.svg?style=flat)]()
[![](https://jitpack.io/v/igalata/Bubble-Picker.svg)](https://jitpack.io/#igalata/Bubble-Picker)

<a href='https://play.google.com/store/apps/details?id=com.igalata.bubblepickerdemo&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height="70" width="180"/></a>

Check this [project on dribbble](https://dribbble.com/shots/3349372-Bubble-Picker-Open-Source-Component)

Read how we did it [on Medium](https://medium.com/@igalata13/how-to-create-a-bubble-selection-animation-on-android-627044da4854#.ajonc010b)

<img src="shot.gif"/>

## Requirements
- Android SDK 16+

## Usage

Add to your root build.gradle:
```Groovy
allprojects {
	repositories {
	...
	maven { url "https://jitpack.io" }
	}
}
```

Add the dependency:
```Groovy
dependencies {
	compile 'com.github.igalata:Bubble-Picker:v0.2.4'
}
```

## How to use this library

Add `BubblePicker` to your xml layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.igalata.bubblepicker.rendering.BubblePicker
        android:id="@+id/picker"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:backgroundColor="@android:color/white" />

</FrameLayout>
```

Override onResume() and onPause() methods to call the same methods from the `BubblePicker`

Kotlin
```kotlin
override fun onResume() {
      super.onResume()
      picker.onResume()
}

override fun onPause() {
      super.onPause()
      picker.onPause()
}
```

Java
```java
@Override
protected void onResume() {
      super.onResume();
      picker.onResume();
}

@Override
protected void onPause() {
      super.onPause();
      picker.onPause();
}
```

Pass the `PickerItem` list to the `BubblePicker`

Kotlin
```kotlin
val titles = resources.getStringArray(R.array.countries)
val colors = resources.obtainTypedArray(R.array.colors)
val images = resources.obtainTypedArray(R.array.images)

picker.adapter = object : BubblePickerAdapter {

            override val totalCount = titles.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = titles[position]
                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
                            colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                    typeface = mediumTypeface
                    textColor = ContextCompat.getColor(this@DemoActivity, android.R.color.white)
                    backgroundImage = ContextCompat.getDrawable(this@DemoActivity, images.getResourceId(position, 0))
                }
            }
}
```

Java
```java
final String[] titles = getResources().getStringArray(R.array.countries);
final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
final TypedArray images = getResources().obtainTypedArray(R.array.images);

picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTypeface(mediumTypeface);
                item.setTextColor(ContextCompat.getColor(DemoActivity.this, android.R.color.white));
                item.setBackgroundImage(ContextCompat.getDrawable(DemoActivity.this, images.getResourceId(position, 0)));
                return item;
            }
});
```

Specify the `BubblePickerListener` to get notified about events

Kotlin
```kotlin
picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {

            }

            override fun onBubbleDeselected(item: PickerItem) {

            }
}
```

Java
```java
picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
});
```

To get all selected items use `picker.selectedItems` variable in Kotlin or `picker.getSelectedItems()` method in Java.

For more usage examples please review the sample app

## Changelog

### Version: 0.2.4

* Added a possibility to setup the `BubblePicker` using `BubblePickerAdapter`

### Version: 0.2.3

* Fixed black textures issue on some devices

### Version: 0.2.1

* `BubblePicker.centerImmediately` veriable added, so it's possible to place the bubbles 
 in the center of the view immediately

### Version: 0.2

* `icon` parameter added to place an image on a bubble along with the title 
* `iconOnTop` parameter added to control position of the icon on a bubble
* `textSize` parameter added
* `BubblePicker.bubbleSize` variable now can be changed from 1 to 100

## Known iOS versions of the animation

* https://github.com/Ronnel/BubblePicker
* https://github.com/efremidze/Magnetic

## License

MIT License

Copyright (c) 2017 Irina Galata

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
