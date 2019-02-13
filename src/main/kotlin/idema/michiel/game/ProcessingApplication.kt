package idema.michiel.game

import processing.core.PApplet

fun main(args: Array<String>) {
    PApplet.main("idema.michiel.game.ProcessingApplication")
}

class ProcessingApplication: PApplet(){

    override fun settings() {
        size(800,500)
    }

    override fun setup() {
        background(0)
    }

    override fun draw() {

    }

}



