package classes

import org.openrndr.MouseEvents
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.LineCap
import org.openrndr.math.map
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle

//class CToggle(rectRef: Rectangle){
//    val innerMarginX = 5.0
//    val innerMarginY = 2.5
//    val bRad = 5.0
//    val xPos = rectRef.x
//    val yPos = rectRef.y + innerMarginY
//    val w = rectRef.width
//    val h = rectRef.height - innerMarginY*2
//    val thisRect = Rectangle(xPos, yPos, w, h)
//}

class CButton(rectRef: Rectangle){
    val innerMarginX = 5.0
    val innerMarginY = 2.5
    val bRad = 5.0
    val xPos = rectRef.x
    val yPos = rectRef.y + innerMarginY
    val w = rectRef.width
    val h = 30.0 - innerMarginY*2
    var isActive: Boolean = false
    var fillCol = ColorRGBa.BLACK
    var toggleOnOff: Boolean = false
    val thisRect = Rectangle(xPos, yPos, w, h)

    fun isHovering(thisMouse: MouseEvents): Boolean {
        return thisMouse.position.x >= xPos && thisMouse.position.x <= xPos + w &&
                thisMouse.position.y >= yPos && thisMouse.position.y <= yPos + h
    }

    fun toggle() {
        isActive = !isActive
    }

    fun checkButton(checkMouse: MouseEvents, mouseClick: Boolean) {
        if (this.isHovering(checkMouse) && mouseClick) {
            toggle()
        }
        if (isActive) fillCol = ColorRGBa.GREEN
        else fillCol = ColorRGBa.WHITE
    }
}

class CSlider(rectRef: Rectangle){
//    val innerMarginX = 5.0
//    val innerMarginY = 2.5
    val xPos = rectRef.x
    val yPos = rectRef.y
    val w = rectRef.width
    val h = rectRef.height // rectRef.height - innerMarginY
    private val sliderRad = h/2
    var sliderX = xPos + (sliderRad * 0.5)
    var sliderY = yPos + (h*0.5)
    var sliderVal: Double = 0.0
    val thisRect = Rectangle(xPos, yPos, w, h)

    fun isHovering(hoverMouse: MouseEvents): Boolean {
        return hoverMouse.position.x >= xPos && hoverMouse.position.y <= xPos + w &&
                hoverMouse.position.y >= yPos && hoverMouse.position.y <= yPos + h
    }
    fun updateSlider(upMouse: MouseEvents, localMouseState: String) {
        if(isHovering(upMouse) && localMouseState == "drag"){
            sliderX = upMouse.position.x.coerceIn(xPos, xPos + w)
            sliderVal = sliderX.map(xPos, xPos + w, 0.0, 1.0)
        }
    }
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
}