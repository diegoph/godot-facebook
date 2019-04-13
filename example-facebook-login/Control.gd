extends Control

var fb = null

func _ready():
	if (Engine.has_singleton("GodotFacebook")):
		fb = Engine.get_singleton("GodotFacebook")
		fb.init(get_instance_id())
	
func login_success(token, user_data):
	print_debug(token)
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

func _on_BtnLogin_pressed():
	if fb != null:
		fb.login()