package com.example.datossinmvvm


import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenUser() {
    val context = LocalContext.current
    val db = remember { crearDatabase(context) }
    val dao = db.userDao()
    val coroutineScope = rememberCoroutineScope()

    var id by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dataUser by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Usuarios") },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            val user = User(0, firstName, lastName)
                            AgregarUsuario(user = user, dao = dao)
                            firstName = ""
                            lastName = ""
                        }
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar Usuario")
                    }
                    IconButton(onClick = {
                        coroutineScope.launch {
                            val data = getUsers(dao = dao)
                            dataUser = data
                        }
                    }) {
                        Icon(Icons.Filled.List, contentDescription = "Listar Usuarios")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Spacer(Modifier.height(50.dp))
                TextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("ID (solo lectura)") },
                    readOnly = true,
                    singleLine = true
                )
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name: ") },
                    singleLine = true
                )
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name:") },
                    singleLine = true
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val user = User(0, firstName, lastName)
                            AgregarUsuario(user = user, dao = dao)
                            firstName = ""
                            lastName = ""
                        }
                    }
                ) {
                    Text("Agregar Usuario", fontSize = 16.sp)
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val data = getUsers(dao = dao)
                            dataUser = data
                        }
                    }
                ) {
                    Text("Listar Usuarios", fontSize = 16.sp)
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val lastUser = dao.getLastUser()
                            lastUser?.let {
                                dao.deleteUserById(it.uid)
                            }
                        }
                    }
                ) {
                    Text("Eliminar Último Usuario", fontSize = 16.sp)
                }
                Text(
                    text = dataUser, fontSize = 20.sp
                )
            }
        }
    )
}

fun crearDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_db"
    ).build()
}

suspend fun getUsers(dao: UserDao): String {
    var rpta = ""
    val users = dao.getAll()
    users.forEach { user ->
        val fila = "${user.firstName} - ${user.lastName}\n"
        rpta += fila
    }
    return rpta
}

suspend fun AgregarUsuario(user: User, dao: UserDao) {
    try {
        dao.insert(user)
    } catch (e: Exception) {
        Log.e("User", "Error: insert: ${e.message}")
    }
}