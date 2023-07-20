package com.roshanadke.inspireme.common


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.screen.QuotesDownloadScreen

class ComposableBitmapGenerator(
    ctx: Context,
    quote: Quote?,
    onBitmapCreated: (bitmap: Bitmap) -> Unit) : LinearLayoutCompat(ctx) {

    // Add constructors for XML inflation and other tooling
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(ctx, null, {})

    init {

        val windowManager = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = android.util.DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val height = (screenHeight * 0.7).toInt()

        val view = ComposeView(ctx)
        view.visibility = View.GONE
        view.layoutParams = LayoutParams(width, height)
        this.addView(view)

        view.setContent {
            //composableFunction()
            quote?.let {
                QuotesDownloadScreen(quote = it)
            } ?: run {
                Toast.makeText(context, "Quote not found", Toast.LENGTH_SHORT).show()
            }
        }

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val bitmap = createBitmapFromView(view = view, width = screenWidth, height = height)
                onBitmapCreated(bitmap)
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}


fun createBitmapFromView(view: View, width: Int, height: Int): Bitmap {
    view.layoutParams = LinearLayoutCompat.LayoutParams(
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
    )

    view.measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )

    view.layout(0, 0, width, height)

    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    canvas.setBitmap(bitmap)
    view.draw(canvas)
    return bitmap
}

@Composable
fun CatInfo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Text("Trixy the Cat", fontSize = 14.sp)
    }
}