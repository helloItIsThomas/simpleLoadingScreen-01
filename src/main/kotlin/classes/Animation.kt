package demos.classes

import org.openrndr.extra.keyframer.Keyframer

// define keyframes channels
class Animation : Keyframer() {
    val pathSlider by DoubleChannel("pathSlider")
    val circleSlider by DoubleChannel("circleSlider")
    val circleSliderOut by DoubleChannel("circleSliderOut")
    val circleSliderOut1 by DoubleChannel("circleSliderOut1")
    val iconSlider by DoubleChannel("iconSlider")
    val lineSlider by DoubleChannel("lineSlider")
    val textSlider by DoubleChannel("textSlider")
    val colorSlider by DoubleChannel("colorSlider")
    val shapeLine by DoubleChannel("shapeLine")
    val half by DoubleChannel("half")
    val whole by DoubleChannel("whole")
    val whole2 by DoubleChannel("whole2")
    val third by DoubleChannel("third")
    val quarter by DoubleChannel("quarter")
}