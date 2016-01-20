import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    objectName: "navigationScreen"
    width: 600
    height: 640
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    property string solarSystemName
    property int solarSystemPlanets
    property bool travelEnabled

    property int fuelCapacity
    property int currentFuel

    property bool useSmallButtons: parent !== null && parent.width <= zoomInButton.width + zoomOutButton.width
                                   + planetButton.width + fuelBar.width + fuelLabel.width + 38

    Button {
        id: planetButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.horizontalCenter: parent.horizontalCenter
        text: "Planet"
        onClicked: mainController.showPlanetNavigationPage()
    }

    Button {
        id: zoomInButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        text: "Zoom In"
        visible: !useSmallButtons
        onClicked: zoomMap(4 / 3)
    }

    Button {
        id: zoomInButtonSmall
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        text: "+"
        visible: useSmallButtons
        onClicked: zoomMap(4 / 3)
    }

    Button {
        id: zoomOutButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: zoomInButton.right
        anchors.leftMargin: 6
        text: "Zoom Out"
        visible: !useSmallButtons
        onClicked: zoomMap(3 / 4)
    }

    Button {
        id: zoomOutButtonSmall
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: zoomInButtonSmall.right
        anchors.leftMargin: 6
        text: "-"
        visible: useSmallButtons
        onClicked: zoomMap(3 / 4)
    }

    ProgressBar {
        id: fuelBar
        anchors.left: fuelLabel.right
        anchors.leftMargin: 6
        anchors.right: parent.right
        anchors.rightMargin: 8
        anchors.top: parent.top
        anchors.topMargin: 8
        minimumValue: 0
        maximumValue: fuelCapacity
        value: currentFuel
    }

    Label {
        id: fuelLabel
        anchors.left: planetButton.right
        anchors.leftMargin: 6
        anchors.verticalCenter: fuelBar.verticalCenter
        text: "⛽"
        color: "white"
    }

    Rectangle {
        border.color: "blue"
        color: "transparent"
        clip: true

        Flickable {
            id: mapRegion
            objectName: "mapRegion"
            contentWidth: 2000
            contentHeight: 2000
            boundsBehavior: Flickable.DragOverBounds
            anchors.fill: parent
        }

        anchors.top: planetButton.bottom
        anchors.topMargin: 6
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8

        Rectangle {
            id: verticalScrollbar
            anchors.right: mapRegion.right
            y: mapRegion.visibleArea.yPosition * mapRegion.height
            width: 10
            height: mapRegion.visibleArea.heightRatio * mapRegion.height
            color: "gray"
        }

        Rectangle {
            id: horizontalScrollbar
            anchors.bottom: mapRegion.bottom
            x: mapRegion.visibleArea.xPosition * mapRegion.width
            height: 10
            width: mapRegion.visibleArea.widthRatio * mapRegion.width
            color: "gray"
        }
    }

    Rectangle {
        id: solarSystemInfoPane
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
            id: solarSystemNameLabel
            anchors.top: parent.top
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
            color: "white"
            text: "Solar System: "
        }

        Label {
            id: solarSystemNameDisplay
            anchors.top: parent.top
            anchors.topMargin: 8
            anchors.left: solarSystemNameLabel.right
            anchors.leftMargin: 6
            color: "white"
            text: solarSystemName
        }

        Label {
            id: solarSystemPlanetsLabel
            anchors.top: solarSystemNameLabel.bottom
            anchors.topMargin: 6
            anchors.left: parent.left
            anchors.leftMargin: 8
            color: "white"
            text: "Planets: "
        }

        Label {
            id: solarSystemPlanetsDisplay
            anchors.top: solarSystemNameDisplay.bottom
            anchors.topMargin: 6
            anchors.left: solarSystemPlanetsLabel.right
            anchors.leftMargin: 6
            color: "white"
            text: solarSystemPlanets
        }

        Button {
            id: cancelButton
            anchors.bottom: parent.bottom
            anchors.bottomMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
            text: "Cancel"
            onClicked: solarSystemInfoPane.visible = false
        }

        Button {
            id: travelToSolarSystemButton
            anchors.bottom: parent.bottom
            anchors.bottomMargin: 8
            anchors.right: parent.right
            anchors.rightMargin: 8
            enabled: travelEnabled
            text: "Travel to " + solarSystemName
            onClicked: {
                navigation.travelToSolarSystem(solarSystemName);
                mainController.saveData();
                solarSystemInfoPane.visible = false;
            }
        }
    }

    function createPlanetButtons(name, color, isCurrentSystem, x, y) {
        var component = Qt.createComponent("SolarSystem.qml");
        var sprite = component.createObject(mapRegion.contentItem, {"solarSystemName": name, "solarSystemColor": color,
                                                "isCurrentSystem": isCurrentSystem, "x": x, "y": y});

        if (sprite === null) {
            console.log("Error creating object");
        }
    }

    function createPlanetButtonsAndroid(name, color, isCurrentSystem, x, y) {
        var component = Qt.createComponent("SolarSystemAndroid.qml");
        var sprite = component.createObject(mapRegion.contentItem, {"solarSystemName": name, "solarSystemColor": color,
                                                "isCurrentSystem": isCurrentSystem, "x": x, "y": y});

        if (sprite === null) {
            console.log("Error creating object");
        }
    }

    function setSolarSystem(name) {
        solarSystemName = name;
        solarSystemPlanets = navigation.getNumPlanets(name);
        travelEnabled = navigation.isTravelableToSolarSystem(name);
        solarSystemInfoPane.visible = true;
    }

    function setNewCurrentPlanet(name) {
        for (var i in mapRegion.contentItem.children) {
            var item = mapRegion.contentItem.children[i];
            item.isCurrentSystem = item.solarSystemName === name;
        }
    }

    function zoomMap(scaling) {
        mapRegion.contentWidth *= scaling;
        mapRegion.contentHeight *= scaling;
        mapRegion.contentX *= scaling;
        mapRegion.contentY *= scaling;
        for (var i in mapRegion.contentItem.children) {
            var item = mapRegion.contentItem.children[i];
            item.x *= scaling;
            item.y *= scaling;
            item.size *= scaling;
        }
    }
}
