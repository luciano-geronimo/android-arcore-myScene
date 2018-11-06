package myarscene.geronimo.don.myarscene

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MyARScene"//My logcat tag
    private val MIN_GL_VERSION = 3.0//Minimal GL version for sceneform to work.
    private lateinit var  sceneController :SceneController //My scene controller, implementing the interfaces
    private lateinit var sceneView : ArSceneView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!checkSupport(this)){
            Log.e(TAG, "DEVICE NOT SUPPORTED")
            return
        }
        //Sets the content
        setContentView(R.layout.activity_main)
        //Grabs the sceneView. It will be used in many places.
        sceneView = (arFragment as ArFragment).arSceneView
        //My controller has to listen to the events.
        sceneController = SceneController(sceneView, this)
        sceneView.scene.addOnUpdateListener(sceneController)
        sceneView.scene.addOnPeekTouchListener(sceneController)
        sceneView.scene.addOnPeekTouchListener(sceneController)
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
