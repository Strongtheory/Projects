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

    Button {
        id: planetNavigationPageButton
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        text: "Back to Planet Navigation"
        onClicked: mainController.showPlanetNavigationPage()
    }

    TabView {
        anchors.top: planetNavigationPageButton.bottom
        anchors.topMargin: 6
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8

        Tab {
            title: "Ships"
            sourceComponent: ShipyardShips {
            }
            onLoaded: shipyard.instantiateShipTab()
        }
        Tab {
            title: "Gadgets"
            sourceComponent: ShipyardGadgets {
            }
            onLoaded: shipyard.instantiateGadgetsTab()
        }
        Tab {
            title: "Weapons"
            sourceComponent: ShipyardWeapons {
            }
            onLoaded: shipyard.instantiateWeaponsTab()
        }
        Tab {
            title: "Shields"
            sourceComponent: ShipyardShields {
            }
            onLoaded: shipyard.instantiateShieldsTab()
        }
    }
}
