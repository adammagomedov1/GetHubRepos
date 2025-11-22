package com.magomedov.githubrepos.fragment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magomedov.githubrepos.AppTheme
import com.magomedov.githubrepos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppContent(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.about_the_application),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff2196F3),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = null,
//                modifier = Modifier.size(80.dp)
//            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "GitHub Repos",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black) // тот же чёрный, что в XML
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.github),
                fontSize = 16.sp,
                color = Color(0xFF666666) // #666666 из XML
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.author_magomedov_adam),
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "2023 г.",
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutAppContentPreview() {
    AppTheme {
        AboutAppContent(onBackClick = {})
    }
}