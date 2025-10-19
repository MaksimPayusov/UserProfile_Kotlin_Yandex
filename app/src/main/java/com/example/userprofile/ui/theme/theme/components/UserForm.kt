package com.example.userprofile.ui.theme.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.userprofile.R
import com.example.userprofile.ui.theme.theme.theme.UserProfileTheme

@Composable
fun UserForm() {

    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableFloatStateOf(25f) }
    var gender by rememberSaveable { mutableStateOf("") }
    var isSubscribed by rememberSaveable { mutableStateOf(false) }
    var summary by rememberSaveable { mutableStateOf("") }

    val genderMaleLabel = stringResource(R.string.gender_male)

    LaunchedEffect(Unit) {
        if (gender.isBlank()) {
            gender = genderMaleLabel
        }
    }

    val subYes = stringResource(R.string.subscription_yes)
    val subNo = stringResource(R.string.subscription_no)
    val summaryTemplate = stringResource(R.string.summary_result)

    val isNameValid = name.isNotBlank()
    val isButtonEnabled = isNameValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        FormSection(
            name = name,
            onNameChange = { name = it },
            age = age,
            onAgeChange = { age = it },
            gender = gender,
            onGenderChange = { gender = it },
            isSubscribed = isSubscribed,
            onSubscribeChange = { isSubscribed = it },
            summary = summary,
            onSubmit = {
                val subStatus = if (isSubscribed) subYes else subNo
                summary = String.format(summaryTemplate, name, age.toInt(), gender, subStatus)
            },
            isButtonEnabled = isButtonEnabled,
            isNameValid = isNameValid
        )
    }
}

@Composable
fun FormSection(
    name: String,
    onNameChange: (String) -> Unit,
    age: Float,
    onAgeChange: (Float) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    isSubscribed: Boolean,
    onSubscribeChange: (Boolean) -> Unit,
    summary: String,
    onSubmit: () -> Unit,
    isButtonEnabled: Boolean,
    isNameValid: Boolean
) {
    val genderMaleLabel = stringResource(R.string.gender_male)
    val genderFemaleLabel = stringResource(R.string.gender_female)

    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text(stringResource(R.string.hint_name)) },
        isError = !isNameValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        supportingText = {
            if (!isNameValid) {
                Text(stringResource(R.string.error_name_empty))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.label_age, age.toInt()),
            modifier = Modifier.width(100.dp)
        )
        Slider(
            value = age,
            onValueChange = onAgeChange,
            valueRange = 1f..100f,
            steps = 98,
            modifier = Modifier.weight(1f)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.label_gender), modifier = Modifier.padding(end = 16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            RadioButton(
                selected = gender == genderMaleLabel,
                onClick = { onGenderChange(genderMaleLabel) }
            )
            Text(genderMaleLabel)
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            RadioButton(
                selected = gender == genderFemaleLabel,
                onClick = { onGenderChange(genderFemaleLabel) }
            )
            Text(genderFemaleLabel)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSubscribed,
            onCheckedChange = onSubscribeChange
        )
        Text(stringResource(R.string.label_subscribe))
    }

    Button(
        onClick = onSubmit,
        enabled = isButtonEnabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.button_send))
    }

    if (summary.isNotEmpty()) {
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Preview(name = "Light Mode - Portrait")
@Composable
fun UserFormPreviewLight() {
    UserProfileTheme(darkTheme = false) {
        Surface {
            UserForm()
        }
    }
}

@Preview(name = "Dark Mode - Portrait")
@Composable
fun UserFormPreviewDark() {
    UserProfileTheme(darkTheme = true) {
        Surface {
            UserForm()
        }
    }
}