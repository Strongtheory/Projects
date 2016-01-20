import QtQuick 2.2
import QtQuick.Controls 1.1
import QtGraphicalEffects 1.0

Item {
    implicitHeight: image1.height + 1
    implicitWidth: image1.width + 1

    property string solarSystemName
    property color solarSystemColor
    property bool isCurrentSystem
    property double size: 32

    Rectangle {
        id: currentSystemRectangle
        color: "#00000000"
        border.color: "#FF00FF00"
        border.width: isCurrentSystem ? 3 : 0
        radius: 5
        width: image1.width + 8
        height: image1.height + 8

        Image {
            id: image1
            source: "qrc:///Planet.png"
            sourceSize: Qt.size(size, size)
            anchors.horizontalCenter: currentSystemRectangle.horizontalCenter
            anchors.verticalCenter: currentSystemRectangle.verticalCenter

            MouseArea {
                anchors.fill: parent
                hoverEnabled: true
                onEntered: {
                    mouseEffect.brightness = 0.4;
                    systemLabelRectangle.visible = true;
                }
                onExited: {
                    mouseEffect.brightness = 0
                    systemLabelRectangle.visible = false;
                }
                onClicked: setSolarSystem(solarSystemName)
            }
        }

        ColorOverlay {
            id: colorEffect
            anchors.fill: image1
            source: image1
            color: solarSystemColor
        }

        BrightnessContrast {
            id: mouseEffect
            anchors.fill: image1
            source: colorEffect
            brightness: 0
        }
    }

    Rectangle {
        id: systemLabelRectangle
        anchors.horizontalCenter: currentSystemRectangle.horizontalCenter
        anchors.top: currentSystemRectangle.bottom
        anchors.topMargin: 4
        color: "black"
        visible: false
        width: systemLabel.width + 8
        height: systemLabel.height + 8
        radius: 6
        z: 5

        Label {
            id: systemLabel
            text: solarSystemName
            anchors.horizontalCenter: parent.horizontalCenter
            anchors.verticalCenter: parent.verticalCenter
            color: "white"
        }
    }
}
