import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    width: 600
    height: 640
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    Item {
        anchors.top: parent.top;
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8

        Label {
            id: videoTitle
            anchors.top: parent.top;
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
            text: "Video"
        }

        Label {
            id: resolutionSettingLabel
            anchors.verticalCenter: resolutionSetting.verticalCenter
            anchors.left: parent.left
            anchors.leftMargin: 8
            text: "Resolution: "
        }

        ComboBox {
            id: resolutionSetting
            anchors.top: videoTitle.bottom
            anchors.topMargin: 6
            anchors.left: resolutionSettingLabel.right
            anchors.leftMargin: 6
        }

        CheckBox {
            id: fullScreenSetting
            anchors.top: resolutionSetting.bottom
            anchors.topMargin: 6
            anchors.left: parent.left
            anchors.leftMargin: 8
            text: "Fullscreen"
        }
    }
}
