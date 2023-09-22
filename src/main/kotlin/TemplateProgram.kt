import kotlinx.coroutines.Dispatchers
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.loadFont
import org.openrndr.draw.loadImage
import org.openrndr.draw.tint
import org.openrndr.extra.color.presets.LIGHT_PINK
import org.openrndr.ffmpeg.VideoPlayerFFMPEG
import org.openrndr.ffmpeg.loadVideo
import org.openrndr.launch
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle
import kotlin.math.cos
import kotlin.math.sin

fun main() = application {
    configure {
        width = 768
        height = 576
    }

    program {

        val font = loadFont("data/fonts/default.otf", 64.0)
        var image = loadImage("data/images/pm5544.png")

        var loaded = false
        val videos = MutableList<VideoPlayerFFMPEG?>(1) { null }


        launch(Dispatchers.IO) {
            for (i in videos.indices) {
                videos[i] = loadVideo("data/video/big.mp4")
            }
            loaded = true
        }

        class CLoader(rectRef: Rectangle){
            val bRad = 5.0
            val xPos = rectRef.x
            val yPos = rectRef.y
            val w = rectRef.width
            val h = 30.0
            var isActive: Boolean = false
            var fillCol = ColorRGBa.BLACK
            var toggleOnOff: Boolean = false
            val thisRect = Rectangle(xPos, yPos, w, h)

            fun draw(drawer: Drawer) {
                drawer.fill = null
                drawer.stroke = ColorRGBa.LIGHT_PINK
                drawer.contour(Circle(thisRect.x, thisRect.y, thisRect.width).contour)
            }

        }

        val loader0 = CLoader(Rectangle(drawer.bounds.center, 30.0, 30.0))


        extend {
            if (loaded) {
                drawer.clear(ColorRGBa.TRANSPARENT)
                loader0.draw(drawer)

            } else {
                drawer.clear(ColorRGBa.BLACK)
                drawer.fill = ColorRGBa.WHITE
                drawer.fontMap = font
                drawer.text("Loading...", drawer.bounds.center)
                drawer.circle(drawer.bounds.center, 100.0)
            }
        }
    }
}
