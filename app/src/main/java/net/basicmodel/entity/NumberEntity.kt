package net.basicmodel.entity

import java.io.Serializable

data class NumberEntity(
    var countryCode:String="",
    var countryNumber:String="",
    var number:String="",
    var numberFull:String="",
    var countryName:String="",
    var imageUrl:String="",
):Serializable
