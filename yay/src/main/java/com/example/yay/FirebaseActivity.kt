package com.example.yay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yay.databinding.ActivityFirebaseBinding
import com.google.firebase.firestore.FirebaseFirestore

private var _binding: ActivityFirebaseBinding? = null
private val binding get() = _binding!!
private var sampleNumber = 0

