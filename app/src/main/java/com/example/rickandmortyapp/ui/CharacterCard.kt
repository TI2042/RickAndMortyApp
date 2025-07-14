import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.style.TextOverflow
import com.example.rickandmortyapp.data.local.CharacterEntity

@Composable
fun CharacterCard(
    character: CharacterEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(character.image),
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            }
            Text(
                text = character.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                StatusDot(character.status.apiName)
                Spacer(Modifier.width(6.dp))
                Text(
                    text = character.status.apiName.capitalize(),
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${character.species} • ${character.gender.apiName.capitalize()}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
            )
        }
    }
}
@Composable
fun StatusDot(status: String) {
    val color = when (status.lowercase()) {
        "alive" -> com.example.rickandmortyapp.ui.theme.GreenAlive
        "dead" -> com.example.rickandmortyapp.ui.theme.RedDead
        else -> com.example.rickandmortyapp.ui.theme.WhiteOther
    }
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(color = color, shape = RoundedCornerShape(50))
    )
}
