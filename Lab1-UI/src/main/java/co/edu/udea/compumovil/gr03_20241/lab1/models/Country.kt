package co.edu.udea.compumovil.gr03_20241.lab1.models

data class Country (
    override val name: String,
    val alpha2Code: String
): Entity