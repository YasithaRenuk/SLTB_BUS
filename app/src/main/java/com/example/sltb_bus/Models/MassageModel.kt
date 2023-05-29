package com.example.sltb_bus.Models

data class MassageModel (
    var MsqID : String?,
    var Massage:String?,
    var Date:String?
){
    constructor() : this(null, null, null)
}
