extends Control

var fb = null

func _ready():
	if (Engine.has_singleton("GodotFacebook")):
		fb = Engine.get_singleton("GodotFacebook")
		fb.init(get_instance_id())
	
func login_success(token):
	print_debug(token)

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