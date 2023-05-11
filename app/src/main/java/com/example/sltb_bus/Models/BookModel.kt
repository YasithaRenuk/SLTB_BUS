package com.example.sltb_bus.Models

data class BookModel (
    val bookId:String?,
    val pemail:String?,
    val busId:String?,
    val noFoSeat:Int?
    )
{
    constructor() : this(null, null, null, null)
}