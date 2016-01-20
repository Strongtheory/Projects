import QtQuick 2.2
import QtQuick.Controls 1.1

Item {
    id: image1
    objectName: "shipyardGadgets"
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
                text: gadgetName
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
                    displayItemInfo.gadgetName = gadgetName;
                    displayItemInfo.price = price;
                    displayItemInfo.minimumTechLevel = minimumTechLevel;
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

            property string gadgetName
            property int price
            property string minimumTechLevel
            property bool bought

            Item {
                id: gadgetNameGroup
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(gadgetNameLabel.height, newGadgetName.height)
                implicitWidth: gadgetNameLabel.width + newGadgetName.width + 6

                Label {
                    id: gadgetNameLabel
                    text: qsTr("Gadget Name: ")
                    color: "blue"
                }
                Label {
                    id: newGadgetName
                    anchors.left: gadgetNameLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.gadgetName
                    color: "blue"
                }
            }

            Item {
                id: priceGroup
                anchors.top: gadgetNameGroup.bottom
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
                id: minTechLevelGroup
                anchors.top: priceGroup.bottom
                anchors.topMargin: 6
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: Math.max(minTechLevelLabel.height, newMinTechLevel.height)
                implicitWidth: minTechLevelLabel.width + newMinTechLevel.width + 6

                Label {
                    id: minTechLevelLabel
                    text: qsTr("Minimum Tech Level: ")
                    color: "blue"
                }
                Label {
                    id: newMinTechLevel
                    anchors.left: minTechLevelLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.minimumTechLevel
                    color: "blue"
                }
            }

            Button {
                id: buyGadgetButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                enabled: !displayItemInfo.bought
                text: "Buy Gadget"
                onClicked: {
                    shipyard.buyGadget(gridView1.currentIndex);
                    mainController.saveData();
                }
            }

            Button {
                id: sellGadgetButton
                anchors.bottom: parent.bottom
                anchors.bottomMargin: 8
                anchors.left: parent.left
                anchors.rightMargin: 8
                enabled: displayItemInfo.bought
                text: "Sell Gadget"
                onClicked: {
                    shipyard.sellGadget(gridView1.currentIndex);
                    mainController.saveData();
                }
            }

            Label {
                id: boughtLabel
                anchors.bottom: buyGadgetButton.top
                anchors.bottomMargin: 8
                anchors.right: parent.right
                anchors.rightMargin: 8
                visible: displayItemInfo.bought
                text: "You already bought this gadget"
            }
        }
    }

    DialogBox {
        id: dialogBox
        z: 1
    }

    function createGadget(gadgetName, price, minimumTechLevel, bought) {
        gridView1.model.append({"gadgetName": gadgetName, "price": price,
                                   "minimumTechLevel": minimumTechLevel, "bought": bought});
    }

    function getGadget(index) {
        return (index < gridView1.model.count) ? gridView1.model.get(index) : 0;
    }

    function setGadget(index, product, updateInfoDisplay) {
        gridView1.model.set(index, product);
        if (updateInfoDisplay) {
            displayItemInfo.gadgetName = product.gadgetName;
            displayItemInfo.price = product.price;
            displayItemInfo.minimumTechLevel = product.minimumTechLevel;
            displayItemInfo.bought = product.bought;
        }
    }

    function showMessage(message) {
        dialogBox.message = message;
        dialogBox.showMessage();
    }
}
