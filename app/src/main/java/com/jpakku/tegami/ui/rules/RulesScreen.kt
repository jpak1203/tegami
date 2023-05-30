package com.jpakku.tegami.ui.rules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpakku.tegami.R

@Composable
fun RulesScreen() {

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp),
            text = stringResource(R.string.rules),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Rules()
    }
}

@Preview(showBackground = true)
@Composable
fun RulesScreenPreview() {
    Surface {
        RulesScreen()
    }
}

@Composable
fun Rules() {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_1_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_1_body),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_2_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_2_body),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_3_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_3_body),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_4_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_4_body),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_5_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_5_body),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_6_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_6_body),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            modifier = Modifier.padding(0.dp, 10.dp),
            text = stringResource(R.string.rule_7_title),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(0.dp, 5.dp),
            text = stringResource(R.string.rule_7_body),
            style = MaterialTheme.typography.bodySmall
        )

    }
}