package com.aristiyo.mynextevent.view

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.aristiyo.mynextevent.R
import com.aristiyo.mynextevent.databinding.FragmentCreateEventBinding
import com.aristiyo.mynextevent.helpers.HelperFunction
import com.aristiyo.mynextevent.model.Event
import com.aristiyo.mynextevent.viewmodel.EventViewModel
import com.aristiyo.mynextevent.viewmodel.ViewModelFatory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.io.ByteArrayOutputStream


class CreateEventFragment : DialogFragment() {

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private var event: Event? = Event()

    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CreateEventDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel = obtainViewModel(requireActivity() as AppCompatActivity)

        binding.topAppBar.setNavigationOnClickListener { dismiss() }

        binding.edtAddEventDateFrom.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener {
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setCalendarConstraints(
                        CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
                            .build()
                    )
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            setText(HelperFunction.dateFormatterSimple(it))
                        }
                    }.show(requireActivity().supportFragmentManager, TAG)
            }
        }

        binding.edtAddEventDateTo.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener {
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setCalendarConstraints(
                        CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
                            .build()
                    )
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            setText(HelperFunction.dateFormatterSimple(it))
                        }
                    }.show(requireActivity().supportFragmentManager, TAG)
            }
        }

        binding.edtAddEventTimeFrom.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener {
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(15)
                    .setTitleText("Select Appointment time")
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            setText("$hour : $minute")
                        }
                    }.show(requireActivity().supportFragmentManager, TAG)
            }
        }

        binding.edtAddEventTimeTo.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener {
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(15)
                    .setTitleText("Select Appointment time")
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            setText("$hour : $minute")
                        }
                    }.show(requireActivity().supportFragmentManager, TAG)
            }
        }

        binding.btnAddEventImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    resultPermission.launch(permissions)
                } else {
                    chooseImageGallery()
                }
            } else {
                chooseImageGallery()
            }
        }

        binding.btnSave.setOnClickListener {
            val eventName = binding.edtAddEventName.editText?.text.toString()
            val eventLocation = binding.edtAddEventLocation.editText?.text.toString()
            val eventDateFrom = binding.edtAddEventDateFrom.editText?.text.toString()
            val eventDateTo = binding.edtAddEventDateTo.editText?.text.toString()
            val eventTimeFrom = binding.edtAddEventTimeFrom.editText?.text.toString()
            val eventTimeTo = binding.edtAddEventTimeTo.editText?.text.toString()
            val eventDescription = binding.edtAddEventDescription.editText?.text.toString()
            val eventOrganizer = binding.edtAddEventOrganizer.editText?.text.toString()

            if (eventName.isEmpty() || eventLocation.isEmpty() || eventDateFrom.isEmpty() || eventTimeTo.isEmpty() || eventDescription.isEmpty() || eventOrganizer.isEmpty()) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Semua Field harus terisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            event.let { event ->
                event?.name = eventName
                event?.location = eventLocation
                event?.dateFrom = eventDateFrom
                event?.timeFrom = eventTimeFrom
                event?.dateTo = eventDateTo
                event?.timeTo = eventTimeTo
                event?.description = eventDescription
                event?.organizer = eventOrganizer
                event?.image = imageArray(binding.imgAddEvent.drawable) ///Belum ada validasi
            }
            if (event != null) eventViewModel.insert(event as Event)
            dismiss()
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): EventViewModel {
        val factory = ViewModelFatory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[EventViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog!!.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.imgAddEvent.setImageURI(data?.data)
            }
        }

    private val resultPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                chooseImageGallery()
            }
        }

    private fun imageArray(drawable: Drawable): ByteArray? {
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    companion object {
        private const val TAG = "CreateEventFragment"

        fun display(fragmentManager: FragmentManager?): CreateEventFragment {
            val dialog = CreateEventFragment()
            if (fragmentManager != null) {
                dialog.show(fragmentManager, TAG)
            }
            return dialog
        }
    }
}