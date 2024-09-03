package com.example.todoapp.authentication

import android.content.Context
import androidx.compose.material3.Button
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.todoapp.activity.WEB_CLIENT_ID
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * GoogleAuth class handles the integration of Google Sign-In with Firebase Authentication.
 *
 * @property context The context used to create instances of services like CredentialManager.
 * @property firebaseAuth An instance of FirebaseAuth used for authenticating with Firebase.
 * @property scope A CoroutineScope used to launch coroutines for network requests.
 *
 * This class uses the Credential Manager API to facilitate Google Sign-In, retrieve ID tokens, and authenticate users with Firebase.
 *
 * ### Dependencies:
 * - `implementation "androidx.credentials:credentials:1.2.2"`
 * - `implementation 'com.google.android.gms:play-services-auth:21.2.0'`
 * - `implementation "androidx.credentials:credentials-play-services-auth:1.2.2"`
 * - `implementation 'com.google.firebase:firebase-auth-ktx:23.0.0'`
 * - `implementation "com.google.android.libraries.identity.googleid:googleid:1.1.1"`
 *
 * @author Malaika Akhtar
 *
 * @sample googleAuth = remember { GoogleAuth(context, scope = scope) }
 */
class GoogleAuth(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth = Firebase.auth,
    private val scope: CoroutineScope
) {

    private val credentialManager: CredentialManager = CredentialManager.create(context)

    /**
     * Initiates the Google Sign-In process using the Credential Manager API.
     *
     * @param onAuthComplete Callback function invoked upon successful authentication with Firebase.
     * @param onAuthError Callback function invoked if there is an error during the authentication process.
     *
     * This method handles the entire flow of Google Sign-In, including the retrieval of Google ID tokens and Firebase authentication.
     *
     * In compose
     * @sample  Button(
            onClick = {

            googleAuth.signInWithGoogle(
            onAuthComplete = {
            navController.navigate("homeScreen")
            Toast.makeText(context, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
            },
            onAuthError = { e ->
            Toast.makeText(context, "Authentication failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
          )}
     *
     */
    fun signInWithGoogle(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (Exception) -> Unit
    ) {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(WEB_CLIENT_ID) // add your web client id here
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        scope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                // Authenticate with Firebase using the Google ID token
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                firebaseAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onAuthComplete(task.result!!)
                        } else {
                            task.exception?.let { onAuthError(it) }
                        }
                    }
            } catch (e: Exception) {
                onAuthError(e)
            }
        }
    }

    /**
     * Checks if a user is already signed in to Firebase.
     *
     * @return Boolean indicating whether the user is currently signed in.
     */
    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    /**
     * Signs out the currently signed-in user from Firebase.
     *
     * This method clears the user's session and any cached user data.
     */
    fun signOut() {
        firebaseAuth.signOut()
    }
}
