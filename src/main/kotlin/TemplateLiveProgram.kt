
import classes.CLoader
import demos.classes.Animation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import org.openrndr.WindowMultisample
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.color.presets.LIGHT_PINK
import org.openrndr.extra.noise.random
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.grid
import org.openrndr.ffmpeg.VideoPlayerFFMPEG
import org.openrndr.ffmpeg.loadVideo
import org.openrndr.launch
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2
import org.openrndr.math.map
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle
import org.openrndr.shape.findShapes
import org.openrndr.svg.loadSVG
import java.io.File


@OptIn(DelicateCoroutinesApi::class)
fun main() = application {
    configure {
        width = 608
        height = 342
        hideWindowDecorations = true
        windowAlwaysOnTop = true
        position = IntVector2(1285,110)
        windowTransparent = true
        multisample = WindowMultisample.SampleCount(4)
    }
    oliveProgram {
// MOUSE STUFF //////
        var mouseClick = false
        var mouseState = "up"
        mouse.dragged.listen { mouseState = "drag" }
        mouse.exited.listen { mouseState = "up" }
        mouse.buttonUp.listen { mouseState = "up"; mouseClick = true }
        mouse.buttonDown.listen { mouseState = "down" }
        mouse.moved.listen { mouseState = "move" }
// END //////////////
        val columnCount = 3
        val rowCount = 3
        val marginX = 10.0
        val marginY = 10.0
        val gutterX = 3.0
        val gutterY = 3.0
        var grid = drawer.bounds.grid(columnCount, rowCount, marginX, marginY, gutterX, gutterY)
        val flatGrid = grid.flatten()

        val incremCheck = onceObj()
        var palette = listOf(ColorRGBa.fromHex(0xF1934B), ColorRGBa.fromHex(0x0E8847), ColorRGBa.fromHex(0xD73E1C), ColorRGBa.fromHex(0xF4ECDF), ColorRGBa.fromHex(0x552F20))
        val white = ColorRGBa.WHITE
        val black = ColorRGBa.BLACK
        val animation = Animation()
        val loopDelay = 2.0
        val message = "hello"
        animation.loadFromJson(File("data/keyframes/keyframes-0.json"))
        val svgA = loadSVG(File("data/fonts/a.svg"))
        val firstShape = svgA.root.findShapes()[0]
        val firstContour = firstShape.shape.contours[0]

        val image = loadImage("data/images/cheeta.jpg")
        val scale: DoubleArray = typeScale(3, 16.0, 3)
        val typeFace: Pair<List<FontMap>, List<FontImageMap>> = defaultTypeSetup(scale, listOf("med"))
        val animArr = mutableListOf<Animation>()
        val randNums = mutableListOf<Double>()
        val charArr = message.toCharArray()
        charArr.forEach { e ->
            animArr.add(Animation())
            randNums.add(random(0.0, 1.0))
        }
        animArr.forEach { a ->
            a.loadFromJson(File("data/keyframes/keyframes-0.json"))
        }
        val globalSpeed = 0.01

        var loaded = false
        val videos = MutableList<VideoPlayerFFMPEG?>(100) { null }


        launch(Dispatchers.IO) {
            for (i in videos.indices) {
                videos[i] = loadVideo("data/video/big.mp4")
            }
            loaded = true
        }

        val backCol = ColorRGBa.WHITE
        val fillCol = ColorRGBa.fromHex(0xA9A9A9)
        val strokeCol = ColorRGBa.PINK


        val loader0 = CLoader(Rectangle(drawer.bounds.center, 30.0, 30.0))

        val loadMessage = "LOADING"



        extend {
            animArr.forEachIndexed { i, a ->
                a((randNums[i] * 0.3 + frameCount * globalSpeed) % loopDelay)
            }

            if (loaded) {
                drawer.clear(backCol)
                drawer.stroke = strokeCol
                drawer.fill = null
                drawer.rectangle(drawer.bounds)
            } else {
                drawer.clear(backCol)
                drawer.pushTransforms()
                drawer.stroke = strokeCol
                drawer.fill = null
                drawer.rectangle(drawer.bounds)
                drawer.translate( drawer.bounds.center * Vector2(-0.5, 0.0))
                drawer.fill = null
                drawer.stroke = strokeCol.opacify(animArr[0].whole2)
                drawer.strokeWeight = 2.5
                drawer.lineCap = LineCap.ROUND
                drawer.contour(Circle(loader0.thisRect.x, loader0.thisRect.y, loader0.thisRect.width).contour.sub( animArr[0].whole2, animArr[0].whole ))

                drawer.fontMap = typeFace.first[0]
                drawer.fill = fillCol.opacify(animArr[0].half)
                drawer.text(loadMessage.take(
                     animArr[0].half.map(0.0, 1.0, 0.0,  loadMessage.length.toDouble()).toInt()
                ), drawer.bounds.center * Vector2(1.2, 1.0))
                drawer.popTransforms()
            }

            // THIS NEEDS TO STAY AT THE END //
            if (mouseClick) mouseClick = false
            // END END ////////////////////////
        }
    }
}