import QtQuick 2.2
import QtQuick.Controls 1.1

Item {
    id: image1
    objectName: "shipyardShields"
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
                text: shieldName
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
                    displayItemInfo.shieldName = shieldName;
                    displayItemInfo.price = price;
                    displayItemInfo.strength = strength;
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

            property string shieldName
            property int price
            property string strength
            property bool bought

            Item {
                id: shieldNameGroup
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(shieldNameLabel.height, newShieldName.height)
                implicitWidth: shieldNameLabel.width + newShieldName.width + 6

                Label {
                    id: shieldNameLabel
                    text: qsTr("Shield Name: ")
                    color: "blue"
                }
                Label {
                    id: newShieldName
                    anchors.left: shieldNameLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.shieldName
                    color: "blue"
                }
            }

            Item {
                id: priceGroup
                anchors.top: shieldNameGroup.bottom
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
                id: strengthGroup
                anchors.top: priceGroup.bottom
                anchors.topMargin: 6
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(strengthLabel.height, newStrength.height)
                implicitWidth: strengthLabel.width + newStrength.width + 6

                Label {
                    id: strengthLabel
                    text: qsTr("Strength: ")
                    color: "blue"
                }
                Label {
                    id: newStrength
                    anchors.left: strengthLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.strength
                    color: "blue"
                }
            }

            Button {
                id: buyShieldButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                enabled: !displayItemInfo.bought
                text: "Buy Shield"
                onClicked: {
                    shipyard.buyShield(gridView1.currentIndex);
                    mainController.saveData();
                }
            }

            Button {
                id: sellShieldButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.left: parent.left
                anchors.rightMargin: 8
                enabled: displayItemInfo.bought
                text: "Sell Shield"
                onClicked: {
                    shipyard.sellShield(gridView1.currentIndex);
                    mainController.saveData();
                }
            }

            Label {
                id: boughtLabel
                anchors.bottom: buyShieldButton.top
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                visible: displayItemInfo.bought
                text: "You already bought this shield"
            }
        }
    }

    DialogBox {
        id: dialogBox
        z: 1
    }

    function createShield(shieldName, price, strength, bought) {
        gridView1.model.append({"shieldName": shieldName, "price": price,
                                   "strength": strength, "bought": bought});
    }

    function getShield(index) {
        return (index < gridView1.model.count) ? gridView1.model.get(index) : 0;
    }

    function setShield(index, product, updateInfoDisplay) {
        gridView1.model.set(index, product);
        if (updateInfoDisplay) {
            displayItemInfo.shieldName = product.shieldName;
            displayItemInfo.price = product.price;
            displayItemInfo.strength = product.strength;
            displayItemInfo.bought = product.bought;
        }
    }

    function showMessage(message) {
        dialogBox.message = message;
        dialogBox.showMessage();
    }
}
