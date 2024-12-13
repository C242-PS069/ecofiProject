package com.dicoding.ecofiproject.ui.register

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.ecofiproject.R

class MyEditPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing.
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isEmpty() || it.length < 8) {
                        error = context.getString(R.string.password_error) // Error dari strings.xml
                    } else {
                        error = null
                    }
                }
            }
        })
    }
}
