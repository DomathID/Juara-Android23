import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class Message(val sender: String, val content: String)

@Composable
fun MessagingApp() {
    val messages = remember { mutableStateListOf<Message>() }
    val newMessage = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Messaging App") },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFECEFF1))
            ) {
                MessageList(messages)
                MessageInput(newMessage.value) {
                    messages.add(Message("You", it))
                    newMessage.value = ""
                }
            }
        }
    )
}

@Composable
fun MessageList(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(messages) { message ->
            MessageItem(message)
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (message.sender == "You") {
            Spacer(modifier = Modifier.width(48.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.profile_image),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .background(
                    color = if (message.sender == "You") Color(0xFFBBDEFB) else Color.White,
                    shape = MaterialTheme.shapes.large
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.sender,
                style = MaterialTheme.typography.subtitle2,
                color = if (message.sender == "You") Color(0xFF2196F3) else Color.Black
            )
            Text(
                text = message.content,
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
        }
        if (message.sender != "You") {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

@Composable
fun MessageInput(message: String, onSendClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { onSendClick(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text(text = "Type a message...") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2196F3),
                unfocusedBorderColor = Color.LightGray
            )
        )
        IconButton(
            onClick = { onSendClick(message) },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Color(0xFF2196F3)
            )
        }
    }
}

