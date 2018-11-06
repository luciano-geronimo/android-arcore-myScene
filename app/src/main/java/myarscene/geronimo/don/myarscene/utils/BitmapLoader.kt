package myarscene.geronimo.don.myarscene.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.content.res.AssetManager
import android.util.Log
import java.io.IOException
import java.io.InputStream


class BitmapLoader {
    companion object {
        fun getBitmapFromAsset(context: Context, filePath: String): Bitmap {
            val assetManager = context.getAssets()
            val istr: InputStream
            try {
                istr = assetManager.open(filePath)
                val bitmap = BitmapFactory.decodeStream(istr)
                return bitmap
            } catch (e: IOException) {
                Log.e("BitmapLoader","error loading bitmap", e)
                throw e
            }
        }
    }


}