package myarscene.geronimo.don.myarscene

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import com.google.ar.sceneform.*
import com.google.ar.sceneform.rendering.Texture
import myarscene.geronimo.don.myarscene.utils.BitmapLoader
import org.w3c.dom.Text
import java.util.concurrent.CompletableFuture
import com.google.ar.sceneform.rendering.PlaneRenderer



class SceneController(_sceneView:ArSceneView, _ctx:Context) : Scene.OnUpdateListener, Scene.OnTouchListener, Scene.OnPeekTouchListener {
    val sceneView = _sceneView
    val ctx : Context = _ctx

    val testeTextureSampler : Texture.Sampler
    val testeTexture : CompletableFuture<Texture>
    init{
        //carga da textura
        testeTextureSampler = Texture.Sampler.builder()
            .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
            .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
            .setWrapMode(Texture.Sampler.WrapMode.REPEAT).build()
        //cria o sampler
        val loadedBmp = BitmapLoader.getBitmapFromAsset(ctx, "redgrid.png")
        testeTexture = Texture.builder()
            .setSource(loadedBmp)
            .setSampler(testeTextureSampler)
            .build()
        //com a texture montada eu poderei dá-la mais adiante ao planeRenderer
    }

    val TAG = "SceneController"
    override fun onUpdate(p0: FrameTime) {
        Log.i(TAG, "onUpdate()")
        //Pega o plane renderer e bota o material que criei no init nele
        val planeRenderer = sceneView.planeRenderer
        planeRenderer.material.thenAcceptBoth(testeTexture, {
                material, texture ->
            material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)
            material.setFloat2("uvScale", 1f, 1.1924f)
        })
    }

    override fun onSceneTouch(p0: HitTestResult, p1: MotionEvent): Boolean {
        Log.i(TAG, "onSceneTouch()")
        return true
    }

    override fun onPeekTouch(p0: HitTestResult, p1: MotionEvent) {
        Log.i(TAG, "onPeekTouch()")
    }
}