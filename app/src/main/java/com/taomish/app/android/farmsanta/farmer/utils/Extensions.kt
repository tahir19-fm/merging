@file:Suppress("DEPRECATION")

package com.taomish.app.android.farmsanta.farmer.utils

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.LocaleList
import android.util.Patterns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.snap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Area
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Coordinate
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.AdvisoryDetails
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.weather.WeatherAll
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToInt


val Any.TAG: String
    get() = this::class.java.simpleName

fun <T> MutableState<T>.postValue(newValue: T) {
    this.value = newValue
}

fun <T> SnapshotStateList<T>.postValue(newValue: List<T>) {
    this.addAll(newValue)
}


infix fun <T> MutableState<T>.changeTo(value: T) {
    this.postValue(value)
}

fun <T> MutableState<List<T>>.add(item: T) {
    this.postValue(this.value + item)
}


@OptIn(ExperimentalMaterialApi::class)
suspend fun ModalBottomSheetState.expand() {
//    animateTo(ModalBottomSheetValue.Expanded)
}


fun Context.showToast(message: String) {
    MainScope().launch {
        Toast.makeText(this@showToast, message, Toast.LENGTH_LONG).show()
    }
}

fun Context.showToast(@StringRes messageId: Int) {
    MainScope().launch {
        Toast.makeText(this@showToast, getString(messageId), Toast.LENGTH_LONG).show()
    }
}


fun Fragment.showToast(message: String) {
    try {
        requireContext().showToast(message)
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    }
}

fun Fragment.showToast(@StringRes messageId: Int) {
    try {
        requireContext().showToast(messageId)
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    }
}


fun Price.format(context: Context): String {
    val name = "${this.tickerCode} " + (this.productName?.substring(
        startIndex = this.productName.indexOf(':') + 1
    ) ?: "")
    val value: String = (if (this.currentPrice?.currency.isNullOrEmpty()) "$"
    else this.currentPrice?.currency ?: "") + "" + (this.currentPrice?.value?.toInt() ?: "0.00")
    val unit = "${(packaging?.unit?.toInt() ?: 0)} ${(packaging?.uom ?: "")}"
    val changePercentage =
        try {
            "${
                (((this.currentPrice?.value ?: 0.0) - (this.previousPrice?.value ?: 0.0))
                        / (this.currentPrice?.value ?: 0.0)).roundToInt()
            }%"
        } catch (e: Exception) {
            "0"
        }
    val symbol = if ((this.currentPrice?.value ?: 0.0) > (this.previousPrice?.value ?: 0.0))
        context.resources.getString(R.string.up_arrow) else context.resources.getString(R.string.down_arrow)
    return "$name $value ${context.getString(R.string.per)} $unit $changePercentage $symbol"
}


fun LocalDate.asString(part: String): String {
    val formatter = DateTimeFormatter.ofPattern(part)
    return this.format(formatter)
}

fun String.getLocalDate(): LocalDate {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00")
    formatter = formatter.withLocale(Locale.ENGLISH)
    return LocalDate.parse(this, formatter)
}


