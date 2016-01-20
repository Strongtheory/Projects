import QtQuick 2.2
import QtQuick.Controls 1.1

Item {
    id: image1
    objectName: "shipyardShips"
    anchors.fill: parent

    property int creditsAvailable

    Label {
        id: creditsAvailableLabel
        text: qsTr("Credits Available: ")  + creditsAvailable
        color: "blue"
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
    }

    Component {
        id: gridItem
        Rectangle {
            width: gridView1.cellWidth;
            height: gridView1.cellHeight
            border.color: "blue"
            border.width: 1
            radius: 5
            color: "transparent"
            Label {
                text: shipName
                color: "blue"
                anchors.verticalCenter: parent.verticalCenter
                anchors.left: parent.left
                anchors.leftMargin: 2
                anchors.right: parent.right
                anchors.rightMargin: 2
                horizontalAlignment: Text.AlignHCenter
                wrapMode: Text.Wrap
            }
            MouseArea {
                anchors.fill: parent
                onClicked: {
                    gridView1.currentIndex = index;
                    displayItemInfo.shipName = shipName;
                    displayItemInfo.cargoCapacity = cargoCapacity;
                    displayItemInfo.gadgetCapacity = gadgetCapacity;
                    displayItemInfo.shieldCapacity = shieldCapacity;
                    displayItemInfo.weaponCapacity = weaponCapacity;
                    displayItemInfo.maxHealth = maxHealth;
                    displayItemInfo.maxFuel = maxFuel;
                    displayItemInfo.cost = cost;
                }
                onEntered: parent.border.color = "orange"
                onExited: parent.border.color = "blue"
                hoverEnabled: true
            }
        }
    }

    SplitView {
        orientation: Qt.Vertical
        anchors.top: creditsAvailableLabel.bottom
        anchors.topMargin: 6
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8

        GridView {
            id: gridView1
            height: 150
            model: ListModel {}
            cellHeight: 75
            cellWidth: 75
            delegate: gridItem
            highlight: Rectangle {
                color: "lightsalmon";
                radius: 5
            }
            focus: true
            clip: true
            currentIndex: -1
        }

        Item {
            id: displayItemInfo

            property string currentShipName
            property int currentCargoCapacity
            property int currentGadgetCapacity
            property int currentShieldCapacity
            property int currentWeaponCapacity
            property int currentMaxHealth
            property int currentMaxFuel
            property int currentCost

            property string shipName
            property int cargoCapacity
            property int gadgetCapacity
            property int shieldCapacity
            property int weaponCapacity
            property int maxHealth
            property int maxFuel
            property int cost

            Item {
                id: shipNameGroup
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentShipName.height, shipNameLabel.height, newShipName.height)

                Label {
                    id: currentShipName
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentShipName
                    color: "blue"
                }
                Label {
                    id: shipNameLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Ship Name")
                    color: "blue"
                }
                Label {
                    id: newShipName
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.shipName
                    color: "blue"
                }
            }

            Item {
                id: cargoCapacityGroup
                anchors.top: shipNameGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentCargoCapacity.height, cargoCapacityLabel.height, newCargoCapacity.height)

                Label {
                    id: currentCargoCapacity
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentCargoCapacity
                    color: "blue"
                }
                Label {
                    id: cargoCapacityLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Cargo Capacity")
                    color: "blue"
                }
                Label {
                    id: newCargoCapacity
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.cargoCapacity
                    color: "blue"
                }
            }

            Item {
                id: gadgetCapacityGroup
                anchors.top: cargoCapacityGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentGadgetCapacity.height, gadgetCapacityLabel.height, newGadgetCapacity.height)

                Label {
                    id: currentGadgetCapacity
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentGadgetCapacity
                    color: "blue"
                }
                Label {
                    id: gadgetCapacityLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Gadget Capacity")
                    color: "blue"
                }
                Label {
                    id: newGadgetCapacity
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.gadgetCapacity
                    color: "blue"
                }
            }

            Item {
                id: shieldCapacityGroup
                anchors.top: gadgetCapacityGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentShieldCapacity.height, shieldCapacityLabel.height, newShieldCapacity.height)

                Label {
                    id: currentShieldCapacity
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentShieldCapacity
                    color: "blue"
                }
                Label {
                    id: shieldCapacityLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Shield Capacity")
                    color: "blue"
                }
                Label {
                    id: newShieldCapacity
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.shieldCapacity
                    color: "blue"
                }
            }

            Item {
                id: weaponCapacityGroup
                anchors.top: shieldCapacityGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentWeaponCapacity.height, weaponCapacityLabel.height, newWeaponCapacity.height)

                Label {
                    id: currentWeaponCapacity
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentWeaponCapacity
                    color: "blue"
                }
                Label {
                    id: weaponCapacityLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Weapon Capacity")
                    color: "blue"
                }
                Label {
                    id: newWeaponCapacity
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.weaponCapacity
                    color: "blue"
                }
            }

            Item {
                id: maxHealthGroup
                anchors.top: weaponCapacityGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentMaxHealth.height, maxHealthLabel.height, newMaxHealth.height)

                Label {
                    id: currentMaxHealth
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentMaxHealth
                    color: "blue"
                }
                Label {
                    id: maxHealthLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Max Health")
                    color: "blue"
                }
                Label {
                    id: newMaxHealth
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.maxHealth
                    color: "blue"
                }
            }

            Item {
                id: maxFuelGroup
                anchors.top: maxHealthGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentMaxFuel.height, maxFuelLabel.height, newMaxFuel.height)

                Label {
                    id: currentMaxFuel
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentMaxHealth
                    color: "blue"
                }
                Label {
                    id: maxFuelLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Max Fuel")
                    color: "blue"
                }
                Label {
                    id: newMaxFuel
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.maxFuel
                    color: "blue"
                }
            }

            Item {
                id: costGroup
                anchors.top: maxFuelGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                implicitHeight: Math.max(currentCost.height, costLabel.height, newCost.height)

                Label {
                    id: currentCost
                    anchors.left: parent.left
                    anchors.leftMargin: 6
                    text: displayItemInfo.currentCost
                    color: "blue"
                }
                Label {
                    id: costLabel
                    anchors.horizontalCenter: parent.horizontalCenter
                    text: qsTr("Cost")
                    color: "blue"
                }
                Label {
                    id: newCost
                    anchors.right: parent.right
                    anchors.rightMargin: 6
                    text: displayItemInfo.cost
                    color: "blue"
                }
            }

            Button {
                id: buyShipButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                enabled: displayItemInfo.shipName !== "" && displayItemInfo.currentShipName !== displayItemInfo.shipName
                text: "Buy Ship"
                onClicked: {
                    shipyard.buyShip(gridView1.currentIndex);
                    mainController.saveData();
                }
            }
        }
    }

    DialogBox {
        id: dialogBox
        z: 1
    }

    function createShip(shipName, cargoCapacity, gadgetCapacity, shieldCapacity, weaponCapacity, maxHealth, maxFuel, cost) {
        gridView1.model.append({"shipName": shipName, "cargoCapacity": cargoCapacity, "gadgetCapacity": gadgetCapacity,
                                   "shieldCapacity": shieldCapacity, "weaponCapacity": weaponCapacity, "maxHealth": maxHealth,
                                   "maxFuel": maxFuel, "cost": cost});
    }

    function getShip(index) {
        return (index < gridView1.model.count) ? gridView1.model.get(index) : 0;
    }

    function setShip(index, product, updateInfoDisplay) {
        gridView1.model.set(index, product);
        if (updateInfoDisplay) {
            displayItemInfo.shipName = product.shipName;
            displayItemInfo.cargoCapacity = product.cargoCapacity;
            displayItemInfo.gadgetCapacity = product.gadgetCapacity;
            displayItemInfo.shieldCapacity = product.shieldCapacity;
            displayItemInfo.weaponCapacity = product.weaponCapacity;
            displayItemInfo.maxHealth = product.maxHealth;
            displayItemInfo.maxFuel = product.maxFuel;
            displayItemInfo.cost = product.cost;
        }
    }

    function setCurrentShip(shipName, cargoCapacity, gadgetCapacity, shieldCapacity, weaponCapacity, maxHealth, maxFuel, cost) {
        displayItemInfo.currentShipName = shipName;
        displayItemInfo.currentCargoCapacity = cargoCapacity;
        displayItemInfo.currentGadgetCapacity = gadgetCapacity;
        displayItemInfo.currentShieldCapacity = shieldCapacity;
        displayItemInfo.currentWeaponCapacity = weaponCapacity;
        displayItemInfo.currentMaxHealth = maxHealth;
        displayItemInfo.currentMaxFuel = maxFuel;
        displayItemInfo.currentCost = cost;
    }

    function showMessage(message) {
        dialogBox.message = message;
        dialogBox.showMessage();
    }
}
