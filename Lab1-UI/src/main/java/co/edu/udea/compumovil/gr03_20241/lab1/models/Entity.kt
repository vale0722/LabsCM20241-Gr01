package co.edu.udea.compumovil.gr03_20241.lab1.models

interface Entity: CharSequence {
    val name: String;
    override val length: Int
        get() = this.name.length

    override operator fun get(index: Int): Char = this[index]

    override fun subSequence(startIndex: Int, endIndex: Int): String {
        return ""
    }
}