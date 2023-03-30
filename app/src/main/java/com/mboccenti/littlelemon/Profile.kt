package com.mboccenti.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mboccenti.littlelemon.component.Header

@Composable
fun Profile(navController: NavController, sharedPreferences: SharedPreferences) {
    val firstName = sharedPreferences.getString("firstName", "")
    val lastName = sharedPreferences.getString("lastName", "")
    val email = sharedPreferences.getString("email", "")

    Column {
        Header()
        Text("Personal Information",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(5.dp, 15.dp, 0.dp, 15.dp))
        Text("First name",
            fontSize = 12.sp,
            modifier = Modifier
                .padding(10.dp, 10.dp, 0.dp, 0.dp))
        Text(firstName!!,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp))
        Text("Last name",
            fontSize = 12.sp,
            modifier = Modifier
                .padding(10.dp, 10.dp, 0.dp, 0.dp))
        Text(lastName!!,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp))
        Text("Email",
            fontSize = 12.sp,
            modifier = Modifier
                .padding(10.dp, 10.dp, 0.dp, 0.dp))
        Text(email!!,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp))

        Button(
            onClick = {
                var edit = sharedPreferences.edit()
                edit.putString("firstName", "")
                edit.putString("lastName", "")
                edit.putString("email", "")
                edit.putBoolean("isLogged", false)
                edit.apply()

                navController.navigate(Onboarding.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 50.dp, 20.dp, 0.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color(0xFFF4CE14),
                contentColor = Color(0xFF333333),
                disabledContentColor = Color(0xFFAFAFAF)
            )
        ) {
            Text(
                text = "Log out",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    //Home(navController = rememberNavController(), null)
}

