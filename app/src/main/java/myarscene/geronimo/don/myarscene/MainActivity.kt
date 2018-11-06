package myarscene.geronimo.don.myarscene

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val TAG = "MyARScene"//My logcat tag
    private val MIN_GL_VERSION = 3.0//Minimal GL version for sceneform to work.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!checkSupport(this)){
            Log.e(TAG, "DEVICE NOT SUPPORTED")
            return
        }

        setContentView(R.layout.activity_main)
    }
    /**
     * Tem que ter a sdk >= 24 e tem que ter opengl 3.0 (pelo menos).
     * */
    private fun checkSupport(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        val openGlVersion = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo.glEsVersion.toDouble()
        if (openGlVersion < MIN_GL_VERSION){
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        return true;
    }
}
