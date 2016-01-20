import QtQuick 2.2
import QtQuick.Controls 1.1

Item {
    id: image1
    objectName: "shipyardWeapons"
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
                text: weaponName
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
                    displayItemInfo.weaponName = weaponName;
                    displayItemInfo.price = price;
                    displayItemInfo.shieldDamage = shieldDamage;
                    displayItemInfo.shipDamage = shipDamage;
                    displayItemInfo.bought = bought;
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

            property string weaponName
            property int price
            property int shieldDamage
            property int shipDamage
            property bool bought

            Item {
                id: weaponNameGroup
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(weaponNameLabel.height, newWeaponName.height)
                implicitWidth: weaponNameLabel.width + newWeaponName.width + 6

                Label {
                    id: weaponNameLabel
                    text: qsTr("Weapon Name: ")
                    color: "blue"
                }
                Label {
                    id: newWeaponName
                    anchors.left: weaponNameLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.weaponName
                    color: "blue"
                }
            }

            Item {
                id: priceGroup
                anchors.top: weaponNameGroup.bottom
                anchors.topMargin: 6
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(priceLabel.height, newPrice.height)
                implicitWidth: priceLabel.width + newPrice.width + 6

                Label {
                    id: priceLabel
                    text: qsTr("Price: ")
                    color: "blue"
                }
                Label {
                    id: newPrice
                    anchors.left: priceLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.price
                    color: "blue"
                }
            }

            Item {
                id: shieldDamageGroup
                anchors.top: priceGroup.bottom
                anchors.topMargin: 6
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(shieldDamageLabel.height, newShieldDamage.height)
                implicitWidth: shieldDamageLabel.width + newShieldDamage.width + 6

                Label {
                    id: shieldDamageLabel
                    text: qsTr("Shield Damage: ")
                    color: "blue"
                }
                Label {
                    id: newShieldDamage
                    anchors.left: shieldDamageLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.shieldDamage
                    color: "blue"
                }
            }

            Item {
                id: shipDamageGroup
                anchors.top: shieldDamageGroup.bottom
                anchors.topMargin: 6
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(shipDamageLabel.height, newShipDamage.height)
                implicitWidth: shipDamageLabel.width + newShipDamage.width + 6

                Label {
                    id: shipDamageLabel
                    text: qsTr("Shield Damage: ")
                    color: "blue"
                }
                Label {
                    id: newShipDamage
                    anchors.left: shipDamageLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.shipDamage
                    color: "blue"
                }
            }

            Button {
                id: buyWeaponButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                enabled: !displayItemInfo.bought
                text: "Buy Weapon"
                onClicked: {
                    shipyard.buyWeapon(gridView1.currentIndex);
                    mainController.saveData();
                }
            }

            Button {
                id: sellWeaponButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.left: parent.left
                anchors.rightMargin: 8
                enabled: displayItemInfo.bought
                text: "Sell Weapon"
                onClicked: {
                    shipyard.sellWeapon(gridView1.currentIndex);
                    mainController.saveData();
                }
            }

            Label {
                id: boughtLabel
                anchors.bottom: buyWeaponButton.top
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                visible: displayItemInfo.bought
                text: "You already bought this weapon"
            }
        }
    }

    DialogBox {
        id: dialogBox
        z: 1
    }

    function createWeapon(weaponName, price, shieldDamage, shipDamage, bought) {
        gridView1.model.append({"weaponName": weaponName, "price": price,
                                   "shieldDamage": shieldDamage, "shipDamage": shipDamage, "bought": bought});
    }

    function getWeapon(index) {
        return (index < gridView1.model.count) ? gridView1.model.get(index) : 0;
    }

    function setWeapon(index, product, updateInfoDisplay) {
        gridView1.model.set(index, product);
        if (updateInfoDisplay) {
            displayItemInfo.weaponName = product.weaponName;
            displayItemInfo.price = product.price;
            displayItemInfo.shieldDamage = product.shieldDamage;
            displayItemInfo.shipDamage = product.shipDamage;
            displayItemInfo.bought = product.bought;
        }
    }

    function showMessage(message) {
        dialogBox.message = message;
        dialogBox.showMessage();
    }
}