fun String?.getApiFormatDate(withZ: Boolean = false): String? {
    val outputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS" + if (withZ) "'Z'" else ""
    val inputDateFormat = "dd-MM-yyyy"
    val input = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
    val output = SimpleDateFormat(outputDateFormat, Locale.ENGLISH)
    this?.let {
        try {
            val getAbbreviate = input.parse(this) // parse input
            if (getAbbreviate != null) {
                return output.format(getAbbreviate) // format output
            }
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
    }
    return this
}


fun MutableState<String>.isNotEmpty(): Boolean {
    return this.value.trim().isNotEmpty()
}

fun MutableState<String>.isEmpty(): Boolean {
    return this.value.trim().isEmpty()
}


fun <T> Collection<T>?.isNotEmptyOrNull(action: (Collection<T>) -> Unit) {
    if (!this.isNullOrEmpty()) {
        action(this)
    }
}

fun <K, V> Map<K, V>?.isNotEmptyOrNull(action: (Map<K, V>) -> Unit) {
    if (!this.isNullOrEmpty()) {
        action(this)
    }
}

fun Uri.asBitmap(context: Context): Bitmap? {
    return Compress.with(context, this).setQuality(100).concrete {
        withScaleMode(ScaleMode.SCALE_LARGER)
        withIgnoreIfSmaller(true)
    }.getBitmap()
}

fun LocalDateTime.asString(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}


fun String.toLocalDateTime(): LocalDateTime {
    if (this.isEmpty()) return LocalDateTime.now()
    var date = ""
    if (this.length > 23) {
        date = this.removeRange(23, this.length)
    }
    if (this.contains("Z", ignoreCase = true)) {
        date = this.replace("Z", "")
    }
    if (this.contains("T", ignoreCase = true).not()) {
        if (this.contains(" ", ignoreCase = true)) {
            date = this.replace(" ", "T")
        }
    }
    if (this.length == 10) {
        date = this + "T00:00:00.000"
    }
    if (this.contains("+", ignoreCase = true)) {
        date = this.dropLast(6)
    }
    return LocalDateTime.parse(date)
}

fun LocalDateTime.asDate(): Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}


fun Coordinate.toLatLng(): LatLng = LatLng(this.latitude, this.longitude)

fun <T> MutableState<List<T>>.clear() {
    this.value = emptyList()
}

