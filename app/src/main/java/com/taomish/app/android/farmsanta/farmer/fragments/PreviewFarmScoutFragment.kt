package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmScoutImageTask
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmScoutingTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.ScoutImage
import com.taomish.app.android.farmsanta.farmer.models.api.profile.UploadedFile
import com.taomish.app.android.farmsanta.farmer.utils.ImageUtil
import java.io.File
import java.io.IOException

/*Preview image with caption
* Call Upload Farm scout image API*/
class PreviewFarmScoutFragment : FarmSantaBaseFragment() {

    private lateinit var img: ImageView
    private lateinit var uploadButton: ImageButton
    private lateinit var editTextCaption: EditText
    private var userProfileBitmapPath: String? = null
    private lateinit var file: File
    private lateinit var farmScouting: FarmScouting
    lateinit var takenImage: Bitmap
    private lateinit var landId: String

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater!!.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun initViewsInLayout() {
        img = initThisView(R.id.imageViewPreview)
        uploadButton = initThisView(R.id.imageButtonSend)
        editTextCaption = initThisView(R.id.editTextCaption)
    }

    override fun initListeners() {

    }

    private fun saveFarmScouting(fileName: String) {
        //get  file name after upload
        val img =
            ScoutImage();
        img.image = fileName

        val images = mutableListOf(img)
        Log.e("TAG0", " FILE NAME ${images.getOrNull(0)?.image}")
        //farmScouting                            = FarmScouting(editTextCaption.text.toString(), landId, images, "", "")

    }

    override fun initData() {
        takenImage = ImageUtil().rotateIfImageNeeded(takenImage, userProfileBitmapPath)
        img.setImageBitmap(takenImage)
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = this.requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(editTextCaption.windowToken, 0)
    }

    @Throws(IOException::class)
    private fun uploadFarmScout() {
        val task = SaveFarmScoutImageTask(file)
        task.context = requireContext()
        task.setLoadingMessage(getString(R.string.saving_farm_scout))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                val savedFileName = (data as UploadedFile).fileName
                saveFarmScouting(savedFileName)
                saveFarmScoutingService(farmScouting)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun saveFarmScoutingService(farmScouting: FarmScouting) {
        val task = SaveFarmScoutingTask(farmScouting)
        task.context = requireContext()
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                hideKeyboard()
                (requireActivity() as FarmerMapSelectActivity).navController.popBackStack()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

}