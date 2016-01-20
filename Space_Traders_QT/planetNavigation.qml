import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    objectName: "planetNavigationScreen"
    width: 600
    height: 640
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    property string planetName
    property string techLevel
    property string resourceType
    property bool smallIcon: parent !== null && parent.width <= navigationPageButton.width + marketplaceButton.width + shipyardButton.width + 24

    Button {
        id: navigationPageButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        text: "Back to Navigation Page"
        visible: !smallIcon
        onClicked: mainController.showNavigationPage()
    }

    Button {
        id: smallNavigationPageButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        text: "←"
        visible: smallIcon
        onClicked: mainController.showNavigationPage()
    }

    Button {
        id: marketplaceButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
        text: "Marketplace"
        onClicked: mainController.showMarketplace()
    }

    Button {
        id: fuelButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.right: marketplaceButton.left
        anchors.rightMargin: 6
        text: "Fuel"
        onClicked: mainController.showFuelStation()
    }

    Button {
        id: shipyardButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.right: fuelButton.left
        anchors.rightMargin: 6
        text: "Shipyard"
        visible: !smallIcon
        onClicked: mainController.showShipyard()
    }

    Button {
        id: smallShipyardButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.right: fuelButton.left
        anchors.rightMargin: 6
        text: "🚀"
        visible: smallIcon
        onClicked: mainController.showShipyard()
    }

    Rectangle {
        id: solarSystemView
        border.color: "blue"
        color: "transparent"
        clip: true
        anchors.top: marketplaceButton.bottom
        anchors.topMargin: 6
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8

        Rectangle {
            id: sun
            color: "yellow"
            border.width: 0
            border.color: Qt.rgba(1.0, 1.0, 0.5, 0.8)
            clip: true
            anchors.verticalCenter: parent.verticalCenter
            anchors.horizontalCenter: parent.horizontalCenter
            width: radius
            height: radius
            radius: 50

            ParallelAnimation {
                id: outward
                running: true
                PropertyAnimation {
                    target: sun
                    property: "border.width"
                    to: 7
                    duration: 750
                }
                PropertyAnimation {
                    target: sun
                    property: "radius"
                    to: 64
                    duration: 750
                }
                onStopped: inward.start()
            }

            ParallelAnimation {
                id: inward
                PropertyAnimation {
                    target: sun
                    property: "border.width"
                    to: 0
                    duration: 750
                }
                PropertyAnimation {
                    target: sun
                    property: "radius"
                    to: 50
                    duration: 750
                }
                onStopped: outward.start()
            }
        }
    }

    Rectangle {
        id: planetInfoPane
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        width: parent.width * 0.6
        height: parent.height * 0.6
        color: "#7F0000BF"
        border.color: "#7F00004F"
        border.width: 5
        radius: 10
        visible: false

        Label {
            id: planetNameLabel
            anchors.top: parent.top
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
            color: "white"
            text: "Planet: "
        }

        Label {
            id: planetNameDisplay
            anchors.verticalCenter: planetNameLabel.verticalCenter
            anchors.left: planetNameLabel.right
            anchors.leftMargin: 6
            color: "white"
            text: planetName
        }

        Label {
            id: techLevelLabel
            anchors.top: planetNameLabel.bottom
            anchors.topMargin: 6
            anchors.left: parent.left
            anchors.leftMargin: 8
            color: "white"
            text: "Tech Level: "
        }

        Label {
            id: techLevelDisplay
            anchors.verticalCenter: techLevelLabel.verticalCenter
            anchors.left: techLevelLabel.right
            anchors.leftMargin: 6
            color: "white"
            text: techLevel
        }

        Label {
            id: resourceTypeLabel
            anchors.top: techLevelLabel.bottom
            anchors.topMargin: 6
            anchors.left: parent.left
            anchors.leftMargin: 8
            color: "white"
            text: "Resource Type: "
        }

        Label {
            id: resourceTypeDisplay
            anchors.verticalCenter: resourceTypeLabel.verticalCenter
            anchors.left: resourceTypeLabel.right
            anchors.leftMargin: 6
            color: "white"
            text: resourceType
        }

        Button {
            id: cancelButton
            anchors.bottom: parent.bottom
            anchors.bottomMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
            text: "Cancel"
            onClicked: planetInfoPane.visible = false
        }

        Button {
            id: travelToSolarSystemButton
            anchors.bottom: parent.bottom
            anchors.bottomMargin: 8
            anchors.right: parent.right
            anchors.rightMargin: 8
            text: "Travel to " + planetName
            onClicked: {
                planetNavigation.travelToPlanet(planetName);
                mainController.saveData();
                planetInfoPane.visible = false;
            }
        }
    }

    function createPlanetButtons(name, techLevel, resourceType, color, radius, isCurrentPlanet) {
        var component = Qt.createComponent("Planet.qml");
        var sprite = component.createObject(solarSystemView, {"planetName": name, "techLevel": techLevel, "resourceType": resourceType,
                                                "planetColor": color, "planetRadius": radius, "isCurrentPlanet": isCurrentPlanet});

        if (sprite === null) {
            console.log("Error creating object");
        }
    }

    function setPlanet(name, techLevel, resourceType) {
        planetName = name;
        image1.techLevel = techLevel;
        image1.resourceType = resourceType;
        planetInfoPane.visible = true;
    }

    function setNewCurrentPlanet(name) {
        for (var i in solarSystemView.children) {
            var item = solarSystemView.children[i];
            item.isCurrentPlanet = item.planetName === name;
        }
    }
}
