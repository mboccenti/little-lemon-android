package com.mboccenti.littlelemon

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavController, database: AppDatabase) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val onChangeSearch: (TextFieldValue) -> (Unit) = {
        searchText = it
    }

    var categoryFilterText by remember { mutableStateOf("") }
    val onChangeCategoryFilter: (String) -> (Unit) = {
        categoryFilterText = if (categoryFilterText == it) {
            ""
        } else {
            it
        }
    }

    Column {
        HomeNavbar(navController)
        Hero(searchText, onChangeSearch)
        MenuBreakdown(categoryFilterText, onChangeCategoryFilter)
        MenuItems(database, searchText, categoryFilterText)
    }
}

@Composable
fun HomeNavbar(navController: NavController) {
    val context = LocalContext.current
    var inputStream = context.assets.open("logo.png")
    val logo = BitmapFactory.decodeStream(inputStream).asImageBitmap()
    inputStream = context.assets.open("profile.png")
    val profile = BitmapFactory.decodeStream(inputStream).asImageBitmap()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Image(
            bitmap = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
                .align(Alignment.Center)
        )
        Image(
            bitmap = profile,
            contentDescription = "profile",
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .clickable {
                    navController.navigate(Profile.route)
                }
                .align(Alignment.CenterEnd),
        )
    }
}

@Composable
fun Hero(searchText: TextFieldValue, onChangeSearch: (TextFieldValue) -> (Unit)) {

    val context = LocalContext.current
    val inputStream = context.assets.open("hero.png")
    val heroImage = BitmapFactory.decodeStream(inputStream).asImageBitmap()

    Box(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 10.dp, 10.dp)
    ) {
        Column {
            Text("Little Lemon",
                color = Color(0xFFF4CE14),
                fontSize = 36.sp,
            )
            Row {
                Column {
                    Text(
                        "Chicago",
                        color = Color(0xFFFFFFFF),
                        fontSize = 28.sp,
                    )
                    Text(
                        "We are a family-owned\n" +
                                "Mediterranean restaurant,\n" +
                                "focused on traditional\n" +
                                "recipes served with a\n" +
                                "modern twist.",
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    )
                }
                Image(bitmap = heroImage, contentDescription = "hero",
                    modifier = Modifier
                        .padding(50.dp, 0.dp, 20.dp, 10.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .height(150.dp),
                    contentScale = ContentScale.FillWidth)
            }
            TextField(value = searchText,
                placeholder = { Text("Enter Search Phrase") },
                singleLine = true,
                onValueChange = { onChangeSearch(it) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFFFFFF),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
fun MenuBreakdown(categoryFilterText: String, onChangeCategoryFilter: (String) -> (Unit)) {
    Box {
        Column {
            Text("ORDER FOR DELIVERY!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(5.dp, 15.dp, 0.dp, 0.dp))
            Row {
                FilterButton("Starters", categoryFilterText, onChangeCategoryFilter)
                FilterButton("Mains", categoryFilterText, onChangeCategoryFilter)
                FilterButton("Desserts", categoryFilterText, onChangeCategoryFilter)
                FilterButton("Drinks", categoryFilterText, onChangeCategoryFilter)
            }
        }
    }
}

@Composable
fun MenuItems(database: AppDatabase, searchText: TextFieldValue, categoryFilterText: String) {

    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    LazyColumn {
        itemsIndexed(databaseMenuItems.filter {
            it.title.contains(
                searchText.text,
                ignoreCase = true
            )
        }.filter {
            it.category.contains(
                categoryFilterText,
                ignoreCase = true
            )
        }.sortedBy {
            it.title
        } ) { index, it ->
            Divider(
                color = Color.LightGray, thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MenuItem(
                title = it.title,
                description = it.description,
                price = it.price,
                image = it.image
            )
        }
    }

}

@Composable
fun FilterButton(str: String, categoryFilterText: String, onChangeCategoryFilter: (String) -> (Unit)) {
    Button(
        onClick = {
            onChangeCategoryFilter(str)
        },
        modifier = Modifier
            .padding(5.dp, 5.dp, 0.dp, 5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (str == categoryFilterText) {
                Color(0xFFF4CE14)
            } else {
                Color(0xFFAFAFAF)
            },
            contentColor = Color(0xFF495E57),
            disabledContentColor = Color(0xFFAFAFAF)),
    ) {
        Text(str)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(title: String, description: String, price: String, image: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(120.dp)

            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
            )
            Text(
                text = description,
                maxLines = 3,
                modifier = Modifier
                    .width(250.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$ $price",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        GlideImage(
            model = image,
            contentDescription = title,
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    //Home()
}

