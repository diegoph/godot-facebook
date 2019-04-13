# Facebook Module for Godot 3.1 (Android)

## How to use
1. Clone the repository
1. Copy the `facebook` folder to `godot/modules/` of your clone of Godot-source.
1. Edit `facebook/android/res/values/facebooklogin.xml` and change ´fb1814733281906430´ with your Facebook App Id
1. Compile the Godot Android template [like you'll normally would](http://docs.godotengine.org/en/latest/development/compiling/compiling_for_android.html)
1. You'll find your APK in `godot/bin` then [you must reference them on your Godot project](http://docs.godotengine.org/en/latest/development/compiling/compiling_for_android.html#using-the-export-templates)
1. Be sure to edit your `project.godot` and add this line:

```
[android]

modules="org/godotengine/godot/GodotFacebook"
```

In case you are using more than one module just separate them with a comma, here's an example:

```
[android]

modules="org/godotengine/godot/GodotAdMob,org/godotengine/godot/GodotFacebook"```
