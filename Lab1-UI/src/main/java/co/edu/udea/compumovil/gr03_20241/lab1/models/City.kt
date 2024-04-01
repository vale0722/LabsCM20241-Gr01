package co.edu.udea.compumovil.gr03_20241.lab1.models

data class City(
    override val name: String,
): Entity

data class GeoNamesResponse(
    val geonames: List<City>
)