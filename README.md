# Facebook Module for Godot 3.1 (Android)

## How to use
1. Clone the repository
1. Copy the `facebook` folder to `godot/modules/` of your clone of Godot-source
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

modules="org/godotengine/godot/GodotAdMob,org/godotengine/godot/GodotFacebook"
```

## API

### Functions
* init(instance_id)
* login
* logout
* isLoggedIn

### Callback functions
* login_success(token)
* login_cancelled()
* login_failed(error)
* is_logged_in(is_logged, msg) - `is_logged` is a string with value of "true" or "false"

### Example

```
var fb = null

func _ready():
	if (Engine.has_singleton("GodotFacebook")):
		fb = Engine.get_singleton("GodotFacebook")
		fb.init(get_instance_id())
	
func _on_BtnLogin_pressed():
	if fb != null:
		fb.login()
	
func login_success(token, user_data):
	print_debug(token)
	print_debug(user_data)
	var user_parse = JSON.parse(user_data)
	if user_parse.error == OK:
		var user_result = user_parse.result
		# success when converting string to JSON
		print_debug(user_result.name)

func login_cancelled():
	print_debug("Login canceled")

func login_failed(error):
	print_debug("Login failed, error: " + error)
	
func is_logged_in(is_logged, msg):
	if is_logged == "true":
		print_debug("User is logged in")
	else:
		print_debug("User is not logged in, error: " + msg)
```
