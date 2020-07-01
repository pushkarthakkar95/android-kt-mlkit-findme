package com.mlkitexample.whatami

import android.Manifest
import com.mlkitexample.whatami.question.Element

object Helper {
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    val REQUEST_CODE_FOR_PERMISSIONS = 75

    val listOfLostGifs = listOf(
        R.drawable.lost_gif_1,
        R.drawable.lost_gif_2,
        R.drawable.lost_gif_3
    ).shuffled()

    val listOfWonGifs = listOf(
        R.drawable.win_gif_1,
        R.drawable.win_gif_2,
        R.drawable.win_gif_3
    ).shuffled()

    val listOfElement = listOf(
        Element("Desk",R.drawable.lamp),
        Element("Eyelash",R.drawable.vision),
        Element("Wall",R.drawable.wall),
        Element("Pillow",R.drawable.pillow),
        Element("Dog",R.drawable.dog),
        Element("Musical instrument",R.drawable.concert),
        Element("Shoe",R.drawable.shoes),
        Element("Ear",R.drawable.ear),
        Element("Hair",R.drawable.hair),
        Element("Glasses",R.drawable.ophthalmology),
        Element("Beard",R.drawable.beard),
        Element("Smile",R.drawable.smile),
        Element("Moustache",R.drawable.hipster),
        Element("Dude",R.drawable.dude),
        Element("Mouth",R.drawable.emoji)
    )

    var mutableList : MutableList<Element>? = null

    fun assignListByShuffling() {
        mutableList = listOfElement.shuffled().subList(0,3).toMutableList()
    }
}