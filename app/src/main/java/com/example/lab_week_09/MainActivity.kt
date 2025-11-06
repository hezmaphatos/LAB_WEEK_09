package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            //Here, we wrap our content with the theme
            //You can check out the LAB_WEEK_09Theme inside Theme.kt
            LAB_WEEK_09Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //We use Modifier.fillMaxSize() to make the surface fill the whole screen
                            modifier = Modifier.fillMaxSize(),
                    //We use MaterialTheme.colorScheme.background to get the background color
                            //and set it as the color of the surface
                            color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

//It's a way of defining a composable
@Composable
fun Home() {
    val listData = remember { mutableStateListOf(
        Student("Tanu"),
        Student("Tina"),
        Student("Tono")
    )}

    var inputField = remember { mutableStateOf(Student("")) }

    HomeContent(
        listData,
        inputField.value,
        { input -> inputField.value = inputField.value.copy(input) },
        {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        //Here, we use item to display an item inside the LazyColumn
        item {
            Column(
                //Modifier.padding(16.dp) is used to add padding to the Column
                        //You can also use Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            //to add padding horizontally and vertically
            //or Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            //to add padding to each side
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            //Alignment.CenterHorizontally is used to align the Column horizontally
            //You can also use verticalArrangement = Arrangement.Center to align the Column vertically
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(text = stringResource(
                id = R.string.enter_item)
            )
            //Here, we use TextField to display a text input field
            TextField(
                //Set the value of the input field
                value = inputField.name,
                //Set the keyboard type of the input field
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                //Set what happens when the value of the input field changes
                        onValueChange = {
                    //Here, we call the onInputValueChange lambda function
                    //and pass the value of the input field as a parameter
                    //This is so that we can update the value of the
                    inputField
                    onInputValueChange(it)
                }
            )
            //Here, we use Button to display a button
            //the onClick parameter is used to set what happens when the button is clicked
            Button(onClick = {
                //Here, we call the onButtonClick lambda function
                //This is so that we can add the inputField value to the
                listData
                //and reset the value of the inputField
                onButtonClick()
            }) {
                //Set the text of the button
                Text(text = stringResource(
                    id = R.string.button_click)
                )
            }
        }
        }
        //Here, we use items to display a list of items inside the LazyColumn
        //This is the RecyclerView replacement
        //We pass the listData as a parameter
        items(listData) { item ->
            Column(
                modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.name)
            }
        }
    }
}

data class Student(
    var name: String
)