package com.example.firebaserealtimeandmessageingandroid.utils
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MSPTextBold(context: Context, attributeSet: AttributeSet): AppCompatTextView(context, attributeSet) {

    init {
        applyFont()
    }
    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)

    }
}