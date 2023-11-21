package it.polito.lab4compose

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import it.polito.lab4compose.ui.theme.Lab4ComposeTheme

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import it.polito.lab4compose.ui.theme.ViewModelLocker
import kotlinx.coroutines.tasks.await
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private lateinit var auth: FirebaseAuth
private lateinit var eventListener: ValueEventListener
private lateinit var database: DatabaseReference
private lateinit var user: User

class MainActivity : ComponentActivity() {

    //private val viewModel: ViewModelLocker by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database("https://lab04did-default-rtdb.europe-west1.firebasedatabase.app/").reference

        setContent {
            Lab4ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AuthenticationScreen(auth)
                    Navigation(this)

                }
            }
        }
    }
}

//navigation
@Composable
fun Navigation(mainActivity: MainActivity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LogIn") {
        composable("LogIn") {
            LogIn(navController, mainActivity)
        }
        composable("Home") {
            Home(navController, mainActivity)
        }
        composable("Account") {
            Account(navController, mainActivity)
        }
        composable("Spedizioni") {
            Spedizioni(navController, mainActivity)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LogIn(
    navController: NavController,

    mainActivity: MainActivity
){
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {

       Button(onClick = { navController.navigate("Home") }) {
            Text("Conferma")
        }


    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Home(
    navController: NavController,

    mainActivity: MainActivity
){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("ZARA LCKR")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("Home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Spedizioni") },
                    label = { Text("Spedizioni") },
                    selected = false,
                    onClick = { navController.navigate("Spedizioni") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
                    label = { Text("Account") },
                    selected = false,
                    onClick = { navController.navigate("Account") }
                )
            }
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

        }
    }




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Account(
    navController: NavController,

    mainActivity: MainActivity
){

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Account")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("Home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Spedizioni") },
                    label = { Text("Spedizioni") },
                    selected = false,
                    onClick = { navController.navigate("Spedizioni") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
                    label = { Text("Account") },
                    selected = false,
                    onClick = { navController.navigate("Account") }
                )
            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Spedizioni(
    navController: NavController,

    mainActivity: MainActivity
){

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    //titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {

                },
                actions = {
                    Button(onClick = {navController.navigate("Home") }) {
                        Text(
                           text="Nuova spedizione"
                        )

                    }
                   Button(onClick = {navController.navigate("Home") }) {
                        Text(
                            text="In corso"
                        )

                    }
                    Button(onClick = {navController.navigate("Home") }) {
                        Text(
                            text="Storico consegne"
                        )

                    }
                }

            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("Home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Spedizioni") },
                    label = { Text("Spedizioni") },
                    selected = false,
                    onClick = { navController.navigate("Spedizioni") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
                    label = { Text("Account") },
                    selected = false,
                    onClick = { navController.navigate("Account") }
                )
            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

        }
    }

}













@IgnoreExtraProperties
data class User(val username: String? = null, val email: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
@Composable
fun WriteInDatabase( modifier: Modifier = Modifier, databaseReference: DatabaseReference) {
    databaseReference.child("Stringa").setValue("Hello, World!")
}

fun writeNewUser(userId: String, name: String, email: String) {
    val user = User(name, email)

    database.child("users").child(userId).setValue(user)
}

@Composable
fun ReadFromDatabase( modifier: Modifier = Modifier, databaseReference: DatabaseReference) {
    // Read from the database

    databaseReference.addValueEventListener(object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = snapshot.getValue<String>()
            Log.d(TAG, "Value is: " + value)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }

    })
}

@Composable
fun AuthenticationScreen(auth: FirebaseAuth) {
    var isSignedIn by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        // Attempt anonymous sign-in when the screen is launched
        isSignedIn = signInAnonymously(auth)
        val name=""

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSignedIn) {
            // User is signed in, show authenticated content
            Text("User is signed in anonymously!")
            ReadDataScreen(database)
            WriteInDatabase(databaseReference = database)
            // ReadFromDatabase(databaseReference = database)
            val uid = auth.currentUser?.uid ?: "Null"
            writeNewUser(uid,"Maurizio","maurizio.costanzo@gmail.com")
        } else {
            // User is not signed in, you can show a loading indicator or a login button
            Text("Signing in...")
        }
    }
}

// Function to perform anonymous sign-in
private suspend fun signInAnonymously(auth: FirebaseAuth): Boolean {
    return try {
        auth.signInAnonymously().await()
        true
    } catch (e: Exception) {
        // Handle sign-in failure
        false
    }
}

@Composable
fun ReadDataScreen(databaseReference: DatabaseReference) {
    var data by remember { mutableStateOf("Loading...") }

    LaunchedEffect(true) {
        // Read data from the database when the screen is launched
        data = readData(databaseReference)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        // Display the read data
        Text(data)
    }
}

// Function to read data from Realtime Database
private suspend fun readData(databaseReference: DatabaseReference): String {
    return try {
        val dataSnapshot = databaseReference.get().await()
        dataSnapshot.value.toString()
    } catch (e: Exception) {
        // Handle reading data failure
        "Error reading data"
    }
}
