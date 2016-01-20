import QtQuick 2.2
import QtQuick.Controls 1.1

Rectangle {
    id: grayingRectangle
    anchors.fill: parent
    color: "#7F000000"
    visible: false

    property string message

    MouseArea {
        id: backgroundMouseArea
        anchors.fill: parent
        hoverEnabled: true
    }

    Rectangle {
        width: parent.width * 0.5
        height: messageLabel.height + okButton.height + 20
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        color: "#AF0000BF"
        border.color: "#AF00004F"
        border.width: 5

        Label {
            id: messageLabel
            width: parent.width
            anchors.top: parent.top
            anchors.topMargin: 8
            anchors.horizontalCenter: parent.horizontalCenter
            color: "white"
            wrapMode: Text.WordWrap
            horizontalAlignment: Text.AlignHCenter
            text: message
        }

        Button {
            id: okButton
            anchors.top: messageLabel.bottom
            anchors.topMargin: 6
            anchors.horizontalCenter: parent.horizontalCenter
            text: "Ok"
            onClicked: {
                grayingRectangle.visible = false;
            }
        }
    }

    function showMessage() {
        grayingRectangle.visible = true;
    }
}
