import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    objectName: "fuelStationScreen"
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    property int fuelCapacity;
    property int currentFuel;

    Button {
        id: planetNavigationButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        text: "Planet Navigation"
        onClicked: mainController.showPlanetNavigationPage()
    }

    Rectangle {
        id: fuelView
        border.color: "blue"
        color: "transparent"
        clip: true
        anchors.top: planetNavigationButton.bottom
        anchors.topMargin: 6
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8

        Item {
            id: fuelDisplay
            anchors.verticalCenter: parent.verticalCenter
            anchors.left: parent.left
            anchors.leftMargin: 8
            anchors.right: parent.right
            anchors.rightMargin: 8
            height: Math.max(fuelLabel.height, fuelBar.height)

            Label {
                id: fuelLabel
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.left: parent.left
                anchors.leftMargin: 8
                font.pointSize: 16
                text: "⛽"
                color: "white"
            }

            ProgressBar {
                id: fuelBar
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.left: fuelLabel.right
                anchors.leftMargin: 6
                anchors.right: parent.right
                anchors.rightMargin: 8
                minimumValue: 0
                maximumValue: fuelCapacity
                value: currentFuel
            }
        }

        Item {
            anchors.horizontalCenter: parent.horizontalCenter
            anchors.top: fuelDisplay.bottom
            anchors.topMargin: 6
            width: buyFuelButton.width + sellFuelButton.width + 22
            height: buyFuelButton.height

            Button {
                id: buyFuelButton
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.left: parent.left
                anchors.leftMargin: 8
                text: "Buy Fuel"
                onClicked: fuelStation.buyFuel()
            }

            Button {
                id: sellFuelButton
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.left: buyFuelButton.right
                anchors.leftMargin: 6
                text: "Sell Fuel"
                onClicked: fuelStation.sellFuel()
            }
        }


    }
}
