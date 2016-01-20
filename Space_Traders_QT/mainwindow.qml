import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    Button {
        id: button1
        text: qsTr("New Game")
        anchors.bottom: button2.top
        anchors.bottomMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
        onClicked: mainController.startNewGame()
    }

    Button {
        id: button2
        text: qsTr("Continue")
        enabled: mainController.isDataLoadable()
        anchors.bottom: button3.top
        anchors.bottomMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
        onClicked: {
            mainController.loadData();
            mainController.showNavigationPage();
        }
    }

    Button {
        id: button3
        text: qsTr("Settings")
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 32
        anchors.right: parent.right
        anchors.rightMargin: 8
        onClicked: mainController.showSettings()
    }

    Label {
        id: label1
        text: qsTr("Space Traders")
        font.pointSize: 24
        color: "gray"
        anchors.right: parent.right
        anchors.rightMargin: 8
    }
}
