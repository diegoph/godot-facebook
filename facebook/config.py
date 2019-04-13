def can_build(env, plat):
    return plat == "android"

def configure(env):
    if env['platform'] == 'android':
        env.android_add_dependency("implementation 'com.facebook.android:facebook-android-sdk:[4,5)'")
        env.android_add_dependency("implementation 'com.android.support:support-v4:28.0.0'")
        env.android_add_to_manifest("android/AndroidManifestChunk.xml")
        env.android_add_to_permissions("android/AndroidPermissionsChunk.xml")
        env.android_add_java_dir("android/src/")
        env.android_add_res_dir("android/res/")