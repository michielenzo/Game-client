package idema.michiel.game.engine

import javafx.scene.image.Image

object ImageLoader {

    fun load(uri: String, width: Double, height: Double): Image{
        return Image(ImageLoader.javaClass.getResource(uri).toExternalForm(),
                width, height, false, false)
    }
}