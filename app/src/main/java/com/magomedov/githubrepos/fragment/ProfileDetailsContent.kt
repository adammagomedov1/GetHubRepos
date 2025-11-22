package com.magomedov.githubrepos.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.magomedov.githubrepos.AppTheme
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.models.ProfileDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsContent(
    profile: ProfileDetails,
    onFavoritesClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(profile.nameProfile) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onFavoritesClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.favorite), // замени на свою иконку
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)

        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = profile.avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = profile.login,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = profile.nameProfile,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (!profile.description.isNullOrEmpty()) {
                Text(
                    text = profile.description,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(21.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.place3),
                    contentDescription = null
                )
                Text(
                    text = profile.locationProfile,
                    modifier = Modifier.padding(start = 17.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (!profile.emailProfile.isNullOrEmpty()) {

                Spacer(modifier = Modifier.height(11.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.mail),
                        contentDescription = null
                    )
                    Text(
                        text = profile.emailProfile,
                        modifier = Modifier.padding(start = 14.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(13.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.people),
                    contentDescription = null
                )
                Text(
                    text = "Подписчики: ${profile.followersProfile}",
                    modifier = Modifier.padding(start = 14.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(11.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.people),
                    contentDescription = null
                )
                Text(
                    text = "Подписки: ${profile.followingProfile}",
                    modifier = Modifier.padding(start = 14.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(31.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.list),
                    contentDescription = null
                )
                Text(
                    text = "Репозиториев:",
                    modifier = Modifier.padding(start = 17.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = profile.publicReposProfile,
                    modifier = Modifier.padding(start = 17.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileDetailsContent(
            profile = ProfileDetails(
                login = "BBBBBBBBB",
                avatar = "",
                nameProfile = "Adam",
                description = "",
                locationProfile = "Rassha",
                emailProfile = "adam5770125@gmail.com",
                followersProfile = "9999999999999",
                followingProfile = "1",
                publicReposProfile = "656786",
            ),
            onBackClick = {},
            onFavoritesClick = {}
        )
    }
}