fun bitmapDescriptor(
    context: Context, vectorResId: Int, size: Int = 100,
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, size, size)
    val bm = Bitmap.createBitmap(
        size, size, Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun List<LatLng>?.getCenterCoordinate(): LatLng? {
    var centerLatLng: LatLng? = null
    if (this != null && this.size > 2) {
        val builder = LatLngBounds.Builder()
        this.forEach(builder::include)
        centerLatLng = builder.build().center
    }
    return centerLatLng
}


fun LatLng.getLocationName(context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val fromLocation = geocoder.getFromLocation(this.latitude, this.longitude, 1)
        if (fromLocation != null && fromLocation.size > 0) {
            return fromLocation[0].getAddressLine(0)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}

fun String.isEmail(): Boolean {
    val pattern: Pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(this.trim()).matches()
}

fun CropMaster.toCrop(): Crop {
    val crop = Crop()
    crop.cropId = this.uuid
    crop.cultivar = ""
    crop.cropType = this.cropGroup
    crop.cropName = this.cropName
    crop.current = false
    crop.stage = ""
    crop.harvestDate = ""
    crop.sowingDate = ""
    crop.expectedYield = 0.0
    crop.previousYield = 0.0
    crop.comment = ""
    crop.cultivationType = ""
    crop.variety = ""
    crop.area = Area()
    crop.rowSpacing = 0.0
    crop.plantSpacing = 0.0
    return crop
}

fun AdvisoryDetails?.formatDetails(context: Context): String {
    return if (this == null) "Details not available"
    else """${context.getString(R.string.symptom_of_attack)}${symptomsOfAttack ?: ""}
        ${context.getString(R.string.favourable_conditions)}${favourableConditions ?: ""}
        ${context.getString(R.string.preventive_measures)}${preventiveMeasures ?: ""}
        ${context.getString(R.string.cultural_mechanical_control)}${culturalMechanicalControl ?: ""}
        ${context.getString(R.string.cultural_control)}${culturalControl ?: ""}
        ${context.getString(R.string.description)}${description ?: ""}
    """.trimIndent()
}

fun getMonth(month: Int?): String {
    if (month == null || month < 0) return ""
    return try {
        DateFormatSymbols().months[month].take(3)
    } catch (e: Exception) {
        ""
    }
}


@OptIn(ExperimentalPagerApi::class)
val PagerState.lastPage: Int
    get() = (this.pageCount - 1)


fun String?.notNull(): String {
    return this ?: "-"
}


fun Int?.ifNull(defaultValue: () -> Int): Int {
    return this ?: defaultValue()
}

fun Double?.ifNull(defaultValue: () -> Double): Double {
    return this ?: defaultValue()
}


fun getFileType(context: Context, uri: Uri): String {
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(
        context.contentResolver.getType(uri)
    ) ?: "jpg"
}

fun saveFileInStorage(context: Context, name: String? = null, uri: Uri): File? {
    val filename = name ?: ("${System.currentTimeMillis()}" + getFileType(context, uri))
    var file: File? = null
    try {
        val inStream = context.contentResolver.openInputStream(uri)
        val directory = context.externalCacheDir
        file = File(directory, filename)
        val fOutStream = FileOutputStream(file)
        val byteArray = ByteArray(1024)
        var len = inStream?.read(byteArray) ?: -1
        while (len > 0) {
            fOutStream.write(byteArray)
            len = inStream?.read(byteArray) ?: -1
        }
        fOutStream.close()
        inStream?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file
}

fun getImageForWeather(all: WeatherAll?): Int {
    if (all == null || all.current == null || all.current.weather == null || all.current.weather[0] == null) return R.drawable.ic_02n
    return getImageForWeather(all.current.weather[0].icon)
}

fun getImageForWeather(icon: String?): Int {
    return when (icon) {
        "01d" -> R.drawable.ic_01d
        "01n" -> R.drawable.ic_01n
        "02d" -> R.drawable.ic_02d
        "02n" -> R.drawable.ic_02n
        "03d" -> R.drawable.ic_03d
        "03n" -> R.drawable.ic_03n
        "04d" -> R.drawable.ic_04d
        "04n" -> R.drawable.ic_04n
        "09d" -> R.drawable.ic_09d
        "09n" -> R.drawable.ic_09n
        "10d" -> R.drawable.ic_10d
        "10n" -> R.drawable.ic_10n
        "11d" -> R.drawable.ic_11d
        "11n" -> R.drawable.ic_11n
        "13d" -> R.drawable.ic_13d
        "13n" -> R.drawable.ic_13n
        "50d" -> R.drawable.ic_50d
        "50n" -> R.drawable.ic_50n
        else -> R.drawable.ic_02n
    }
}


fun getWindDirection(windDeg: Int): String {
    return when (windDeg) {
        in 12..55 -> "North-East"
        in 56..79 -> "East-North"
        in 80..101 -> "East"
        in 102..123 -> "East-South"
        in 124..168 -> "South-East"
        in 169..191 -> "South"
        in 192..236 -> "South-West"
        in 237..258 -> "South-West"
        in 259..281 -> "West"
        in 282..303 -> "West-North"
        in 304..348 -> "North-West"
        else -> "North"
    }
}


/*
fun createDynamicLink(popUuid: String, onSuccess: (Uri?) -> Unit) {
    Firebase.dynamicLinks.shortLinkAsync {
        link = Uri.parse("http://farmsanta.com/pop/$popUuid")
        domainUriPrefix = "http://farmsanta.com"
        androidParameters {
            minimumVersion = 0
        }
    }.addOnSuccessListener { onSuccess(it.shortLink) }
        .addOnCanceledListener { Log.d("createDynamicLink", "onFailure") }
}*/



infix fun <T> T?.or(other: () -> T): T {
    return this ?: other()
}

fun String.getIconTitle(): String {
    val newLine = System.lineSeparator()
    return if (this.length > 11) this.split(" ").fold("") { acc, str ->
        val indexOfNewLine = (acc.length - 1) - StringBuffer(acc).lastIndexOf(newLine)
        if ((indexOfNewLine + str.length) > 10) "$acc\n$str"
        else "$acc $str"
    }
    else this
}


fun Context.setLanguage(language: String) {
    if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(language)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
    }
}

val Context.language: String?
    get() {
        return try {
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.getSystemService(LocaleManager::class.java).applicationLocales[0]?.language
            } else {
                AppCompatDelegate.getApplicationLocales()[0]?.language
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

fun Int.getLanguageIcon(): Int {
    return when (this) {
        1 -> R.drawable.ic_english
        2 -> R.drawable.ic_french
        else -> R.drawable.ic_english
    }
}

fun Activity.startAgain() {
    finish()
    startActivity(Intent(this, javaClass))
}

fun Context.findActivity(): AppCompatActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) return context
        context = context.baseContext
    }
    return null
}