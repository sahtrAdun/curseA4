package dot.curse.matule.ui.screens.profile.comp

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dot.curse.matule.R
import dot.curse.matule.ui.items.MatuleTextField
import dot.curse.matule.ui.screens.profile.ProfileViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lastNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.pasteImageByUri(it)
        }

    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
        .verticalScroll(rememberScrollState())
        .clickable(null, null) {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.user.avatar.isNotEmpty()) {
                        AsyncImage(
                            model = state.user.avatar,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.user.firstName + " " + state.user.lastName,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Box(modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .clickable(null, null) {
                                if (!state.edit) {
                                    viewModel.onEditClick()
                                } else {
                                    viewModel.updateUser()
                                }
                            },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (!state.edit) Icons.Default.Edit else Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    if (state.edit) {
                        Text(
                            text = stringResource(R.string.profile_image),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable(null, null) {
                                viewModel.onImageClick()
                            }
                        )
                    }
                }
            }

            ProfileInputRow(
                label = stringResource(R.string.profile_first_name)
            ) {
                MatuleTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.firstName,
                    enabled = state.edit,
                    onValueChange = viewModel::updateFirstName,
                    placeholder = "",
                    background = MaterialTheme.colorScheme.background,
                    actionType = ImeAction.Next,
                    actions = KeyboardActions(onNext = {
                        lastNameFocusRequester.requestFocus()
                    })
                )
            }
            ProfileInputRow(
                label = stringResource(R.string.profile_last_name)
            ) {
                MatuleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(lastNameFocusRequester),
                    value = state.lastName,
                    enabled = state.edit,
                    onValueChange = viewModel::updateLastName,
                    placeholder = "",
                    background = MaterialTheme.colorScheme.background,
                    actionType = ImeAction.Next,
                    actions = KeyboardActions(onNext = {
                        emailFocusRequester.requestFocus()
                    }),
                )
            }
            ProfileInputRow(
                label = stringResource(R.string.email)
            ) {
                MatuleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester),
                    value = state.email,
                    enabled = state.edit,
                    onValueChange = viewModel::updateEmail,
                    placeholder = "",
                    background = MaterialTheme.colorScheme.background,
                    type = KeyboardType.Email,
                    actionType = ImeAction.Next,
                    actions = KeyboardActions(onNext = {
                        phoneFocusRequester.requestFocus()
                    })
                )
            }
            ProfileInputRow(
                label = stringResource(R.string.profile_phone)
            ) {
                MatuleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(phoneFocusRequester),
                    value = state.phone,
                    enabled = state.edit,
                    onValueChange = viewModel::updatePhone,
                    placeholder = "",
                    background = MaterialTheme.colorScheme.background,
                    type = KeyboardType.Phone,
                    actionType = ImeAction.Done,
                    actions = KeyboardActions(onDone = {
                        keyboard?.hide()
                        focusManager.clearFocus()
                    })
                )
            }
        }
    }
    if (state.pickImage) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
            .clickable(null, null) { viewModel.closeImageClick() },
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.gallery),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp).clickable(null, null) {
                            launcher.launch("image/*")
                        }
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.camera),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}