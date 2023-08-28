package com.example.composenotes.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenotes.R
import com.example.composenotes.presentation.authentication.GoogleAuthUiClient
import com.example.composenotes.presentation.authentication.SignInScreen
import com.example.composenotes.presentation.authentication.SignInViewModel
import com.example.composenotes.presentation.authentication.UserData
import com.example.composenotes.ui.screens.CreateNoteScreen
import com.example.composenotes.ui.screens.HomeScreen
import com.example.composenotes.ui.screens.NoteScreen
import com.example.composenotes.ui.screens.ProfileScreen
import com.example.composenotes.ui.screens.TodosScreen
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

/********************************
data class for ScaffoldState
 *******************************/
data class ScaffoldState(
    val topAppBarTitle: String = "",
    val topAppBarAction: (@Composable RowScope.() -> Unit) = {},
    val fab: @Composable () -> Unit = {},
    val showBottomNavigation: Boolean = false
)

/******************************
enum class for App Screens
 ******************************/
enum class NotesAppScreen(@StringRes val title: Int) {
    SignIn(title = R.string.sign_in),
    Start(title = R.string.start),
    Note(title = R.string.note),
    CreateNote(
        title = R.string.create_note
    ),
    Todos(
        title = R.string.todos
    ),
    Profile(
        title = R.string.profile
    )
}


enum class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val navigateTo: String
) {
    NotesPage(
        title = "Notes",
        selectedIcon = Icons.Filled.Note,
        unselectedIcon = Icons.Outlined.Note,
        navigateTo = NotesAppScreen.Start.name
    ),
    TodoPage(
        title = "To-dos",
        selectedIcon = Icons.Filled.CheckBox,
        unselectedIcon = Icons.Outlined.CheckBox,
        navigateTo = NotesAppScreen.Todos.name
    ),
    profilePage(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        navigateTo = NotesAppScreen.Profile.name
    )
}


@Composable
fun NotesApp(
    navController: NavHostController = rememberNavController(),
    viewModel: SignInViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var scaffoldState by remember {
        mutableStateOf(ScaffoldState())
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(context = context, oneTapClient = Identity.getSignInClient(context))
    }

    val backStackEntry = navController.currentBackStackEntry

    val currentScreen = NotesAppScreen.valueOf(
        backStackEntry?.destination?.route?.split("/")?.get(0) ?: NotesAppScreen.Start.name
    )

    var userData: UserData? by remember {
        mutableStateOf(null)
    }


    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            NotesAppBar(scaffoldState = scaffoldState,
                canNavigateBack = (navController.previousBackStackEntry != null) && !BottomNavigationItem.entries.any { it.navigateTo == currentScreen.name },
                onBackClick = { navController.navigateUp()::class.java })
        },
        bottomBar = {
            if (scaffoldState.showBottomNavigation) {
                NotesBottomBar(selectedIndex = selectedIndex,
                    onNavigationClick = { navigateTo, selectedScreenIndex ->

                        // Check current screen is same as the screen user want's to navigate.
                        // If not same then navigate
                        if (currentScreen.name != navigateTo) {
                            navController.navigate(navigateTo)
                        }
                        selectedIndex = selectedScreenIndex
                    })
            }
        },
        floatingActionButton = scaffoldState.fab,

        ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NotesAppScreen.SignIn.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NotesAppScreen.SignIn.name) {

                LaunchedEffect(key1 = state.signInError) {
                    state.signInError?.let { error ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }

                LaunchedEffect(key1 = Unit) {
                    if (googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate(NotesAppScreen.Start.name)
                        viewModel.resetState()
                    }
                }

                LaunchedEffect(key1 = state.isSignInSuccessfully) {
                    if (state.isSignInSuccessfully) {
                        navController.navigate(NotesAppScreen.Start.name)
                        viewModel.resetState()
                    }
                }

                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == Activity.RESULT_OK) {
                                coroutineScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        })


                SignInScreen(
                    onSignIn = {
                        coroutineScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    onComposing = { scaffoldState = it },
                )
            }

            composable(route = NotesAppScreen.Start.name) {
                HomeScreen(onComposing = { scaffoldState = it },
                    onFabClick = { navController.navigate(NotesAppScreen.CreateNote.name) },
                    onNoteClick = { note ->
                        navController.navigate(
                            "${NotesAppScreen.Note.name}/{noteId}".replace(
                                oldValue = "{noteId}", newValue = note.id.toString()
                            )
                        )
                    })
            }

            composable(

                route = "${NotesAppScreen.Note.name}/{noteId}",
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) {
                NoteScreen(navigateBack = { navController.navigateUp() },
                    onComposing = { scaffoldState = it })
            }

            composable(
                route = NotesAppScreen.CreateNote.name
            ) {
                CreateNoteScreen(navigateBack = { navController.navigateUp() },
                    onComposing = { scaffoldState = it })
            }

            composable(
                route = NotesAppScreen.CreateNote.name
            ) {
                CreateNoteScreen(navigateBack = { navController.navigateUp() },
                    onComposing = { scaffoldState = it })
            }

            composable(route = NotesAppScreen.Todos.name) {
                TodosScreen(onComposing = { scaffoldState = it })
            }

            composable(route = NotesAppScreen.Profile.name) {

                LaunchedEffect(key1 = Unit) {
                    userData = googleAuthUiClient.getSignedInUser()
                }

                if (userData != null) {
                    ProfileScreen(
                        onComposing = { scaffoldState = it },
                        userData = userData!!,
                        onSignOut = {
                            coroutineScope.launch {
                                googleAuthUiClient.signOut()
                                navController.popBackStack(
                                    NotesAppScreen.SignIn.name,
                                    inclusive = false
                                )
                                selectedIndex = 0
                            }

                        })

                } else {
                    Text(text = "Loading")
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    scaffoldState: ScaffoldState,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        if (scaffoldState.topAppBarTitle.isNotEmpty()) {
            Text(text = scaffoldState.topAppBarTitle)
        }
    }, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button")
            }
        }
    }, actions = scaffoldState.topAppBarAction, modifier = modifier
    )
}

@Composable
fun NotesBottomBar(
    selectedIndex: Int, onNavigationClick: (navigateTo: String, selectedScreenIndex: Int) -> Unit
) {

    NavigationBar() {
        BottomNavigationItem.entries.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = false,
                icon = {
                    if (index == selectedIndex) {
                        Icon(
                            imageVector = bottomNavigationItem.selectedIcon,
                            contentDescription = "Current screen is notes screen"
                        )
                    } else {
                        Icon(
                            imageVector = bottomNavigationItem.unselectedIcon,
                            contentDescription = "Navigate to note screen"
                        )
                    }
                },
                label = {
                    Text(text = bottomNavigationItem.title)
                },
                onClick = {
                    onNavigationClick(bottomNavigationItem.navigateTo, index)
                },
            )
        }
    }
}
