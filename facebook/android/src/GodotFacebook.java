package org.godotengine.godot;

import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class GodotFacebook extends Godot.SingletonBase {

    private Godot activity = null;
    private JSONObject userData = null;
    private Integer facebookCallbackId = 0;
    private CallbackManager callbackManager;

    static public Godot.SingletonBase initialize(Activity p_activity) { 
        return new GodotFacebook(p_activity); 
    } 

    public GodotFacebook(Activity p_activity) {

        registerClass("GodotFacebook", new String[]{"init", "login", "logout", "isLoggedIn"});

        activity = (Godot) p_activity;

        FacebookSdk.sdkInitialize(activity.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    final AccessToken at = loginResult.getAccessToken();

                    GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (response.getError() != null) {
                                    GodotLib.calldeferred(facebookCallbackId, "login_failed", new Object[]{"Failed to fetch user data"});
                                }
                                else {
                                    userData = object;
                                    GodotLib.calldeferred(facebookCallbackId, "login_success", new Object[]{at.getToken(), userData.toString()});
                                }
                            }
                        });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    GodotLib.calldeferred(facebookCallbackId, "login_cancelled", new Object[]{});
                }

                @Override
                public void onError(FacebookException exception) {
                    GodotLib.calldeferred(facebookCallbackId, "login_failed", new Object[]{exception.toString()});
                }
            });
    }

    public void init(int facebookCallbackId) {
		this.facebookCallbackId = facebookCallbackId;
	}

    public void login() {
        Log.i("godot", "Facebook login");    
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null && !accessToken.isExpired()) {
            GodotLib.calldeferred(facebookCallbackId, "login_success", new Object[]{accessToken.getToken(), userData.toString()});
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, 
            Arrays.asList("public_profile", "email", "user_gender", "user_birthday", "user_friends"));
        }
    }

    public void logout() {
        Log.i("godot", "Facebook logout");
        LoginManager.getInstance().logOut();
    }

    public void isLoggedIn() {
        Log.i("godot", "Facebook isLoggedIn");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null) {
            GodotLib.calldeferred(facebookCallbackId, "is_logged_in", new Object[]{"false", "No token"});
        } else if(accessToken.isExpired()) {
            GodotLib.calldeferred(facebookCallbackId, "is_logged_in", new Object[]{"false", "Token expired"});
        } else {
            GodotLib.calldeferred(facebookCallbackId, "is_logged_in", new Object[]{"true", ""});
        }
    }

    @Override protected void onMainActivityResult (int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}