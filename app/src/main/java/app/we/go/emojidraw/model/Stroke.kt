package app.we.go.emojidraw.model

class Stroke {

    val xcoords = ArrayList<Int>()
    val ycoords = ArrayList<Int>()

    fun addPoint(x: Int, y: Int) {
        xcoords.add(x)
        ycoords.add(y)
    }
}