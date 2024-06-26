package com.example.bachelordegreeproject.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bachelordegreeproject.R
import com.example.bachelordegreeproject.R.string
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UIConst
import com.example.bachelordegreeproject.presentation.component.CustomToast
import com.example.bachelordegreeproject.presentation.component.GradientButton
import com.example.bachelordegreeproject.presentation.component.SimpleOutlinedTextFieldSample
import com.example.bachelordegreeproject.presentation.route.Screen
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.presentation.viewmodel.PlaneAuthViewModel

@Composable
fun PlaneAuthScreen(
    navController: NavController,
    viewModel: PlaneAuthViewModel,
    modifier: Modifier = Modifier
) {
    val rfidInfo: RfidStatus? by viewModel.rfidStatus.observeAsState()
    var login by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        when (rfidInfo) {
            is RfidStatus.Success -> {
                navController.navigate(Screen.FlightCheckScreen.withArgs((rfidInfo as RfidStatus.Success).rfid))
                viewModel.resetParams()
            }

            is RfidStatus.Failure -> CustomToast(stringResource(id = (rfidInfo as RfidStatus.Failure).failure))
            is RfidStatus.Error -> CustomToast(
                stringResource(id = (rfidInfo as RfidStatus.Error).error),
                backgroundColor = Color.White,
                textColor = Color.Red,
                isClosing = false
            )

            else -> {}
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center),
        ) {

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_page_animation))
            LottieAnimation(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(id = string.authPlaneHeader),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Dark,
                )
                Spacer(modifier = Modifier.height(24.dp))
                SimpleOutlinedTextFieldSample(onTextChanged = {
                    login = it
                })

                Spacer(modifier = Modifier.padding(32.dp))

                GradientButton(
                    onClickAction = {
                        if (login.isNotEmpty()) navController.navigate(
                            Screen.FlightCheckScreen.withArgs(
                                login
                            )
                        )
                    },
                    gradientColors = UIConst.gradientColor,
                    cornerRadius = UIConst.gradientCornerRadius,
                    nameButton = stringResource(id = string.loginActionButtonTitle),
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
                )

                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = stringResource(id = string.scanRfidPlane),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge,
                    color = Dark,
                )

                Spacer(modifier = Modifier.padding(20.dp))

            }
        }

    }
}
