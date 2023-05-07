data class BusModel (
    var BusID : String?,
    var BusNumbr:String?,
    var DEmail:String?,
    var StartLocation : String?,
    var EndLocation:String?,
    var StartTime:String?,
    var EndTime: String?,
    var Date:String?,
    var NoFoSeat:Int?,
    var NoFoBookingSeat:Int?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}
