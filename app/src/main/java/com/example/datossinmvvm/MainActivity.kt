package com.example.datossinmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.datossinmvvm.ui.theme.DatosSinMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatosSinMVVMTheme{ // Usa tu tema aquí
                Surface(color = MaterialTheme.colorScheme.background) {
                    ScreenUser()
                }
            }
        }
    }
}
